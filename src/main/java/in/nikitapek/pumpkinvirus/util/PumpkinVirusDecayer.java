package in.nikitapek.pumpkinvirus.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class PumpkinVirusDecayer implements Runnable {
    private static PumpkinVirusConfigurationContext configurationContext;

    private final Block block;
    private final Material initialMaterial;

    public PumpkinVirusDecayer(Block block) {
        this.block = block;
        this.initialMaterial = block.getType();
    }

    public static void initialize(PumpkinVirusConfigurationContext configurationContext) {
        PumpkinVirusDecayer.configurationContext = configurationContext;
    }

    @Override
    public void run() {
        // If this block has been changed since the spread began (e.g. anti-virus having been removed), then the decay fails.
        if (!initialMaterial.equals(block.getType())) {
            return;
        }

        // Decays the current block.
        block.setType(Material.AIR);
    }

    public static void decayBlock(Block block, int decayTime) {
        // Creates an sync task, which when run, decays a block.
        Bukkit.getScheduler().scheduleSyncDelayedTask(configurationContext.plugin, new PumpkinVirusDecayer(block), decayTime);
    }
}
