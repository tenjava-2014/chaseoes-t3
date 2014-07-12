package com.tenjava.entries.chaseoes.t3;

import org.bukkit.entity.Player;

public enum RainType {

    CAT_AND_DOG("cats and dogs"), ANVIL("anvils"), POTION("deadly potions"), DIAMOND("diamonds"), RAINBOW("rainbows"), EXPERIENCE("experience"), CREEPER("creepers");

    private final String name;

    RainType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void run(Player targetPlayer) {
        switch (this) {
        case CAT_AND_DOG:
            System.out.println(this.toString());
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
