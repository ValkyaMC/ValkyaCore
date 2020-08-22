/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class GlobalPlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(!ValkyaCore.getInstance().getPlayerUtils().exist(event.getPlayer().getName())){
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "pex user " + event.getPlayer().getName() + " group set Saint");
        }
        ValkyaCore.getInstance().getPlayerUtils().update(event.getPlayer());
        if (!ConfigBuilder.getBoolean("onJoin.isLobby")) {
            if (!(ValkyaCore.getInstance().getPlayerUtils().getFirstLoginKit(event.getPlayer().getName()).equalsIgnoreCase("true"))) {
                ValkyaCore.getInstance().getPlayerUtils().setFirstLoginKit(event.getPlayer().getName(), "true");
                Player player = event.getPlayer();
                player.getInventory().addItem(new ItemStack(Material.IRON_HELMET));
                player.getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE));
                player.getInventory().addItem(new ItemStack(Material.IRON_LEGGINGS));
                player.getInventory().addItem(new ItemStack(Material.IRON_BOOTS));
                player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                player.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
                player.getInventory().addItem(new ItemStack(Material.COOKED_CHICKEN, 32));
            }
        }

        event.setJoinMessage(null);

        if(ConfigBuilder.getBoolean("onJoin.execute-command")){
            for(String command : (List<String>) ConfigBuilder.getList("onJoin.commands"))
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%playerName%", event.getPlayer().getName()).replaceAll("%onlinePlayers%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size())).replaceAll("%maxPlayers%", String.valueOf(Bukkit.getServer().getMaxPlayers())).replaceAll("%serverName%", Bukkit.getServerName()));
        }

        if(ConfigBuilder.getBoolean("onJoin.teleport"))
            event.getPlayer().teleport(new Location(Bukkit.getWorld(ConfigBuilder.getString("onJoin.teleport-coos.world")), ConfigBuilder.getInt("onJoin.teleport-coos.X"),ConfigBuilder.getInt("onJoin.teleport-coos.Y"),ConfigBuilder.getInt("onJoin.teleport-coos.Z"),ConfigBuilder.getInt("onJoin.teleport-coos.YAW"),ConfigBuilder.getInt("onJoin.teleport-coos.PITCH")));
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(player.getName());
        ValkyaCore.getInstance().getBanManager().checkDuration(targetUUID);
        if(ValkyaCore.getInstance().getBanManager().isBanned(targetUUID)){
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(ConfigBuilder.getCString("messages.ban.banned-player-join", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ValkyaCore.getInstance().getBanManager().getReason(targetUUID)).replaceAll("%time%", ValkyaCore.getInstance().getBanManager().getTimeLeft(targetUUID)));
        }
    }
}
