/**
 *
 * @author Indivisible0
 */
package com.github.indiv0.pumpkinvirus;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    public static PumpkinVirus plugin;

    public BlockPlaceListener(PumpkinVirus instance) {
        plugin = instance;
    }

    // Create a method to handle/interact with block placement events.
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!plugin.getPumpkinSpreadStatus())
            return;

        Block block = event.getBlock();

        if (block.getType() != Material.PUMPKIN)
            return;

        // Attempts to spread a pumpkin.
        plugin.setPumpkinSpreadTimer(block);
    }
}
