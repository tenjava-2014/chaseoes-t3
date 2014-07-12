package com.tenjava.entries.chaseoes.t3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockIterator;

public enum RainType {

    CAT_AND_DOG("cats and dogs"), ANVIL("anvils"), POTION("deadly potions"), DIAMOND("diamonds"), RAINBOW("rainbows"), EXPERIENCE("experience"), CREEPER("creepers"), EGG("eggs");

    private final String name;
    private RainType rainType;

    RainType(String name) {
        this.name = name;
        this.rainType = this;
    }

    public String getName() {
        return name;
    }

    public void run(final Player targetPlayer) {
        final Random r = new Random();

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
                            alreadyDone.add(blockLocation);

                            float chance = r.nextFloat();
                            switch (rainType) {
                            case CAT_AND_DOG:
                                EntityType type = EntityType.OCELOT;
                                int randomTwo = r.nextInt(3);
                                if (chance <= 0.01f) {
                                    if (randomTwo == 2) {
                                        type = EntityType.WOLF;
                                    }

                                    final Entity spawnedEntity = blockLocation.getWorld().spawnEntity(blockLocation, type);
                                    RandomRain.getInstance().getServer().getScheduler().runTaskLater(RandomRain.getInstance(), new Runnable() {
                                        public void run() {
                                            spawnedEntity.remove();
                                        }
                                    }, 60L);
                                }
                                break;
                            case ANVIL:
                                if (chance <= 0.005f) {
                                    if (block.getType() == Material.AIR) {
                                        block.setType(Material.ANVIL);
                                    }
                                }
                                break;

                            case POTION:
                                if (chance <= 0.004f) {
                                    ThrownPotion entity = (ThrownPotion) blockLocation.getWorld().spawnEntity(blockLocation, EntityType.SPLASH_POTION);
                                    ItemStack potion = new Potion(PotionType.POISON).toItemStack(1);
                                    if (chance <= 0.003f) {
                                        potion = new Potion(PotionType.WEAKNESS).toItemStack(1);
                                    }

                                    if (chance <= 0.0001f) {
                                        potion = new Potion(PotionType.INSTANT_DAMAGE).toItemStack(1);
                                    }

                                    entity.setItem(potion);
                                }
                                break;

                            case DIAMOND:
                                if (chance <= 0.001f) {
                                    ItemStack drop = new ItemStack(Material.DIAMOND);
                                    if (chance <= 0.0001f) {
                                        drop = new ItemStack(Material.EMERALD);
                                    }

                                    blockLocation.getWorld().dropItem(blockLocation, drop);
                                }
                                break;

                            case RAINBOW:
                                if (chance <= 0.005f) {
                                    blockLocation.getWorld().playEffect(targetPlayer.getLocation(), Effect.POTION_BREAK, 1);
                                }
                                break;

                            case EXPERIENCE:
                                if (chance <= 0.005f) {
                                    ((ExperienceOrb) blockLocation.getWorld().spawn(blockLocation, ExperienceOrb.class)).setExperience(2);
                                }
                                break;

                            case CREEPER:
                                if (chance <= 0.001f) {
                                    Creeper creeper = (Creeper) blockLocation.getWorld().spawnEntity(blockLocation, EntityType.CREEPER);
                                    if (chance <= 0.0001f) {
                                        creeper.setPowered(true);
                                    }
                                }
                                break;

                            case EGG:
                                if (chance <= 0.006f) {
                                    blockLocation.getWorld().spawnEntity(blockLocation, EntityType.EGG);
                                }
                                break;

                            default:
                                break;
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
