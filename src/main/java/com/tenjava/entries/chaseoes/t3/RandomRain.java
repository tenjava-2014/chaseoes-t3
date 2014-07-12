package com.tenjava.entries.chaseoes.t3;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

    // getPlayer() is deprecated. :(
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (args.length == 0) {
            cs.sendMessage(PREFIX + "Plugin version " + getDescription().getVersion() + " by " + getDescription().getAuthors().get(0) + ".");
            cs.sendMessage(PREFIX + "Type " + ChatColor.RED + "/" + string + " help" + ChatColor.GRAY + " for help.");
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            return true;
        }

        if (args[0].equalsIgnoreCase("types")) {
            cs.sendMessage(PREFIX + "Available types: " + ChatColor.RED + RainType.getTypes());
            return true;
        }

        if (args[0].equalsIgnoreCase("rain")) { // /rr rain <rain type> [player]
            if (args.length > 1) {
                RainType rainType = RainType.valueOf(args[1].toUpperCase());
                if (rainType != null) {
                    Player player = null;
                    if (args.length == 3) {
                        player = getServer().getPlayer(args[2]);
                    } else {
                        if (cs instanceof Player) {
                            player = (Player) cs;
                        } else {
                            cs.sendMessage(PREFIX + ChatColor.RED + "You must be a player to do that.");
                            return true;
                        }
                    }

                    if (player != null) {
                        cs.sendMessage(rainType.getName() + player.getDisplayName());
                    } else {
                        cs.sendMessage(PREFIX + ChatColor.RED + "That player isn't online.");
                    }
                } else {
                    cs.sendMessage(PREFIX + ChatColor.RED + "That's not a valid type of rain!");
                    cs.sendMessage(PREFIX + "Available types: " + ChatColor.RED + RainType.getTypes());
                }
            } else {
                cs.sendMessage(PREFIX + "Usage: " + ChatColor.RED + "/" + string + " rain <rain type> [player]");
            }
        }
        return true;
    }

}
