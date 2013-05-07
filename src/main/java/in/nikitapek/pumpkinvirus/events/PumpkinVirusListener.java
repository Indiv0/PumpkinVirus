package in.nikitapek.pumpkinvirus.events;

import in.nikitapek.pumpkinvirus.util.PumpkinVirusConfigurationContext;
import in.nikitapek.pumpkinvirus.util.PumpkinVirusSpreader;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PumpkinVirusListener implements Listener {
    private PumpkinVirusConfigurationContext configurationContext;

    public PumpkinVirusListener(final PumpkinVirusConfigurationContext configurationContext) {
        this.configurationContext = configurationContext;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!configurationContext.isPumpkinSpreadEnabled)
            return;

        if (event.getBlock().getType() != Material.PUMPKIN)
            return;

        // Creates an sync task, which when run, spreads a pumpkin.
        Bukkit.getScheduler().scheduleSyncDelayedTask(configurationContext.plugin, new PumpkinVirusSpreader(configurationContext, event.getBlock()), configurationContext.ticks);
    }
}
