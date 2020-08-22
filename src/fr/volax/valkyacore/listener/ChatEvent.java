/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.PermissionsHelper;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.valkyacore.util.ValkyaUtils;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.UUID;

public class ChatEvent implements Listener {
    @EventHandler
    public void playerChatAsync(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(!ValkyaCore.getInstance().getMuteManager().isMuted(uuid)){
            if(player.hasPermission(ValkyaCore.getInstance().getPermissionsHelper().cooldownChatBypass)) return;
            if(ValkyaCore.getInstance().cooldown.containsKey(uuid)){
                float time = (System.currentTimeMillis() - ValkyaCore.getInstance().cooldown.get(uuid)) / 1000;
                if(time < ConfigBuilder.getCInt("cooldownchat.time", ConfigType.COOLDOWNCHAT.getConfigName())){
                    event.setCancelled(true);
                    player.sendMessage(ConfigBuilder.getCString("messages.cooldownchat.cooldownchat", ConfigType.MESSAGES.getConfigName()));
                }else { ValkyaCore.getInstance().cooldown.put(uuid, System.currentTimeMillis()); }
            } else{ ValkyaCore.getInstance().cooldown.put(uuid, System.currentTimeMillis()); }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if(!ConfigBuilder.getString("chat").equalsIgnoreCase("true")){
            if(!player.hasPermission(ValkyaCore.getInstance().getPermissionsHelper().chatOffBypass)){
                ValkyaUtils.sendChat(player, "§eLe chat est actuellement désactivé !");
                event.setCancelled(true);
            }
        }

        ValkyaCore.getInstance().getMuteManager().checkDuration(playerUUID);
        if(ValkyaCore.getInstance().getMuteManager().isMuted(playerUUID)){
            player.sendMessage(ConfigBuilder.getCString("messages.mute.player-talking", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ValkyaCore.getInstance().getMuteManager().getReason(playerUUID)).replaceAll("%time%", ValkyaCore.getInstance().getMuteManager().getTimeLeft(playerUUID)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(PlayerCommandPreprocessEvent event){
        String[] cmd = event.getMessage().split(" ");
        Player player = event.getPlayer();
        if(cmd[0].equalsIgnoreCase("/ver") || cmd[0].equalsIgnoreCase("/pl") || cmd[0].equalsIgnoreCase("/bukkit:plugins") || cmd[0].equalsIgnoreCase("/bukkit:pl") || cmd[0].equalsIgnoreCase("/version")   || cmd[0].equalsIgnoreCase("/about") || cmd[0].equalsIgnoreCase("//calc") || cmd[0].equalsIgnoreCase("/ipwl") || cmd[0].equalsIgnoreCase("/bukkit:help") || cmd[0].equalsIgnoreCase("/bukkit:?") || cmd[0].equalsIgnoreCase("/?") || cmd[0].equalsIgnoreCase("/bukkit:about") || cmd[0].equalsIgnoreCase("/bukkit:version") || cmd[0].equalsIgnoreCase("/bukkit:ver") || cmd[0].equalsIgnoreCase("/cauldron") || cmd[0].equalsIgnoreCase("/cauldron_e") || cmd[0].equalsIgnoreCase("/cauldron:") || cmd[0].equalsIgnoreCase("/thermos") || cmd[0].equalsIgnoreCase("/thermos:")|| cmd[0].equalsIgnoreCase("/plugins")) {
            player.sendMessage(ConfigBuilder.getCString("messages.no-command", ConfigType.MESSAGES.getConfigName()));
            event.setCancelled(true);
        }
    }
}
