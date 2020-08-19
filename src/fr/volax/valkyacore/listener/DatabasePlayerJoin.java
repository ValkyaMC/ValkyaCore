package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DatabasePlayerJoin implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
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