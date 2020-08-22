/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.obsidianbreaker;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class BlockIntersector {
  public static List<Block> getIntersectingBlocks(Location explosionSource, Location block) {
    List<Block> list = new ArrayList<Block>();
    int pixelX = explosionSource.getBlockX();
    int pixelY = explosionSource.getBlockY();
    int pixelZ = explosionSource.getBlockZ();
    int dx = block.getBlockX() - explosionSource.getBlockX();
    int dy = block.getBlockY() - explosionSource.getBlockY();
    int dz = block.getBlockZ() - explosionSource.getBlockZ();
    int x_inc = (dx < 0) ? -1 : 1;
    int l = Math.abs(dx);
    int y_inc = (dy < 0) ? -1 : 1;
    int m = Math.abs(dy);
    int z_inc = (dz < 0) ? -1 : 1;
    int n = Math.abs(dz);
    int dx2 = l << 1;
    int dy2 = m << 1;
    int dz2 = n << 1;
    if (l >= m && l >= n) {
      int err_1 = dy2 - l;
      int err_2 = dz2 - l;
      for (int i = 0; i < l; i++) {
        list.add(explosionSource.getWorld().getBlockAt(pixelX, pixelY, pixelZ));
        if (err_1 > 0) {
          pixelY += y_inc;
          err_1 -= dx2;
        } 
        if (err_2 > 0) {
          pixelZ += z_inc;
          err_2 -= dx2;
        } 
        err_1 += dy2;
        err_2 += dz2;
        pixelX += x_inc;
      } 
    } else if (m >= l && m >= n) {
      int err_1 = dx2 - m;
      int err_2 = dz2 - m;
      for (int i = 0; i < m; i++) {
        list.add(explosionSource.getWorld().getBlockAt(pixelX, pixelY, pixelZ));
        if (err_1 > 0) {
          pixelX += x_inc;
          err_1 -= dy2;
        } 
        if (err_2 > 0) {
          pixelZ += z_inc;
          err_2 -= dy2;
        } 
        err_1 += dx2;
        err_2 += dz2;
        pixelY += y_inc;
      } 
    } else {
      int err_1 = dy2 - n;
      int err_2 = dx2 - n;
      for (int i = 0; i < n; i++) {
        list.add(explosionSource.getWorld().getBlockAt(pixelX, pixelY, pixelZ));
        if (err_1 > 0) {
          pixelY += y_inc;
          err_1 -= dz2;
        } 
        if (err_2 > 0) {
          pixelX += x_inc;
          err_2 -= dz2;
        } 
        err_1 += dy2;
        err_2 += dx2;
        pixelZ += z_inc;
      } 
    } 
    list.add(explosionSource.getWorld().getBlockAt(pixelX, pixelY, pixelZ));
    return list;
  }
}


/* Location:              C:\Users\flo31\Desktop\ObsidianBreaker.jar!\com\creeperevents\oggehej\obsidianbreaker\BlockIntersector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */