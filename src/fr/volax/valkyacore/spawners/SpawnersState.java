/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.spawners;

import lombok.Getter;

@Deprecated
public enum SpawnersState {
    BREAKED("break", "Cassé"),
    TRANSIT("transit", "En transit"),
    PLACED("placed", "Placé");

    @Getter
    private String name, displayName;

    SpawnersState(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
}
