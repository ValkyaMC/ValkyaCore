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

public class CheckCommand implements CommandExecutor {
    CheckCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().checkUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.check")) return false;

        if(args.length != 1){
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.help-message", ConfigType.MESSAGES.getConfigName()));
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(args[0]);
        String targetName = args[0];

        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.separator", ConfigType.MESSAGES.getConfigName()));
        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.player", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", args[0]));
        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.uuid", ConfigType.MESSAGES.getConfigName()).replaceAll("%uuid%", targetUUID.toString()));
        sender.sendMessage("§eIP §f: §b" + ValkyaCore.getInstance().getPlayerUtils().getAddress(args[0]));
        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.mute", ConfigType.MESSAGES.getConfigName()).replaceAll("%isMute%", ValkyaCore.getInstance().getMuteManager().isMuted(targetName) ? "§a✔" : "§c✖"));
        if(ValkyaCore.getInstance().getMuteManager().isMuted(targetName)){
            sender.sendMessage("");
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.reason", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ValkyaCore.getInstance().getMuteManager().getReason(targetName)));
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.time", ConfigType.MESSAGES.getConfigName()).replaceAll("%duration%", ValkyaCore.getInstance().getMuteManager().getTimeLeft(targetName)));
            sender.sendMessage("");
        }


        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.ban", ConfigType.MESSAGES.getConfigName()).replaceAll("%isBan%", ValkyaCore.getInstance().getBanManager().isBanned(targetUUID) ? "§a✔" : "§c✖"));

        if(ValkyaCore.getInstance().getBanManager().isBanned(targetUUID)){
            sender.sendMessage("");
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.reason", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ValkyaCore.getInstance().getBanManager().getReason(targetUUID)));
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.time", ConfigType.MESSAGES.getConfigName()).replaceAll("%duration%", ValkyaCore.getInstance().getBanManager().getTimeLeft(targetUUID)));
        }

        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.check.separator", ConfigType.MESSAGES.getConfigName()));

        return false;
    }
}
