/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.spawners;

@Deprecated
public enum SpawnersState {
    BREAKED("break", "Cassé"),
    TRANSIT("transit", "En transit"),
    PLACED("placed", "Placé");

    private String name, displayName;

    SpawnersState(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
}
