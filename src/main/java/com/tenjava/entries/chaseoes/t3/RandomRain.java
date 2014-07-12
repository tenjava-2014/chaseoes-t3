package com.tenjava.entries.chaseoes.t3;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomRain extends JavaPlugin {

    private static RandomRain instance;

    public static final String PREFIX = ChatColor.YELLOW + "[RandomRain] " + ChatColor.GRAY;

    public static RandomRain getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;

        getServer().getScheduler().runTaskTimer(this, new RainTask(), 0L, 6000L); // Run the rain task every 5 minutes.
        getCommand(getDescription().getName().toLowerCase()).setExecutor(new RainCommandExecutor()); // Handle the "randomrain" command in the RainCommandExecutor class.
    }

    public void onDisable() {
        instance = null;
    }

}
