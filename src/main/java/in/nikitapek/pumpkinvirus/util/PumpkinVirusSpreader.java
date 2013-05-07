package in.nikitapek.pumpkinvirus.util;

import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class PumpkinVirusSpreader implements Runnable {
    private final PumpkinVirusConfigurationContext configurationContext;
    private final Block block;

    public PumpkinVirusSpreader(PumpkinVirusConfigurationContext configurationContext, final Block block) {
        this.configurationContext = configurationContext;
        this.block = block;
    }

    @Override
    public void run() {
        try {
            // Checks to make sure pumpkins are allowed to spread.
            if (configurationContext.isPumpkinSpreadEnabled == false)
                return;

            Random randomGenerator = new Random();

            // Determine the direction in which to spread the plugins.
            int randX = randomGenerator.nextInt(2);
            int randY = randomGenerator.nextInt(2);
            int randZ = randomGenerator.nextInt(2);

            // Determines the direction using random boolean values.
            boolean dirX = randomGenerator.nextBoolean();
            boolean dirY = randomGenerator.nextBoolean();
            boolean dirZ = randomGenerator.nextBoolean();

            // Instantializes the new coordinates, which the pumpkin
            // will spread to.
            int newX;
            int newY;
            int newZ;

            // Based on the directions, adds or subtracts the randomly
            // generated values in order to move the location at which
            // the next pumpkin will be spawned.
            if (dirX == true)
                newX = block.getX() + randX;
            else
                newX = block.getX() - randX;

            if (dirY == true)
                newY = block.getY() + randY;
            else
                newY = block.getY() - randY;

            if (dirZ == true)
                newZ = block.getZ() + randZ;
            else
                newZ = block.getZ() - randZ;

            // Gets the block to be converted, as well as its material.
            Block targetBlock = block.getWorld().getBlockAt(newX, newY, newZ);
            Material targetBlockMaterial = targetBlock.getType();

            // If the block is not air, then attempt to create a pumpkin
            // there once again.
            if (targetBlockMaterial != Material.AIR) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(configurationContext.plugin, new PumpkinVirusSpreader(configurationContext, block), configurationContext.ticks);
                return;
            }

            // Gets the material 3 blocks under the target block.
            Material baseBlockMaterial = block.getWorld().getBlockAt(newX, newY - 3, newZ).getType();

            // If the material of the block acting as "support"
            // underneath the one being targetted is not considered to
            // be a valid support, then we must retry the creation of
            // the pumpkin, so as not to allow the pumpkins to rise too
            // far above the ground.
            if (baseBlockMaterial == Material.AIR ||
                    baseBlockMaterial == Material.PUMPKIN ||
                    baseBlockMaterial == Material.WATER ||
                    baseBlockMaterial == Material.LAVA) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(configurationContext.plugin, new PumpkinVirusSpreader(configurationContext, block), configurationContext.ticks);
                return;
            }

            // Converts the target block to a pumpkin.
            targetBlock.setType(Material.PUMPKIN);

            // Spreads a new pumpkin from the target location.
            Bukkit.getScheduler().scheduleSyncDelayedTask(configurationContext.plugin, new PumpkinVirusSpreader(configurationContext, targetBlock), configurationContext.ticks);
        }
        catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to spread pumpkins.");
        }
    }
}
