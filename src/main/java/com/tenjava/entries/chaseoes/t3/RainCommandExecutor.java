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
            // If no arguments are given, give some general information about the plugin, like it's name, version, and author.
            cs.sendMessage(RandomRain.PREFIX + "Plugin version " + RandomRain.getInstance().getDescription().getVersion() + " by " + RandomRain.getInstance().getDescription().getAuthors().get(0) + ".");
            cs.sendMessage(RandomRain.PREFIX + "Type " + ChatColor.RED + "/" + string + " help" + ChatColor.GRAY + " for help.");
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            // We can give some help messages in-game as to how to use the plugin.
            cs.sendMessage(RandomRain.PREFIX + "By default, " + RandomRain.getInstance().getDescription().getName() + " will randomly make it \"rain\" upon players, with a random type of rain.");
            cs.sendMessage(RandomRain.PREFIX + "To view the types of rain, use: " + ChatColor.RED + "/" + string + " types");
            cs.sendMessage(RandomRain.PREFIX + "To manually make it rain: " + ChatColor.RED + "/" + string + " rain <rain type> [player]");
            return true;
        }

        if (args[0].equalsIgnoreCase("types")) {
            // Use our method that returns a comma separated list of rain types and give them to the player, for reference in future commands.
            cs.sendMessage(RandomRain.PREFIX + "Available types: " + ChatColor.RED + RainType.getTypes());
            return true;
        }

        if (args[0].equalsIgnoreCase("rain")) {
            if (cs.hasPermission("randomrain.admin")) { // Make sure the command sender has permission to use the command.
                if (args.length > 1) { // Check that they're actually giving us enough information.
                    try { // Make sure it's a valid type, as it'll throw an exception if we try to get an invalid type, then convert it to the proper enum value from a string.
                        RainType rainType = RainType.valueOf(args[1].toUpperCase());
                        Player player = null;

                        // If they provide a fourth argument with a valid player name, use it instead of their own.
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

                        // Make sure the player is online.
                        if (player != null) {
                            rainType.run(player); // Run the rain effect on the specified player.
                            return true;
                        } else {
                            cs.sendMessage(RandomRain.PREFIX + ChatColor.RED + "That player isn't online.");
                        }
                    } catch (IllegalArgumentException e) {
                        // Not a real type of rain? Let's give them the available types to choose from.
                        cs.sendMessage(RandomRain.PREFIX + "That's not a valid type of rain!");
                        cs.sendMessage(RandomRain.PREFIX + "Available types: " + ChatColor.RED + RainType.getTypes());
                    }
                } else {
                    // Print some usage information to help them out.
                    cs.sendMessage(RandomRain.PREFIX + "Usage: " + ChatColor.RED + "/" + string + " rain <rain type> [player]");
                }
            } else {
                cs.sendMessage(RandomRain.PREFIX + ChatColor.RED + "You don't have permission to use that command.");
            }
        } else {
            // Provide some useful help information if we don't recognize that argument.
            cs.sendMessage(RandomRain.PREFIX + "Unknown command!");
            cs.sendMessage(RandomRain.PREFIX + "Type " + ChatColor.RED + "/" + string + " help" + ChatColor.GRAY + " for help.");
        }
        return true;
    }

}
