/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tools.ConfigType;
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
        if (!ValkyaCore.getInstance().getConfigBuilder().getBoolean("onJoin.isLobby")) {
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

        if(ValkyaCore.getInstance().getConfigBuilder().getBoolean("onJoin.execute-command")){
            for(String command : (List<String>) ValkyaCore.getInstance().getConfigBuilder().getList("onJoin.commands"))
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%playerName%", event.getPlayer().getName()).replaceAll("%onlinePlayers%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size())).replaceAll("%maxPlayers%", String.valueOf(Bukkit.getServer().getMaxPlayers())).replaceAll("%serverName%", Bukkit.getServerName()));
        }

        if(ValkyaCore.getInstance().getConfigBuilder().getBoolean("onJoin.teleport"))
            event.getPlayer().teleport(new Location(Bukkit.getWorld(ValkyaCore.getInstance().getConfigBuilder().getString("onJoin.teleport-coos.world")), ValkyaCore.getInstance().getConfigBuilder().getInt("onJoin.teleport-coos.X"),ValkyaCore.getInstance().getConfigBuilder().getInt("onJoin.teleport-coos.Y"),ValkyaCore.getInstance().getConfigBuilder().getInt("onJoin.teleport-coos.Z"),ValkyaCore.getInstance().getConfigBuilder().getInt("onJoin.teleport-coos.YAW"),ValkyaCore.getInstance().getConfigBuilder().getInt("onJoin.teleport-coos.PITCH")));
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(player.getName());
        ValkyaCore.getInstance().getBanManager().checkDuration(targetUUID);
        if(ValkyaCore.getInstance().getBanManager().isBanned(targetUUID)){
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.banned-player-join", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ValkyaCore.getInstance().getBanManager().getReason(targetUUID)).replaceAll("%time%", ValkyaCore.getInstance().getBanManager().getTimeLeft(targetUUID)));
        }
    }
}
