/**
 *
 * @author Indivisible0
 */
package com.github.Indiv0.PumpkinVirus;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

public class PumpkinVirus extends JavaPlugin {
    private final BlockPlaceListener blockPlaceListener = new BlockPlaceListener(this);
    // Stores whether or not pumpkins are currently spreading.
    private boolean isPumpkinSpreadEnabled = false;

    @Override
    public void onLoad() {
        // Enable PluginMetrics.
        enableMetrics();
    }

    @Override
    public void onEnable() {
        // Retrieves an instance of the PluginManager.
        PluginManager pm = getServer().getPluginManager();

        // Registers the blockListener with the PluginManager.
        pm.registerEvents(blockPlaceListener, this);

        // Prints a message to the server confirming successful initialization
        // of the plugin.
        PluginDescriptionFile pdfFile = getDescription();
        getLogger().info(pdfFile.getName() + " " + pdfFile.getVersion() + " is enabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Checks to see if the command is the "/pumpkinvirus" command.
        if (!cmd.getName().equalsIgnoreCase("pumpkinvirus")) return false;

        // Checks to make sure user has proper permissions.
        if (!sender.hasPermission("pumpkinvirus.use"))
            return false;

        // Makes sure at least one argument has been provided.
        if (args.length == 0) {
            isPumpkinSpreadEnabled = !isPumpkinSpreadEnabled;

            if (isPumpkinSpreadEnabled)
                sender.sendMessage("Pumpkins are now SPREADING!");
            else
                sender.sendMessage("Pumpkins are no longer spreading!");

            return true;
        }

        sender.sendMessage("To use PumpkinVirus, type \"/pumpkinvirus\" followed by no arguments.");

        return false;
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

    public boolean getPumpkinSpreadStatus() {
        return isPumpkinSpreadEnabled;
    }
}
