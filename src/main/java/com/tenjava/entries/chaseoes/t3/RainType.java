package com.tenjava.entries.chaseoes.t3;

public enum RainType {

    CAT_AND_DOG("cats and dogs"), ANVIL("anvils"), POTION("deadly potions"), DIAMOND("diamonds"), RAINBOW("rainbows"), EXPERIENCE("experience"), CREEPER("creepers");

    private final String name;

    RainType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
