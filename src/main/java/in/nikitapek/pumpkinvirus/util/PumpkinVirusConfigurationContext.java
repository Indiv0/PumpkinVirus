package in.nikitapek.pumpkinvirus.util;

import com.amshulman.mbapi.MbapiPlugin;
import com.amshulman.mbapi.util.ConfigurationContext;

public class PumpkinVirusConfigurationContext extends ConfigurationContext {
    // Stores whether or not pumpkins are currently spreading.
    public boolean isPumpkinSpreadEnabled;
    public final long ticks;

    public PumpkinVirusConfigurationContext(MbapiPlugin plugin) {
        super(plugin);

        plugin.saveDefaultConfig();

        // Retrieves whether or not pumpkin spreading is enabled.
        isPumpkinSpreadEnabled = plugin.getConfig().getBoolean("spreadEnabled", false);

        // Retrieves the ticks between pumpkin spread attempts.
        ticks = plugin.getConfig().getInt("spreadSpeed", 5);
    }
}
