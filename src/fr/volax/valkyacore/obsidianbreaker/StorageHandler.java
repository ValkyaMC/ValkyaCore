/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.obsidianbreaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.exception.UnknownBlockTypeException;
import fr.volax.valkyacore.tools.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class StorageHandler {
  public ConcurrentHashMap<String, ConcurrentHashMap<String, BlockStatus>> damage;
  
  public StorageHandler() {
    this.damage = new ConcurrentHashMap<String, ConcurrentHashMap<String, BlockStatus>>();
  }
  
  private String generateBlockHash(Location loc) {
    return String.valueOf(loc.getWorld().getUID().toString()) + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
  }
  
  private String generateChunkHash(Chunk chunk) {
    return String.valueOf(chunk.getWorld().getUID().toString()) + ":" + chunk.getX() + ":" + chunk.getZ();
  }
  
  public Location generateLocation(String blockHash) {
    try {
      String[] s = blockHash.split(":");
      return new Location(Bukkit.getWorld(UUID.fromString(s[0])), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
    } catch (Exception e) {
      System.err.println("Couldn't generate hash from location (hash: " + blockHash + ") + e");
      return null;
    } 
  }
  
  public boolean isValidBlock(Block block) {
    try {
      for (String section : ValkyaCore.getInstance().getConfigBuilder().configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getConfigurationSection("Blocks").getKeys(false)) {
        String[] s = section.split(":");
        if (block.getTypeId() == Integer.parseInt(s[0]) && (s.length == 1 || block.getData() == Byte.parseByte(s[1])))
          return true; 
      } 
    } catch (Exception exception) {}
    return false;
  }
  
  public float getTotalDurabilityFromConfig(Block block) throws UnknownBlockTypeException {
    try {
      for (String section : ValkyaCore.getInstance().getConfigBuilder().configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getConfigurationSection("Blocks").getKeys(false)) {
        String[] s = section.split(":");
        if (block.getTypeId() == Integer.parseInt(s[0]) && (s.length == 1 || block.getData() == Byte.parseByte(s[1])))
          return (float)ValkyaCore.getInstance().getConfigBuilder().configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getDouble("Blocks." + section);
      } 
    } catch (Exception exception) {}
    throw new UnknownBlockTypeException();
  }
  
  public boolean addDamage(Block block, float addDamage) throws UnknownBlockTypeException {
    BlockStatus status = getBlockStatus(block, false);
    if (addDamage <= 0.0F)
      return false; 
    if (status == null) {
      if (getTotalDurabilityFromConfig(block) <= 0.0F)
        return false; 
      status = getBlockStatus(block, true);
      if (status == null)
        throw new UnknownBlockTypeException(); 
    } 
    status.setDamage(status.getDamage() + addDamage);
    if (status.getDamage() >= status.getMaxDamage() - 0.001F) {
      removeBlockStatus(status);
      return true;
    } 
    return false;
  }
  
  public BlockStatus getBlockStatus(Block block, boolean create) {
    try {
      String chunkHash = generateChunkHash(block.getLocation().getChunk());
      Map<String, BlockStatus> chunkMap = null;
      if (this.damage.containsKey(chunkHash)) {
        chunkMap = this.damage.get(chunkHash);
      } else if (create) {
        this.damage.put(chunkHash, new ConcurrentHashMap<String, BlockStatus>());
        chunkMap = this.damage.get(chunkHash);
      } else {
        return null;
      } 
      String blockHash = generateBlockHash(block.getLocation());
      if (chunkMap.containsKey(blockHash))
        return chunkMap.get(blockHash); 
      if (create) {
        chunkMap.put(blockHash, new BlockStatus(blockHash, chunkHash, getTotalDurabilityFromConfig(block)));
        return chunkMap.get(blockHash);
      } 
      return null;
    } catch (UnknownBlockTypeException e) {
      return null;
    } 
  }
  
  public void removeBlockStatus(BlockStatus blockStatus) {
    String chunkHash = blockStatus.getChunkHash();
    Map<String, BlockStatus> chunk = this.damage.get(chunkHash);
    if (chunk == null)
      return; 
    chunk.remove(blockStatus.getBlockHash());
    if (chunk.isEmpty())
      this.damage.remove(chunkHash); 
  }
  
  public List<BlockStatus> getNearbyBlocks(Chunk chunk, int chunkRadius) {
    ArrayList<BlockStatus> status = new ArrayList<BlockStatus>();
    for (int x = chunk.getX() - chunkRadius; x <= chunk.getX() + chunkRadius; x++) {
      for (int z = chunk.getZ() - chunkRadius; z <= chunk.getZ() + chunkRadius; z++) {
        String hash = String.valueOf(chunk.getWorld().getUID().toString()) + ":" + x + ":" + z;
        Map<String, BlockStatus> map = this.damage.get(hash);
        if (map != null)
          for (BlockStatus s : map.values())
            status.add(s);  
      } 
    } 
    return status;
  }
}