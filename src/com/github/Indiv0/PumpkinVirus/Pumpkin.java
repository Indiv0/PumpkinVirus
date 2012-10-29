/**
 *
 * @author Indivisible0
 */
package com.github.Indiv0.PumpkinVirus;

import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class Pumpkin {
    PumpkinVirus plugin;

    public Pumpkin(PumpkinVirus instance) {
        plugin = instance;
    }

    public void pumpkinSpread(Pumpkin pumpkin, Block block) {
        long speed = 500;

        // Checks to make sure pumpkins are spreading.
        if (plugin.getPumpkinSpreadStatus() == false)
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

        // Instantializes the new coordinates, which the pumpkin will spread to.
        int newX;
        int newY;
        int newZ;

        // Based on the directions, adds or subtracts the randomly generated
        // values in order to move the location at which the next pumpkin will
        // be spawned.
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

        // If the block is not air, then attempt to create a pumpkin there once
        // again.
        if (targetBlockMaterial != Material.AIR) {
            pumpkinSpread(pumpkin, block);
            return;
        }

        // Gets the material 3 blocks under the target block.
        Material baseBlockMaterial = block.getWorld().getBlockAt(newX, newY - 3, newZ).getType();

        // If the material is air, then we must retry the creation of the
        // pumpkin, so as not to allow the pumpkins to rise too far above the
        // ground.
        if (baseBlockMaterial == Material.AIR) {
            pumpkinSpread(pumpkin, block);
            return;
        }

        // Converts the target block to a pumpkin.
        Pumpkin newPumpkin = new Pumpkin(plugin);
        targetBlock.setType(Material.PUMPKIN);

        // Stops the thread temporarily in order to pause the spread of
        // pumpkins.
        try {
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            plugin.logException(ex, Level.SEVERE, "Thread has failed.");
        }

        // Spreads a new pumpkin from the target location.
        newPumpkin.pumpkinSpread(newPumpkin, targetBlock);
    }
}