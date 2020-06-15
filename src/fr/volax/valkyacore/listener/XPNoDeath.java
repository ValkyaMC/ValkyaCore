package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.utils.PermissionsHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class XPNoDeath implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(player.hasPermission(new PermissionsHelper().xpDeath)){
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }
}

