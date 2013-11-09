package in.nikitapek.pumpkinvirus.util;

import com.amshulman.mbapi.MbapiPlugin;
import com.amshulman.mbapi.util.ConfigurationContext;
import com.amshulman.typesafety.TypeSafeSet;
import com.amshulman.typesafety.impl.TypeSafeSetImpl;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

public final class PumpkinVirusConfigurationContext extends ConfigurationContext {
    public final int ticks;
    public final boolean trackingPlayers;
    public final int maxHeightOffGround;
    public final Material virusBlockType;
    public final Material antiVirusBlockType;

    public final TypeSafeSet<String> worlds;

    public PumpkinVirusConfigurationContext(final MbapiPlugin plugin) {
        super(plugin);

        plugin.saveDefaultConfig();

        worlds = new TypeSafeSetImpl<>(new HashSet<String>(), SupplementaryTypes.STRING);

        // Retrieves the ticks between pumpkin spread attempts.
        ticks = plugin.getConfig().getInt("spreadSpeed", 5);
        // Retrieves whether or not pumpkins should attempt to track and kill players.
        trackingPlayers = plugin.getConfig().getBoolean("trackingPlayers", true);
        // Retrieves the maximum height pumpkins are allowed to get off the ground.
        maxHeightOffGround = plugin.getConfig().getInt("maxHeightOffGround", 5);

        // Retrieves the block type to use for the virus.
        virusBlockType = retrieveMaterialFromConfig("virusBlockType", Material.PUMPKIN);
        // Retrieves the block type to use for the anti-virus.
        antiVirusBlockType = retrieveMaterialFromConfig("antiVirusBlockType", Material.MELON);

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

    /**
     * Attempts to retrieve a material from the plugin configuration.
     * @param key the key for the material to be retrieved.
     */
    Material retrieveMaterialFromConfig(String key, Material defaultMaterial) {
        String materialString = plugin.getConfig().getString(key);

        if (materialString == null) {
            Bukkit.getLogger().log(Level.WARNING, "Invalid value for key: " + key);
            return defaultMaterial;
        }

        Material material = Material.getMaterial(materialString);

        if (material == null) {
            Bukkit.getLogger().log(Level.WARNING, "Invalid value for key: " + key);
            return defaultMaterial;
        }

        return material;
    }
}
