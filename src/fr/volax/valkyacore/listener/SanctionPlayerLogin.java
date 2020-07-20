package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class SanctionPlayerLogin implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        ValkyaCore.getInstance().getBanManager().checkDuration(player.getUniqueId());
        if(ValkyaCore.getInstance().getBanManager().isBanned(player.getUniqueId())){
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(ConfigBuilder.getCString("messages.ban.banned-player-join", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ValkyaCore.getInstance().getBanManager().getReason(player.getUniqueId())).replaceAll("%time%", ValkyaCore.getInstance().getBanManager().getTimeLeft(player.getUniqueId())));
        }
    }
}
