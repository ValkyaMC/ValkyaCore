package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigBuilder;
import fr.volax.valkyacore.tool.ConfigType;
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
            player.sendMessage(ConfigBuilder.getCString("messages.mute.player-talking", ConfigType.MESSAGES).replaceAll("%reason%", ValkyaCore.getInstance().getMuteManager().getReason(playerUUID)).replaceAll("%time%", ValkyaCore.getInstance().getMuteManager().getTimeLeft(playerUUID)));
            event.setCancelled(true);
        }
    }
}
