package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ModerationQuit implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();

        if(PlayerManager.isInModerationMod(player)){
            PlayerManager pm = PlayerManager.getFromPlayer(player);
            ValkyaCore.getInstance().staff.remove(player.getUniqueId());
            player.getInventory().clear();
            pm.giveInventory();
            pm.destroy();
            if(player.getGameMode() != GameMode.CREATIVE){
                player.setAllowFlight(false);
                player.setFlying(false);
            }
        }
    }

}
