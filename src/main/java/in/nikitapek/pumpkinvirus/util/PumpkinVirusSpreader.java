package in.nikitapek.pumpkinvirus.util;

import in.nikitapek.pumpkinvirus.util.astar.AStar;
import in.nikitapek.pumpkinvirus.util.astar.PathingResult;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class PumpkinVirusSpreader implements Runnable {
    private static PumpkinVirusConfigurationContext configurationContext;

    private final Block block;
    private final Material initialMaterial;

    public PumpkinVirusSpreader(final Block block) {
        this.block = block;
        this.initialMaterial = block.getType();
    }

    public static void initialize(PumpkinVirusConfigurationContext configurationContext) {
        PumpkinVirusSpreader.configurationContext = configurationContext;
    }

    @Override
    public void run() {
        World world = block.getWorld();

        // Checks to make sure pumpkins are allowed to spread in the current world.
        if (!configurationContext.worlds.contains(world.getName())) {
            return;
        }

        // If this block has been changed since the spread began (e.g. a pumpkin being removed by the anti-virus), then the spread fails.
        if (!initialMaterial.equals(block.getType())) {
            return;
        }

        // Get the block to which the virus will attempt to spread.
        Block targetBlock = getToBlock(block);

        if (targetBlock == null) {
            return;
        }

        Material targetMaterial = targetBlock.getType();

        if (initialMaterial.equals(configurationContext.antiVirusBlockType)) {
            // If the target block is not a virus block, attempt to spread from this location again.
            if (!targetMaterial.equals(configurationContext.virusBlockType)) {
                spreadBlock(block);
                return;
            }
        } else {
            // If the block is not air, then attempt to spread the virus from the current location again.
            if (!Material.AIR.equals(targetMaterial) ) {
                if (!configurationContext.virusBurrowing || !configurationContext.burrowableBlocks.contains(targetMaterial)) {
                    // If the block being spread to is an anti-virus block, then the anti-virus begins spreading.
                    if (configurationContext.antiVirusBlockType.equals(targetMaterial)) {
                        spreadBlock(targetBlock);
                    }

                    spreadBlock(block);
                    return;
                }
            }
        }

        // If the target block does not have a valid supporting material, we must re-try the spread of the virus.
        if (!isSupportMaterialUnderBlockValid(targetBlock)) {
            spreadBlock(block);
            return;
        }

        // Converts the target block to a virus block.
        targetBlock.setType(block.getType());

        // Spreads a new pumpkin from the target location.
        spreadBlock(targetBlock);
    }

    public static void spreadBlock(Block block) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        Material material = block.getType();

        // Creates an sync task, which when run, spreads a pumpkin.
        scheduler.scheduleSyncDelayedTask(configurationContext.plugin, new PumpkinVirusSpreader(block), configurationContext.ticks);

        if (material.equals(configurationContext.antiVirusBlockType) && configurationContext.antiVirusDecayTime != 0) {
            PumpkinVirusDecayer.decayBlock(block, configurationContext.antiVirusDecayTime);
        } else if (material.equals(configurationContext.antiVirusBlockType) && configurationContext.virusDecayTime != 0) {
            PumpkinVirusDecayer.decayBlock(block, configurationContext.virusDecayTime);
        }
    }

    private static Block getToBlock(Block fromBlock) {
        Location location = fromBlock.getLocation();

        if (configurationContext.trackingPlayers) {
            Player nearestPlayer = getNearestPlayer(location);

            if (nearestPlayer != null) {
                AStar aStar = null;
                try {
                    aStar = new AStar(location, nearestPlayer.getLocation(), 50);

                    if (aStar.getPathingResult().equals(PathingResult.SUCCESS)) {
                        return aStar.getEndLocation().getBlock();
                    }
                } catch (AStar.InvalidPathException ex) { }
            }
        }

        // Determine the direction in which to spread the plugins.
        final int randX = PumpkinVirusUtil.RANDOM.nextInt(2);
        final int randY = PumpkinVirusUtil.RANDOM.nextInt(2);
        final int randZ = PumpkinVirusUtil.RANDOM.nextInt(2);

        // Instantializes the new coordinates, which the pumpkin will spread to.
        int newX;
        int newY;
        int newZ;

        // Based on random directions, adds or subtracts the randomly generated values in order to move the location at which the next pumpkin will be spawned.
        newX = fromBlock.getX() + (PumpkinVirusUtil.RANDOM.nextBoolean() ? randX : -randX);
        newY = fromBlock.getY() + (PumpkinVirusUtil.RANDOM.nextBoolean() ? randY : -randY);
        newZ = fromBlock.getZ() + (PumpkinVirusUtil.RANDOM.nextBoolean() ? randZ : -randZ);

        // Gets the block to be converted.
        return fromBlock.getWorld().getBlockAt(newX, newY, newZ);
    }

    private static boolean isSupportMaterialUnderBlockValid(Block block) {
        // Gets the materials under the target block to check for a valid support.
        for (int i = 1; i <= configurationContext.maxHeightOffGround; i++) {
            Material supportBlockMaterial = block.getRelative(0, -i, 0).getType();

            // If the material of the block acting as "support" underneath the one being targetted is not considered to be a valid support, then we must retry the creation of the pumpkin, so as not to allow the pumpkins to rise too far above the ground.
            if (Material.AIR.equals(supportBlockMaterial) ||
                    Material.WATER.equals(supportBlockMaterial) ||
                    Material.LAVA.equals(supportBlockMaterial) ||
                    configurationContext.virusBlockType.equals(supportBlockMaterial) ||
                    configurationContext.antiVirusBlockType.equals(supportBlockMaterial)) {
                continue;
            }

            return true;
        }

        return false;
    }

    private static Player getNearestPlayer(Location location) {
        Entity nearestPlayer = null;
        int distanceToNearestPlayer = Integer.MAX_VALUE;

        for (Entity entity : location.getWorld().getLivingEntities()) {
            if (entity.getType().equals(EntityType.PLAYER)) {
                double distanceToEntity = location.distanceSquared(entity.getLocation());
                if (distanceToEntity < configurationContext.playerTrackingRadius && distanceToEntity < distanceToNearestPlayer) {
                    nearestPlayer = entity;
                    distanceToEntity = distanceToNearestPlayer;
                }
            }
        }

        return (Player) nearestPlayer;
    }
}
