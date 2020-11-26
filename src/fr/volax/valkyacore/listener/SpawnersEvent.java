/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;
import java.util.Random;

@Deprecated
public class SpawnersEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(block.getType() == Material.QUARTZ_BLOCK){
            block.setMetadata("spawnerID", new FixedMetadataValue(ValkyaCore.getInstance(), /*spawner.getID()*/ new Random().nextInt(100000)));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.AIR && event.getClickedBlock().getType() == Material.QUARTZ_BLOCK){
            List<MetadataValue> values = event.getClickedBlock().getMetadata("spawnerID");
            System.out.println(values.get(0).value());
        }
    }
}
