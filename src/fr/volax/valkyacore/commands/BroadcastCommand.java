/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.utils.ValkyaUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {
    public BroadcastCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().broadcastUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.broadcast")) return false;
        if(args.length == 0){
            helpMessage(sender);
        }else{
            ValkyaUtils.broadcast("§r" + ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
            return false;
        }
        return false;
    }

    private void helpMessage(CommandSender player){
        ValkyaUtils.sendChat(player,"§e/broadcast <messages>");
    }
}
