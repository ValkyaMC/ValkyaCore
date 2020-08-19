package fr.volax.valkyacore.listener;

import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class GlobalPlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);

        if(ConfigBuilder.getBoolean("onJoin.execute-command")){
            for(String command : (List<String>) ConfigBuilder.getList("onJoin.commands"))
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%playerName%", event.getPlayer().getName()).replaceAll("%onlinePlayers%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size())).replaceAll("%maxPlayers%", String.valueOf(Bukkit.getServer().getMaxPlayers())).replaceAll("%serverName%", Bukkit.getServerName()));
        }

        if(ConfigBuilder.getBoolean("onJoin.teleport"))
            event.getPlayer().teleport(new Location(Bukkit.getWorld(ConfigBuilder.getString("onJoin.teleport-coos.world")), ConfigBuilder.getInt("onJoin.teleport-coos.X"),ConfigBuilder.getInt("onJoin.teleport-coos.Y"),ConfigBuilder.getInt("onJoin.teleport-coos.Z"),ConfigBuilder.getInt("onJoin.teleport-coos.YAW"),ConfigBuilder.getInt("onJoin.teleport-coos.PITCH")));
    }
}
