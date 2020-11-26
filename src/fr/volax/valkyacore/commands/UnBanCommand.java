/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tools.ConfigType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class UnBanCommand implements CommandExecutor {
    UnBanCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().unbanUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.unban")) return false;

        if(args.length != 1){
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.unban.help-message", ConfigType.MESSAGES.getConfigName()));
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(args[0]);

        if(!ValkyaCore.getInstance().getBanManager().isBanned(targetUUID)){
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.unban.no-ban", ConfigType.MESSAGES.getConfigName()));
            return false;
        }

        ValkyaCore.getInstance().getBanManager().unban(targetUUID);
        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.unban.unban", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", args[0]));
        return false;
    }
}
