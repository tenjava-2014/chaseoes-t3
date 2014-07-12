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

}
