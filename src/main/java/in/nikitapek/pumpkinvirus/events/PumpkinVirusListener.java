package in.nikitapek.pumpkinvirus.events;

import in.nikitapek.pumpkinvirus.util.PumpkinVirusConfigurationContext;
import in.nikitapek.pumpkinvirus.util.PumpkinVirusSpreader;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public final class PumpkinVirusListener implements Listener {
    private final PumpkinVirusConfigurationContext configurationContext;

    public PumpkinVirusListener(final PumpkinVirusConfigurationContext configurationContext) {
        this.configurationContext = configurationContext;
    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (!configurationContext.isPumpkinSpreadEnabled) {
            return;
        }

        if (event.getBlock().getType() != Material.PUMPKIN) {
            return;
        }

        PumpkinVirusSpreader.spreadPumpkins(configurationContext, event.getBlock());
    }
}
