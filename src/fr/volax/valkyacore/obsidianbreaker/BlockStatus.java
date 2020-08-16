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
    return this.damage;
  }
  public void setDamage(float damage) {
    this.damage = damage;
  }
  public boolean isModified() {
    return this.modified;
  }
  public void setModified(boolean modified) {
    this.modified = modified;
  }
  public float getTotalDurability() {
    return this.maxDamage;
  }
  String getBlockHash() {
    return this.blockHash;
  }
  String getChunkHash() {
    return this.chunkHash;
  }
}
