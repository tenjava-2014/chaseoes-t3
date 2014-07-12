package com.tenjava.entries.chaseoes.t3;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomRain extends JavaPlugin {

    private static RandomRain instance;

    public static final String PREFIX = ChatColor.YELLOW + "[RandomRain] " + ChatColor.GRAY; // This is the basic formatting used to print messages to the player throughout the plugin.

    public static RandomRain getInstance() { // Keep an instance of our main class for easy access from other classes.
        return instance;
    }

    public void onEnable() {
        instance = this;

        getServer().getScheduler().runTaskTimer(this, new RainTask(), 0L, 6000L); // Run the rain task every 5 minutes.

        PluginCommand rainCommand = getCommand(getDescription().getName().toLowerCase());
        rainCommand.setExecutor(new RainCommandExecutor()); // Handle the "randomrain" command in the RainCommandExecutor class.
        rainCommand.setTabCompleter(new RainTabCompleter()); // Set the tab completer for the "randomrain" command.
    }

    public void onDisable() {
        instance = null;
    }

}
