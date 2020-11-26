/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.utils.PermissionsHelper;
import fr.volax.valkyacore.tools.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {
    KickCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().kickUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.kick")) return false;

        if(args.length < 2){
            helpMessage(sender);
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().isOnlinePlayer(sender, args[0])) return false;
        Player player = Bukkit.getPlayer(args[0]);

        String reason = "";
        for(int i = 1; i < args.length; i++){
            reason += args[i] + " ";
        }
        if(player.hasPermission(new PermissionsHelper().kickBypass)){
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.kick.cant-kick", ConfigType.MESSAGES.getConfigName()));
            return false;
        }
        player.kickPlayer(ValkyaCore.getInstance().getConfigBuilder().getString("messages.kick.have-been-kick", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', String.join(" ", reason))));
        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.kick.kick", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", player.getName()).replaceAll("%reason%",ChatColor.translateAlternateColorCodes('&', String.join(" ", reason))));
        return false;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.kick.help-message", ConfigType.MESSAGES.getConfigName()));
    }
}
