package fr.volax.valkyacore.listeners;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import fr.volax.valkyacore.tools.ConfigBuilder;
import fr.volax.valkyacore.tools.ConfigType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class AsyncPlayerChatAntiSpam implements Listener {
    @EventHandler
    public void playerChatAsync(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(!ValkyaCore.getInstance().getMuteManager().isMuted(uuid)){
            if(player.hasPermission(new PermissionsManager().cooldownChatBypass)) return;
            if(ValkyaCore.getInstance().cooldown.containsKey(uuid)){
                float time = (System.currentTimeMillis() - ValkyaCore.getInstance().cooldown.get(uuid)) / 1000;
                if(time < ConfigBuilder.getCInt("cooldownchat.time", ConfigType.COOLDOWNCHAT)){
                    event.setCancelled(true);
                    player.sendMessage(ConfigBuilder.getString("messages.prefix") + ConfigBuilder.getCString("messages.cooldownchat", ConfigType.COOLDOWNCHAT));
                }else { ValkyaCore.getInstance().cooldown.put(uuid, System.currentTimeMillis()); }
            } else{ ValkyaCore.getInstance().cooldown.put(uuid, System.currentTimeMillis()); }
        }
    }
}
