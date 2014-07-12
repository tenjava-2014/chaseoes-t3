package com.tenjava.entries.chaseoes.t3;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// getPlayer() is deprecated. :(
@SuppressWarnings("deprecation")
public class RainCommandExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (args.length == 0) {
            cs.sendMessage(RandomRain.PREFIX + "Plugin version " + RandomRain.getInstance().getDescription().getVersion() + " by " + RandomRain.getInstance().getDescription().getAuthors().get(0) + ".");
            cs.sendMessage(RandomRain.PREFIX + "Type " + ChatColor.RED + "/" + string + " help" + ChatColor.GRAY + " for help.");
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            cs.sendMessage(RandomRain.PREFIX + "By default, " + RandomRain.getInstance().getDescription().getName() + " will randomly make it \"rain\" upon players, with a random type of rain.");
            cs.sendMessage(RandomRain.PREFIX + "To view the types of rain, use: " + ChatColor.RED + "/" + string + " types");
            cs.sendMessage(RandomRain.PREFIX + "To manually make it rain: " + ChatColor.RED + "/" + string + " rain <rain type> [player]");
            return true;
        }

        if (args[0].equalsIgnoreCase("types")) {
            cs.sendMessage(RandomRain.PREFIX + "Available types: " + ChatColor.RED + RainType.getTypes());
            return true;
        }

        if (args[0].equalsIgnoreCase("rain")) {
            if (args.length > 1) {
                RainType rainType = RainType.valueOf(args[1].toUpperCase());
                if (rainType != null) {
                    Player player = null;
                    if (args.length == 3) {
                        player = RandomRain.getInstance().getServer().getPlayer(args[2]);
                    } else {
                        if (cs instanceof Player) {
                            player = (Player) cs;
                        } else {
                            cs.sendMessage(RandomRain.PREFIX + ChatColor.RED + "You must be a player to do that.");
                            return true;
                        }
                    }

                    if (player != null) {
                        cs.sendMessage(rainType.getName() + " " + player.getDisplayName());
                        rainType.run(player);
                        return true;
                    } else {
                        cs.sendMessage(RandomRain.PREFIX + ChatColor.RED + "That player isn't online.");
                    }
                } else {
                    cs.sendMessage(RandomRain.PREFIX + ChatColor.RED + "That's not a valid type of rain!");
                    cs.sendMessage(RandomRain.PREFIX + "Available types: " + ChatColor.RED + RainType.getTypes());
                }
            } else {
                cs.sendMessage(RandomRain.PREFIX + "Usage: " + ChatColor.RED + "/" + string + " rain <rain type> [player]");
            }
        }
        return true;
    }

}
