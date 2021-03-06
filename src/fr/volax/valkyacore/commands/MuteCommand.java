/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tools.ConfigType;
import fr.volax.valkyacore.utils.ValkyaUtils;
import fr.volax.volaxapi.tool.time.TimeUnit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class MuteCommand implements CommandExecutor {
    MuteCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().muteUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.mute")) return false;

        if(args.length < 3){
            helpMessage(sender);
            return false;
        }

        String targetName = args[0];

        if(!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(targetName);

        if(ValkyaCore.getInstance().getMuteManager().isMuted(targetName)){
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.already-mute", ConfigType.MESSAGES.getConfigName()));
            return false;
        }

        String reason = "";
        for(int i = 2; i < args.length; i++){
            reason += args[i] + " ";
        }

        if(args[1].equalsIgnoreCase("perm") || args[1].equalsIgnoreCase("perma") || args[1].equalsIgnoreCase("permanent") || args[1].equalsIgnoreCase("p")){
            if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().mutePermUse)) return false;
            ValkyaCore.getInstance().getMuteManager().mute(sender, -1, reason, args);
            return false;
        }

        if(!args[1].contains(":")){
            helpMessage(sender);
            return false;
        }

        int duration = 0;
        try {
            duration = Integer.parseInt(args[1].split(":")[0]);
        } catch (NumberFormatException e){
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.enter-number", ConfigType.MESSAGES.getConfigName()));
            return false;
        }

        if(!TimeUnit.existFromShortcut(args[1].split(":")[1])){
            sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.invalid-format-time", ConfigType.MESSAGES.getConfigName()));
            for(TimeUnit units : TimeUnit.values()){
                ValkyaUtils.sendChat(sender,"§b" + units.getName() + " §f: §e" + units.getShortcut());
            }
            return false;
        }

        TimeUnit unit = TimeUnit.getFromShortcut(args[1].split(":")[1]);
        long muteTime = unit.getToSecond() * duration;

        ValkyaCore.getInstance().getMuteManager().tempMute(sender, muteTime, reason, args, unit, duration);
        return false;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.help-message", ConfigType.MESSAGES.getConfigName()));
    }
}
