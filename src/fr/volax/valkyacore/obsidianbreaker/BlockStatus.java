/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.obsidianbreaker;

public class BlockStatus {
    private float damage = 0.0F, maxDamage;
    private boolean modified = true;
    private final String blockHash, chunkHash;
  
  BlockStatus(String blockHash, String chunkHash, float maxDamage) {
    this.blockHash = blockHash;
    this.chunkHash = chunkHash;
    this.maxDamage = maxDamage;
  }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(float maxDamage) {
        this.maxDamage = maxDamage;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public String getChunkHash() {
        return chunkHash;
    }
}
