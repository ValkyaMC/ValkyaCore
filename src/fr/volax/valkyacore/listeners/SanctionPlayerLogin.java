package fr.volax.valkyacore.listeners;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class SanctionPlayerLogin implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        ValkyaCore.getInstance().getBanManager().checkDuration(player.getUniqueId());
        if(ValkyaCore.getInstance().getBanManager().isBanned(player.getUniqueId())){
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            event.setKickMessage("§cVous êtes banni ! \n" + "\n" + "§6Raison:§f " + ValkyaCore.getInstance().getBanManager().getReason(player.getUniqueId()) + "\n" + "§aTemps restant: §f" + ValkyaCore.getInstance().getBanManager().getTimeLeft(player.getUniqueId()));
        }
    }
}
