package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DatabasePlayerJoin implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        ValkyaCore.getInstance().getPlayerUtils().update(event.getPlayer());
    }
}