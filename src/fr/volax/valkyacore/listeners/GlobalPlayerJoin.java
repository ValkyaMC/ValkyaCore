package fr.volax.valkyacore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GlobalPlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
    }
}
