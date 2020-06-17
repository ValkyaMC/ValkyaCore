package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.PermissionsHelper;
import fr.volax.valkyacore.tool.ConfigBuilder;
import fr.volax.valkyacore.tool.ConfigType;
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
            if(player.hasPermission(new PermissionsHelper().cooldownChatBypass)) return;
            if(ValkyaCore.getInstance().cooldown.containsKey(uuid)){
                float time = (System.currentTimeMillis() - ValkyaCore.getInstance().cooldown.get(uuid)) / 1000;
                if(time < ConfigBuilder.getCInt("cooldownchat.time", ConfigType.COOLDOWNCHAT)){
                    event.setCancelled(true);
                    player.sendMessage(ConfigBuilder.getCString("messages.cooldownchat.cooldownchat", ConfigType.MESSAGES));
                }else { ValkyaCore.getInstance().cooldown.put(uuid, System.currentTimeMillis()); }
            } else{ ValkyaCore.getInstance().cooldown.put(uuid, System.currentTimeMillis()); }
        }
    }
}
