package com.tenjava.entries.chaseoes.t3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockIterator;

public enum RainType {

    CAT_AND_DOG("cats and dogs"), ANVIL("anvils"), POTION("deadly potions"), DIAMOND("diamonds"), RAINBOW("rainbows"), EXPERIENCE("experience"), CREEPER("creepers");

    private final String name;

    RainType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void run(final Player targetPlayer) {
        final Random r = new Random();

        switch (this) {
        case CAT_AND_DOG:
            targetPlayer.sendMessage(RandomRain.PREFIX + "It's raining " + this.getName() + "...");

            final BukkitTask task = RandomRain.getInstance().getServer().getScheduler().runTaskTimer(RandomRain.getInstance(), new Runnable() {
                public void run() {
                    float yaw = -180;
                    while (yaw != 180) {
                        List<Location> alreadyDone = new ArrayList<Location>();
                        Location bl = new Location(targetPlayer.getWorld(), targetPlayer.getLocation().getX(), targetPlayer.getLocation().getY(), targetPlayer.getLocation().getZ(), yaw, targetPlayer.getLocation().getPitch());
                        BlockIterator bi = new BlockIterator(bl, 15, 5);
                        while (bi.hasNext()) {
                            Block block = bi.next();
                            Location blockLocation = block.getLocation();
                            if (!alreadyDone.contains(blockLocation)) {
                                EntityType type = EntityType.OCELOT;
                                int randomTwo = r.nextInt(3);
                                float chance = r.nextFloat();
                                alreadyDone.add(blockLocation);

                                if (randomTwo == 2) {
                                    type = EntityType.WOLF;
                                }

                                if (chance <= 0.01f) {
                                    final Entity spawnedEntity = blockLocation.getWorld().spawnEntity(blockLocation, type);
                                    RandomRain.getInstance().getServer().getScheduler().runTaskLater(RandomRain.getInstance(), new Runnable() {
                                        public void run() {
                                            spawnedEntity.remove();
                                        }
                                    }, 60L);
                                }
                            }
                        }
                        yaw++;
                    }

                }
            }, 0L, 10L);

            RandomRain.getInstance().getServer().getScheduler().runTaskLater(RandomRain.getInstance(), new Runnable() {
                public void run() {
                    task.cancel();
                }
            }, 100L);

            break;
        case ANVIL:
            System.out.println(this.toString());
            break;

        case POTION:
            System.out.println(this.toString());
            break;

        case DIAMOND:
            System.out.println(this.toString());
            break;

        case RAINBOW:
            System.out.println(this.toString());
            break;

        case EXPERIENCE:
            System.out.println(this.toString());
            break;

        case CREEPER:
            System.out.println(this.toString());
            break;

        default:
            break;
        }

    }

    public static String getTypes() {
        StringBuilder sb = new StringBuilder();
        for (RainType type : RainType.values()) {
            sb.append(type.toString().toLowerCase() + ", ");
        }

        String types = sb.toString();
        return types.toString().substring(0, types.length() - 2);
    }

}
