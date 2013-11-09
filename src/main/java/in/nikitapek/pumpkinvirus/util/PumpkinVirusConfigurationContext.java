package in.nikitapek.pumpkinvirus.util;

import com.amshulman.mbapi.MbapiPlugin;
import com.amshulman.mbapi.util.ConfigurationContext;
import com.amshulman.typesafety.TypeSafeSet;
import com.amshulman.typesafety.impl.TypeSafeSetImpl;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

public final class PumpkinVirusConfigurationContext extends ConfigurationContext {
    public final int ticks;

    public final TypeSafeSet<String> worlds;

    public PumpkinVirusConfigurationContext(final MbapiPlugin plugin) {
        super(plugin);

        plugin.saveDefaultConfig();

        worlds = new TypeSafeSetImpl<>(new HashSet<String>(), SupplementaryTypes.STRING);

        // Retrieves the ticks between pumpkin spread attempts.
        ticks = plugin.getConfig().getInt("spreadSpeed", 5);

        // Attempts to read the configurationSection containing the worlds in which pumpkins are allowed to spread.
        @SuppressWarnings("unchecked")
        List<String> worldList = (List<String>) plugin.getConfig().getList("worlds");
        if (worldList == null) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load pumpkin-enabled worlds list.");
        } else {
            for (final String world : worldList) {
                worlds.add(world);
            }
        }
    }
}
