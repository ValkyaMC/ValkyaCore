/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.spawners;

import lombok.Getter;

import java.util.HashMap;

@Deprecated
public enum SpawnersEnum {
    ZOMBIE("Zombies", "zombie",4649),
    SKELETON("Squelettes","skeleton", 4571),
    IRONGOLEMS("Iron Golem", "irongolems",4573),
    PIGMEN("Pigmen", "pigmen",4643),
    SPIDER("Araignées", "spider",4626),
    COW("Vaches", "cow",4575);

    @Getter private String displayName, name;
    @Getter private int id;
    private static HashMap<String, SpawnersEnum> NAME = new HashMap();

    SpawnersEnum(String displayName, String name, int id) {
        this.displayName = displayName;
        this.name = name;
        this.id = id;
    }

    public static boolean existFromName(String name) {
        return NAME.containsKey(name);
    }

    public static int getIDByName(String name){
        return NAME.get(name).id;
    }

    public static String translateNameToDisplayName(String name) {
        return NAME.get(name).displayName;
    }

    static {
        SpawnersEnum[] spawnersEnums = values();
        for (SpawnersEnum spawnersEnum : spawnersEnums)
            NAME.put(spawnersEnum.name, spawnersEnum);
    }
}
