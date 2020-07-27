package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.handler.TeleportCooldownHandler;
import fr.volax.valkyacore.handler.TeleportHandler;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RTPCommand implements CommandExecutor {
    public RTPCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player)sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().rtpUse)) return false;

        if(ConfigBuilder.getBoolean("rtp.cooldown-active") && (!TeleportCooldownHandler.areTherePlayersInTheMap())){
            TeleportCooldownHandler cooldown = TeleportCooldownHandler.getCooldown(player);
            if(!cooldown.check(player) && (cooldown.getTimeLeft(player) * -1L >= 1L)){
                player.sendMessage(ValkyaCore.PREFIX + ConfigBuilder.getCString("messages.rtp.error", ConfigType.MESSAGES.getConfigName()).replaceAll("&","§").replaceAll("%cooldown%", String.valueOf(cooldown.getTimeLeft(player) * -1L)));
                return true;
            }else if(cooldown.getTimeLeft(player) * -1L == 0L){
                cooldown.finalize();
            }else{
                cooldown.finalize();
            }
        }
        TeleportHandler tp = new TeleportHandler(player, Bukkit.getWorld(ConfigBuilder.getString("rtp.world")), ConfigBuilder.getInt("rtp.x"), ConfigBuilder.getInt("rtp.z"));
        tp.teleport();
        player.sendMessage(tp.getMessage());
        if(ConfigBuilder.getBoolean("rtp.cooldown-active")){
            TeleportCooldownHandler cooldown = new TeleportCooldownHandler(player, ConfigBuilder.getInt("rtp.cooldown-temps"));
            cooldown.start();
        }
        return false;
    }
}
