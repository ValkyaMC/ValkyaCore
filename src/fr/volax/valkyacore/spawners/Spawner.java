/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.spawners;

import org.bukkit.World;

public class Spawner {
    private String owner;
    private double X, Y,  Z;
    private World world;
    private int materialId, id;
    private boolean isDestroy;
    private SpawnersState state;

    public Spawner(String owner, int materialId, SpawnersState state, boolean isDestroy) {
        this.owner = owner;
        this.materialId = materialId;
        this.state = state;
        this.isDestroy = isDestroy;
    }

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getX() {
        return X;
    }
    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }
    public void setY(double y) {
        Y = y;
    }

    public double getZ() {
        return Z;
    }
    public void setZ(double z) {
        Z = z;
    }

    public World getWorld() {
        return world;
    }
    public void setWorld(World world) {
        this.world = world;
    }

    public int getMaterialId() {
        return materialId;
    }
    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }

    public SpawnersState getState() {
        return state;
    }

    public void setState(SpawnersState state) {
        this.state = state;
    }

    public boolean isState(SpawnersState state){
        return state != this.state;
    }
}
