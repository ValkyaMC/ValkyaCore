package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class AntiCommands implements Listener {
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
