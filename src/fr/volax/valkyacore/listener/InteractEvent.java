/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getItem() == null || event.getItem().getType() == Material.AIR) return;
        if(event.getItem().getType() == Material.getMaterial(4631)){
            event.getPlayer().getInventory().remove(event.getItem());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "faddpoints "+event.getPlayer().getName()+ " 1");
        }
    }
}
