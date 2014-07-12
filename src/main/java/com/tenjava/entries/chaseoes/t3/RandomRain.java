package com.tenjava.entries.chaseoes.t3;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomRain extends JavaPlugin {

    private static RandomRain instance;

    public static RandomRain getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
    }

    public void onDisable() {
        instance = null;
    }

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        return true;
    }

}
