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
        if (chance <= 0.30f) {
            int randomType = r.nextInt(RainType.values().length) + 1;
            Player target = getRandomPlayer();
            if (target != null) {
                RainType type = Arrays.asList(RainType.values()).get(randomType);
                type.run(target);
            }
        }
    }

    private Player getRandomPlayer() {
        Random r = new Random();
        int p = r.nextInt(RandomRain.getInstance().getServer().getOnlinePlayers().length) + 1;
        Player player = Arrays.asList(RandomRain.getInstance().getServer().getOnlinePlayers()).get(p);
        if (player != null) {
            return player;
        }
        return null;
    }

}
