/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.spawners;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;

@Deprecated
public class Spawner {
    @Getter @Setter private String owner;
    @Getter @Setter private double X, Y, Z;
    @Getter @Setter private World world;
    @Getter @Setter private int materialId, id;
    @Getter @Setter private boolean isDestroy;
    @Getter @Setter private SpawnersState state;

    public Spawner(String owner, int materialId, SpawnersState state, boolean isDestroy) {
        this.owner = owner;
        this.materialId = materialId;
        this.state = state;
        this.isDestroy = isDestroy;
    }
}
