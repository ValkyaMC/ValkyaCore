package fr.volax.valkyacore.listener;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.obsidianbreaker.BlockIntersector;
import fr.volax.valkyacore.obsidianbreaker.BlockStatus;
import fr.volax.valkyacore.obsidianbreaker.StorageHandler;
import fr.volax.valkyacore.obsidianbreaker.UnknownBlockTypeException;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockListener implements Listener {

  private class BlockIteratorRunner extends BukkitRunnable {
      private Block block;
      private Location source;
      private EntityType explosive;

      private boolean isLiquid = false;

      private BlockIteratorRunner(Block block, Location source, EntityType explosive) {
          this.block = block;
          this.source = source;
          this.explosive = explosive;
      }

      public void run() {
          Location loc = this.block.getLocation();
          try {
              Iterator<Block> it = BlockIntersector.getIntersectingBlocks(this.source, loc).iterator();
              while (it.hasNext()) {
                  Block b = it.next();
                  if (b.isLiquid()) {
                      this.isLiquid = true;
                      break;
                  }
                  if (b.getType() == Material.BEDROCK && ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getBoolean("BedrockBlocking") && it.hasNext())
                      return;
              }
          } catch (Exception e) {
              if (this.source.getBlock().isLiquid())
                  this.isLiquid = true;
              System.err.println("Liquid detection system failed. Fell back to primitive detection. " + e);
          }
          (new BukkitRunnable() {
              public void run() {
                  removeBlock(BlockListener.BlockIteratorRunner.this.block, BlockListener.BlockIteratorRunner.this.isLiquid, BlockListener.BlockIteratorRunner.this.explosive);
              }
          }).runTask(ValkyaCore.getInstance());
      }
  }
  
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onEntityExplode(EntityExplodeEvent event) {
    if (event.getEntity() == null)
      return; 
    List<String> worlds = ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getStringList("DisabledWorlds");
    for (String world : worlds) {
      if (world.equalsIgnoreCase(event.getLocation().getWorld().getName()))
        return; 
    } 
    Iterator<Block> it = event.blockList().iterator();
    while (it.hasNext()) {
      Block block = it.next();
      if (ValkyaCore.getInstance().getStorage().isValidBlock(block))
        it.remove(); 
    } 
    float unalteredRadius = (float)ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getDouble("BlastRadius");
    int radius = (int)Math.ceil(unalteredRadius);
    Location detonatorLoc = event.getLocation();
    for (int x = -radius; x <= radius; x++) {
      for (int y = -radius; y <= radius; y++) {
        for (int z = -radius; z <= radius; z++) {
          Location targetLoc = new Location(detonatorLoc.getWorld(), detonatorLoc.getX() + x, detonatorLoc.getY() + y, detonatorLoc.getZ() + z);
          if (detonatorLoc.distance(targetLoc) <= unalteredRadius)
            explodeBlock(targetLoc, detonatorLoc, event.getEntityType()); 
        } 
      } 
    } 
  }
  
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onBlockBreak(BlockBreakEvent event) {
    StorageHandler storage = ValkyaCore.getInstance().getStorage();
    Block block = event.getBlock();
    BlockStatus status = storage.getBlockStatus(block, false);
    if (status != null) storage.removeBlockStatus(status);
  }
  
  void explodeBlock(Location loc, Location source, EntityType explosive) {
    if (!loc.getChunk().isLoaded() || (loc.getBlockY() == 0 && ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getBoolean("VoidProtector")))
      return; 
    Block block = loc.getWorld().getBlockAt(loc);
    if (ValkyaCore.getInstance().getStorage().isValidBlock(block)) {
      float liquidDivider = (float)ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getDouble("LiquidMultiplier");
      if (liquidDivider != 1.0F || ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getBoolean("BedrockBlocking")) new BlockIteratorRunner(block, source, explosive).runTask(ValkyaCore.getInstance());
      else removeBlock(block, false, explosive);
    } 
  }
  
  private void removeBlock(Block block, boolean isLiquid, EntityType explosive) {
    try {
      float liquidDivider = (float)ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getDouble("LiquidMultiplier");
      if (isLiquid && liquidDivider <= 0.0F)
        return; 
      float rawDamage = (explosive == null) ? 1.0F : (float)ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getDouble("ExplosionSources." + explosive.toString());
      if (ValkyaCore.getInstance().getStorage().addDamage(block, isLiquid ? (rawDamage / liquidDivider) : rawDamage)) {
        List<String> list = (List<String>) ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getList("Drops.DontDrop");
        for (Object section : list) {
          if (section instanceof Integer)
            section = Integer.toString(((Integer)section).intValue()); 
          String[] s = ((String)section).split(":");
          if (block.getTypeId() == Integer.parseInt(s[0]) && (s.length == 1 || block.getData() == Byte.parseByte(s[1]))) {
            block.setType(Material.AIR);
            return;
          } 
        } 
        if ((new Random()).nextInt(100) + 1 >= ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getInt("Drops.DropChance")) {
          block.setType(Material.AIR);
        } else {
          block.breakNaturally();
        } 
      }
    } catch (UnknownBlockTypeException unknownBlockTypeException) {}
  }
}