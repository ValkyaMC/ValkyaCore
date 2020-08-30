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
        String playerName = player.getName();
        if(!ValkyaCore.getInstance().getMuteManager().isMuted(playerName)){
            if(player.hasPermission(ValkyaCore.getInstance().getPermissionsHelper().cooldownChatBypass)) return;
            if(ValkyaCore.getInstance().cooldown.containsKey(playerName)){
                float time = (System.currentTimeMillis() - ValkyaCore.getInstance().cooldown.get(playerName)) / 1000;
                if(time < ConfigBuilder.getCInt("cooldownchat.time", ConfigType.COOLDOWNCHAT.getConfigName())){
                    event.setCancelled(true);
                    player.sendMessage(ConfigBuilder.getCString("messages.cooldownchat.cooldownchat", ConfigType.MESSAGES.getConfigName()));
                }else { ValkyaCore.getInstance().cooldown.put(playerName, System.currentTimeMillis()); }
            } else{ ValkyaCore.getInstance().cooldown.put(playerName, System.currentTimeMillis()); }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        if(!ConfigBuilder.getString("chat").equalsIgnoreCase("true")){
            if(!player.hasPermission(ValkyaCore.getInstance().getPermissionsHelper().chatOffBypass)){
                ValkyaUtils.sendChat(player, "§eLe chat est actuellement désactivé !");
                event.setCancelled(true);
            }
        }

        ValkyaCore.getInstance().getMuteManager().checkDuration(player.getName());
        if(ValkyaCore.getInstance().getMuteManager().isMuted(player.getName())){
            player.sendMessage(ConfigBuilder.getCString("messages.mute.player-talking", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ValkyaCore.getInstance().getMuteManager().getReason(player.getName())).replaceAll("%time%", ValkyaCore.getInstance().getMuteManager().getTimeLeft(player.getName())));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(PlayerCommandPreprocessEvent event){
        String[] cmd = event.getMessage().split(" ");
        Player player = event.getPlayer();
        if(cmd[0].equalsIgnoreCase("/ver") || cmd[0].equalsIgnoreCase("/pl") || cmd[0].equalsIgnoreCase("/bukkit:plugins") || cmd[0].equalsIgnoreCase("/bukkit:pl") || cmd[0].equalsIgnoreCase("/version") || cmd[0].equalsIgnoreCase("/about") || cmd[0].equalsIgnoreCase("//calc") || cmd[0].equalsIgnoreCase("/ipwl") || cmd[0].equalsIgnoreCase("/bukkit:help") || cmd[0].equalsIgnoreCase("/bukkit:?") || cmd[0].equalsIgnoreCase("/?") || cmd[0].equalsIgnoreCase("/bukkit:about") || cmd[0].equalsIgnoreCase("/bukkit:version") || cmd[0].equalsIgnoreCase("/bukkit:ver") || cmd[0].equalsIgnoreCase("/cauldron") || cmd[0].equalsIgnoreCase("/cauldron_e") || cmd[0].equalsIgnoreCase("/cauldron:") || cmd[0].equalsIgnoreCase("/thermos") || cmd[0].equalsIgnoreCase("/thermos:")|| cmd[0].equalsIgnoreCase("/plugins")) {
            player.sendMessage(ConfigBuilder.getCString("messages.no-command", ConfigType.MESSAGES.getConfigName()));
            event.setCancelled(true);
        }

        if(ValkyaCore.getInstance().getMuteManager().isMuted(player.getName())){
            if(cmd[0].equalsIgnoreCase("/msg") || cmd[0].equalsIgnoreCase("/message") || cmd[0].equalsIgnoreCase("/tell") || cmd[0].equalsIgnoreCase("/chat") || cmd[0].equalsIgnoreCase("/emsg") || cmd[0].equalsIgnoreCase("/etell")){
                event.setCancelled(true);
                ValkyaUtils.sendChat(player, "§eVous ne pouvez pas executer cette commande en étant mute !");
            }
        }

        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(player)){
            if(cmd[0].equalsIgnoreCase("/hub") || cmd[0].equalsIgnoreCase("/spawn") || cmd[0].equalsIgnoreCase("/sethome") || cmd[0].equalsIgnoreCase("/home") || cmd[0].equalsIgnoreCase("/espawn") || cmd[0].equalsIgnoreCase("/ehome") || cmd[0].equalsIgnoreCase("/esethome") || cmd[0].equalsIgnoreCase("/f") && cmd[1].equalsIgnoreCase("claim") || cmd[0].equalsIgnoreCase("/rtp") || cmd[0].equalsIgnoreCase("/tpa") || cmd[0].equalsIgnoreCase("/etpa") || cmd[0].equalsIgnoreCase("/ewarp")  || cmd[0].equalsIgnoreCase("/warp")){
                event.setCancelled(true);
                ValkyaUtils.sendChat(player, "§eVous ne pouvez pas executer cette commande en étant en combat !");
            }
        }
    }
}
