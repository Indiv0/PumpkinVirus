package com.github.Indiv0.PumpkinVirus;

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

        // Creates a new pumpkin and tells it to spread.
        Pumpkin pumpkin = new Pumpkin(plugin);
        pumpkin.pumpkinSpread(pumpkin, block);
    }
}
