package com.tenjava.entries.chaseoes.t3;

import java.util.Random;

import org.bukkit.scheduler.BukkitRunnable;

public class RainTask extends BukkitRunnable {

    /**
     * This task runs every 5 minutes. Each time it runs, there will be a 10% chance of actually triggering some custom rain.
     */
    public void run() {
        Random r = new Random();
        float chance = r.nextFloat();
        if (chance <= 0.10f) {

        }
    }

}
