package fr.volax.valkyacore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class GlobalPlayerQuit implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
    }
}
