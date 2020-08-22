/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class GlobalPlayerQuit implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        ValkyaCore.getInstance().cooldown.remove(event.getPlayer().getUniqueId());
        event.setQuitMessage(null);
    }
}
