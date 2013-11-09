package in.nikitapek.pumpkinvirus;

import com.amshulman.mbapi.MbapiPlugin;
import in.nikitapek.pumpkinvirus.commands.CommandPumpkinVirus;
import in.nikitapek.pumpkinvirus.events.PumpkinVirusListener;
import in.nikitapek.pumpkinvirus.util.PumpkinVirusConfigurationContext;
import in.nikitapek.pumpkinvirus.util.PumpkinVirusDecayer;
import in.nikitapek.pumpkinvirus.util.PumpkinVirusSpreader;
import org.bukkit.Bukkit;

public final class PumpkinVirusPlugin extends MbapiPlugin {
    @Override
    public void onEnable() {
        final PumpkinVirusConfigurationContext configurationContext = new PumpkinVirusConfigurationContext(this);

        registerEventHandler(new PumpkinVirusListener(configurationContext));
        registerCommandExecutor(new CommandPumpkinVirus(configurationContext));

        PumpkinVirusSpreader.initialize(configurationContext);
        PumpkinVirusDecayer.initialize(configurationContext);

        super.onEnable();
    }

    @Override
    public void onDisable() {
        // Cancels any tasks scheduled by this plugin.
        Bukkit.getScheduler().cancelTasks(this);
    }
}
