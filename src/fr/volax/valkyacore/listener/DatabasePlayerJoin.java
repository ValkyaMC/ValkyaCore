package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

public class DatabasePlayerJoin implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        if(!ConfigBuilder.getBoolean("onJoin.isLobby")){
            if(!ValkyaCore.getInstance().getPlayerUtils().exist(event.getPlayer().getName())){
                Player player = event.getPlayer();
                player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
                player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
                player.getInventory().setItem(1, new ItemStack(Material.DIAMOND_PICKAXE));
                player.getInventory().setItem(2, new ItemStack(Material.COOKED_CHICKEN, 32));
            }
        }
        ValkyaCore.getInstance().getPlayerUtils().update(event.getPlayer());
    }

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