package com.github.Indiv0.PumpkinVirus;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

public class PumpkinVirus extends JavaPlugin {
    @Override
    public void onLoad() {
        // Enable PluginMetrics.
        enableMetrics();
    }

    @Override
    public void onEnable() {
        // Prints a message to the server confirming successful initialization
        // of the plugin.
        PluginDescriptionFile pdfFile = getDescription();
        getLogger().info(pdfFile.getName() + " " + pdfFile.getVersion() + " is enabled.");
    }

    private void enableMetrics()
    {
        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException ex) {
            logException(ex, Level.WARNING, "An error occured while attempting to connect to PluginMetrics.");
        }
    }

    public void logException(Exception ex, Level level, String message) {
        ex.printStackTrace(System.out);
        getLogger().log(level, message);
    }
}
