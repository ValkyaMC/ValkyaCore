package fr.volax.valkyacore.listeners;

import fr.volax.valkyacore.managers.PermissionsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class XPNoDeath implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(player.hasPermission(new PermissionsManager().xpDeath)){
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }
}

