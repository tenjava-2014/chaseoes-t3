package com.tenjava.entries.chaseoes.t3;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
    }

    public void onDisable() {
        instance = null;
    }

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (args.length == 0) {
            cs.sendMessage(PREFIX + "Plugin version " + getDescription().getVersion() + " by " + getDescription().getAuthors().get(0) + ".");
            cs.sendMessage(PREFIX + "Type " + ChatColor.RED + "/" + string + " help" + ChatColor.GRAY + " for help.");
            return true;
        }
        return true;
    }

}
