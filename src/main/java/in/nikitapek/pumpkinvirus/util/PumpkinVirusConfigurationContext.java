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
    public final int virusDecayTime;
    public final int antiVirusDecayTime;
    public final boolean virusBurrowing;
    public final double playerTrackingRadiusSquared;

    public final TypeSafeSet<String> worlds;
    public final TypeSafeSet<Material> nonSupportingBlocks;
    public final TypeSafeSet<Material> burrowableBlocks;

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

        // Retrieves the ticks before anti-virus decay begins.
        virusDecayTime = plugin.getConfig().getInt("virusDecayTime", 0);
        // Retrieves the ticks before anti-virus decay begins.
        antiVirusDecayTime = plugin.getConfig().getInt("antiVirusDecayTime", 5000);
        // Retrieves whether or not pumpkins should be allowed to burrow through blocks.
        virusBurrowing = plugin.getConfig().getBoolean("virusBurrowing", true);
        // Retrieves the radius within which to track players.
        playerTrackingRadiusSquared = Math.pow(plugin.getConfig().getDouble("playerTrackingRadius", 50), 2);

        // Attempts to read the configurationSection containing the worlds in which pumpkins are allowed to spread.
        @SuppressWarnings("unchecked")
        List<String> worldList = (List<String>) plugin.getConfig().getList("worlds");
        if (worldList == null) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load pumpkin-enabled worlds list.");
        } else {
            for (String world : worldList) {
                worlds.add(world);
            }
        }

        nonSupportingBlocks = getMaterialsFromConfigurationSection("nonSupportingBlocks");
        burrowableBlocks = getMaterialsFromConfigurationSection("burrowableBlocks");
    }

    /**
     * Attempts to retrieve a material from the plugin configuration.
     * @param key the key for the material to be retrieved.
     */
    private Material retrieveMaterialFromConfig(String key, Material defaultMaterial) {
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


    private TypeSafeSetImpl<Material> getMaterialsFromConfigurationSection(String sectionName) {
        TypeSafeSetImpl<Material> materials = new TypeSafeSetImpl<>(new HashSet<Material>(), SupplementaryTypes.MATERIAL);
        List<String> materialsList = (List<String>) plugin.getConfig().getList(sectionName);
        if (materialsList == null) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load " + sectionName + " list.");
        } else {
            for (String materialName : materialsList) {
                materials.add(Material.getMaterial(materialName));
            }
        }

        return materials;
    }
}
