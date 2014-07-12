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

    // All the different types of random rain!
    CAT_AND_DOG("cats and dogs"), ANVIL("anvils"), POTION("deadly potions"), DIAMOND("diamonds"), RAINBOW("rainbows"), EXPERIENCE("experience"), CREEPER("creepers"), EGG("eggs");

    private final String name;
    private RainType rainType;

    RainType(String name) {
        this.name = name;
        this.rainType = this;
    }

    /**
     * Returns the "pretty" name of the rain type. For example: getName() on CAT_AND_DOG would return "cats and dogs".
     * 
     * @return the pretty name of the rain type.
     */
    public String getName() {
        return name;
    }

    /**
     * Runs the specified rain type on the provided player. For example: Calling RainType.ANVIL.run(player) would run the anvil rain effect on the given player.
     */
    public void run(final Player targetPlayer) {
        final Random r = new Random();

        targetPlayer.sendMessage(RandomRain.PREFIX + "It's raining " + this.getName() + "...");

        // This is a task to give the effect of rain. It runs every 10 ticks and is canceled after 5 seconds.
        // Thus, new "rainfall" is created every 10 ticks and lasts for 5 seconds total.
        final BukkitTask task = RandomRain.getInstance().getServer().getScheduler().runTaskTimer(RandomRain.getInstance(), new Runnable() {
            public void run() {
                float yaw = -180;

                // This is how we're getting a radius of blocks 5 above the given player.
                // We're then using the locations of those blocks as the orgin of many of the effects, such as spawning a creeper.
                // We're using a block iterator going out in all directions (the yaw) above a player.
                while (yaw != 180) {
                    List<Location> alreadyDone = new ArrayList<Location>();
                    Location bl = new Location(targetPlayer.getWorld(), targetPlayer.getLocation().getX(), targetPlayer.getLocation().getY(), targetPlayer.getLocation().getZ(), yaw, targetPlayer.getLocation().getPitch());
                    BlockIterator bi = new BlockIterator(bl, 15, 5); // Create the block iterator, using the location determined from the current yaw and starting 15 blocks above the player, with a radius of 5.
                    while (bi.hasNext()) { // We've gotten our radius of blocks, now to loop through them and use their locations.
                        Block block = bi.next();
                        Location blockLocation = block.getLocation();
                        if (!alreadyDone.contains(blockLocation)) { // Just to make sure we haven't already done something at this location, as it's likely we'll get duplicate locations.
                            alreadyDone.add(blockLocation);

                            // Create a random float which is used throughout the rest of the effects, because we don't want to spawn something on *every* block above the player.
                            float chance = r.nextFloat();
                            switch (rainType) { // Let's check what rain type we need then do stuff as needed.

                            // Raining cats and dogs!
                            case CAT_AND_DOG:
                                EntityType type = EntityType.OCELOT; // Start out by spawning an ocelot.
                                int randomTwo = r.nextInt(3);
                                if (chance <= 0.01f) {
                                    if (randomTwo == 2) {
                                        type = EntityType.WOLF; // But, we'll give it a ~50% chance to also be a dog!
                                    }

                                    final Entity spawnedEntity = blockLocation.getWorld().spawnEntity(blockLocation, type); // Spawn the final dog/cat.

                                    // Create a task that will remove the dog/cat 60 ticks later, after it's fallen from the sky, because we don't want to leave them behind.
                                    RandomRain.getInstance().getServer().getScheduler().runTaskLater(RandomRain.getInstance(), new Runnable() {
                                        public void run() {
                                            spawnedEntity.remove();
                                        }
                                    }, 60L);
                                }
                                break;

                            // Raining anvils!
                            case ANVIL:
                                if (chance <= 0.005f) { // Here's our random chance used in action, since we don't want hundreds of anvils falling, just a few.
                                    if (block.getType() == Material.AIR) { // We don't want to destroy anything player-created, even though they'll have to clean up some anvils.
                                        block.setType(Material.ANVIL);
                                    }
                                }
                                break;

                            // Raining poison, weakness, and instant damage splash potions!
                            case POTION:
                                if (chance <= 0.004f) {
                                    // Create the initial potion entity as a poison potion.
                                    ThrownPotion entity = (ThrownPotion) blockLocation.getWorld().spawnEntity(blockLocation, EntityType.SPLASH_POTION);
                                    ItemStack potion = new Potion(PotionType.POISON).toItemStack(1);

                                    // Give it a small chance to be a weakness potion.
                                    if (chance <= 0.003f) {
                                        potion = new Potion(PotionType.WEAKNESS).toItemStack(1);
                                    }

                                    // / ...and an even smaller chance to be an instant damage potion, to give the player a better chance of survival.
                                    if (chance <= 0.0001f) {
                                        potion = new Potion(PotionType.INSTANT_DAMAGE).toItemStack(1);
                                    }

                                    // Set the falling potion entity to be the correct type.
                                    entity.setItem(potion);
                                }
                                break;

                            // Raining diamonds (and emeralds)!
                            case DIAMOND:
                                if (chance <= 0.001f) { // We don't want to give away too many free diamonds.
                                    ItemStack drop = new ItemStack(Material.DIAMOND);
                                    if (chance <= 0.0001f) { // Let's have an even smaller chance of dropping an emerald instead of a diamond.
                                        drop = new ItemStack(Material.EMERALD);
                                    }

                                    blockLocation.getWorld().dropItem(blockLocation, drop); // Drop the final diamond or emerald.
                                }
                                break;

                            // Purple rainbow potion swirls!
                            case RAINBOW:
                                if (chance <= 0.005f) {
                                    // Simple but cool (and scary if you don't expect it), this creates the swirly potion effect and noise at the player's feet.
                                    blockLocation.getWorld().playEffect(targetPlayer.getLocation(), Effect.POTION_BREAK, 1);
                                }
                                break;

                            // Experience orbs raining from the sky!
                            case EXPERIENCE:
                                if (chance <= 0.005f) {
                                    // We spawn the experience orb above the player and set it's experience so the player actually gets something and it's not just visual.
                                    ((ExperienceOrb) blockLocation.getWorld().spawn(blockLocation, ExperienceOrb.class)).setExperience(2);
                                }
                                break;

                            // Creepers can rain from the sky too!
                            case CREEPER:
                                if (chance <= 0.001f) {
                                    // Create a creeper entity above the player.
                                    Creeper creeper = (Creeper) blockLocation.getWorld().spawnEntity(blockLocation, EntityType.CREEPER);
                                    if (chance <= 0.0001f) { // For fun, let's give it a small chance of being a powered creeper!
                                        creeper.setPowered(true);
                                    }
                                }
                                break;

                            // Why not some eggs? They're fun when you get hit by them.
                            case EGG:
                                if (chance <= 0.006f) {
                                    // Spawn the egg entity above the player.
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

        // This is our task to end the "rainfall" effect about 5 seconds after it starts.
        RandomRain.getInstance().getServer().getScheduler().runTaskLater(RandomRain.getInstance(), new Runnable() {
            public void run() {
                task.cancel();
            }
        }, 100L);

    }

    /**
     * Returns a friendly, comma separated list of all the available rainfall types.
     * 
     * @return a string with the lowecase names of all RainType items, comma separated.
     */
    public static String getTypes() {
        StringBuilder sb = new StringBuilder(); // Create a string builder and append each enum item name to it.
        for (RainType type : RainType.values()) {
            sb.append(type.toString().toLowerCase() + ", "); // Make it lowercase and add a comma for readability.
        }

        String types = sb.toString();
        return types.toString().substring(0, types.length() - 2); // We use a substring to remove the trailing comma and space at the end.
    }

}
