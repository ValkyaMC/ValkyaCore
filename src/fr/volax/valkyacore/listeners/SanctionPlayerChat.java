package fr.volax.valkyacore.listeners;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class SanctionPlayerChat implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        ValkyaCore.getInstance().getMuteManager().checkDuration(playerUUID);
        if(ValkyaCore.getInstance().getMuteManager().isMuted(playerUUID)){
            event.setCancelled(true);
            player.sendMessage(ValkyaCore.PREFIX + " §cVous êtes mute pour §6" + ValkyaCore.getInstance().getMuteManager().getReason(playerUUID));
            player.sendMessage(ValkyaCore.PREFIX + " §aTemps restant: §f" + ValkyaCore.getInstance().getMuteManager().getTimeLeft(playerUUID));

        }
    }
}
