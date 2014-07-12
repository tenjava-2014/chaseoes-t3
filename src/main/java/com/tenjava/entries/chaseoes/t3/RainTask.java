package com.tenjava.entries.chaseoes.t3;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RainTask extends BukkitRunnable {

    /**
     * This task runs every 5 minutes. Each time it runs, there will be a 30% chance of actually triggering some custom rain.
     */
    public void run() {
        Random r = new Random();
        float chance = r.nextFloat();
        if (chance <= 0.30f) { // We don't want someone to suffer *every* 5 minutes, so we give a 30% chance of actually raining on someone.
            int randomType = r.nextInt(RainType.values().length) + 1; // Get a random number which will be used to get a random rainfall type.
            Player target = getRandomPlayer();
            if (target != null) { // We want to make sure they're not null, which getRandomPlayer() returns if no players are online.
                RainType type = Arrays.asList(RainType.values()).get(randomType);
                type.run(target); // Run the effect on the random player.
            }
        }
    }

    /**
     * Picks out a random player from all players currently online.
     * 
     * @return the Player object for a random online player or null if there is no players online
     */
    private Player getRandomPlayer() {
        Random r = new Random();
        int p = r.nextInt(RandomRain.getInstance().getServer().getOnlinePlayers().length); // Get a random number within the amount of online players.

        // Return null of no players are online.
        if (RandomRain.getInstance().getServer().getOnlinePlayers().length == 0) {
            return null;
        }

        Player player = Arrays.asList(RandomRain.getInstance().getServer().getOnlinePlayers()).get(p); // Use that number we just got to get that player from the online player list.
        return player;
    }

}
