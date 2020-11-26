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

public class UnMuteCommand implements CommandExecutor {
    UnMuteCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().unmuteUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.unmute")) return false;

        if(args.length != 1){
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.unmute.help-message", ConfigType.MESSAGES.getConfigName()));
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(args[0]);

        if(!ValkyaCore.getInstance().getMuteManager().isMuted(args[0])){
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.unmute.no-mute", ConfigType.MESSAGES.getConfigName()));
            return false;
        }

        ValkyaCore.getInstance().getMuteManager().unmute(args[0]);
        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.unmute.unmute", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", args[0]));
        return false;
    }
}
