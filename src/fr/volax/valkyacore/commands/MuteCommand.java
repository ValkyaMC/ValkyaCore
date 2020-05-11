package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import fr.volax.valkyacore.utils.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class MuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsManager().muteUse)) return false;

        if(args.length < 3){
            helpMessage(sender);
            return false;
        }

        String targetName = args[0];

        if(!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(targetName);

        if(ValkyaCore.getInstance().getMuteManager().isMuted(targetUUID)){
            sender.sendMessage(ValkyaCore.PREFIX + " §cCe joueur est déjà mute !");
            return false;
        }

        String reason = "";
        for(int i = 2; i < args.length; i++){
            reason += args[i] + " ";
        }

        if(args[1].equalsIgnoreCase("perm") || args[1].equalsIgnoreCase("perma") || args[1].equalsIgnoreCase("permanent") || args[1].equalsIgnoreCase("p")){
            if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsManager().mutePermUse)) return false;
            if(!ValkyaCore.getInstance().getMuteManager().mute(targetUUID, sender, -1, reason, args)) return false;
            ValkyaCore.getInstance().getMuteManager().mute(targetUUID, sender, -1, reason, args);
            sender.sendMessage(ValkyaCore.PREFIX + " §eVous avez mute §6" + targetName + " §c(Permanent) §epour : §6" + ChatColor.translateAlternateColorCodes('&', String.join(" ", reason)));
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
            sender.sendMessage(ValkyaCore.PREFIX + " §cVous devez entrer un nombre pour la durée du mute !");
            return false;
        }

        if(!TimeUnit.existFromShortcut(args[1].split(":")[1])){
            sender.sendMessage(ValkyaCore.PREFIX + " §cCette unité de temps n'existe pas !");
            for(TimeUnit units : TimeUnit.values()){
                sender.sendMessage(ValkyaCore.PREFIX + " §b" + units.getName() + " §f: §e" + units.getShortcut());
            }
            return false;
        }

        TimeUnit unit = TimeUnit.getFromShortcut(args[1].split(":")[1]);
        long muteTime = unit.getToSecond() * duration;

        if(!ValkyaCore.getInstance().getMuteManager().mute(targetUUID, sender, muteTime, reason, args)) return false;
        ValkyaCore.getInstance().getMuteManager().mute(targetUUID, sender, muteTime, reason, args);
        sender.sendMessage(ValkyaCore.PREFIX + " §eVous avez mute §6" + targetName + " §c(" + duration + " " + unit.getName() + ") §epour : §6" + ChatColor.translateAlternateColorCodes('&', String.join(" ", reason)));
        return false;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage(ValkyaCore.PREFIX + " §c/mute <joueur> perm <raison>");
        sender.sendMessage(ValkyaCore.PREFIX + " §c/mute <joueur> <durée>:<unité> <raison>");
    }
}
