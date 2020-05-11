package fr.volax.valkyacore.listeners;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class AntiSpamPlayerQuit implements Listener {
    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        ValkyaCore.getInstance().cooldown.remove(event.getPlayer().getUniqueId());
    }
}
