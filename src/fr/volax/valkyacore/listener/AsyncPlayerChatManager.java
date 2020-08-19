package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.ValkyaUtils;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatManager implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(!ConfigBuilder.getString("chat").equalsIgnoreCase("true")){
            if(!event.getPlayer().hasPermission(ValkyaCore.getInstance().getPermissionsHelper().chatOffBypass)){
                ValkyaUtils.sendChat(event.getPlayer(), "§eLe chat est actuellement désactivé !");
                event.setCancelled(true);
            }
        }
    }
}