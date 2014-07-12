package com.tenjava.entries.chaseoes.t3;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class RainTabCompleter implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> types = new ArrayList<String>();
        for (RainType type : RainType.values()) {
            types.add(type.toString().toLowerCase());
        }

        if (args.length == 1) {
            List<String> options = new ArrayList<String>();
            options.add("types");
            options.add("rain");
            return StringUtil.copyPartialMatches(args[0], options, new ArrayList<String>(options.size()));
        } else if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1], types, new ArrayList<String>(types.size()));
        } else if (args.length == 3) {
            List<String> onlinePlayers = new ArrayList<String>();
            for (Player player : RandomRain.getInstance().getServer().getOnlinePlayers()) {
                onlinePlayers.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[2], onlinePlayers, new ArrayList<String>(onlinePlayers.size()));
        }

        return new ArrayList<String>();
    }
}
