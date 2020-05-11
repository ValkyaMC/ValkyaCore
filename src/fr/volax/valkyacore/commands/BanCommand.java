package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import fr.volax.valkyacore.utils.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class BanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsManager().banUse)) return false;

        if (args.length < 3) {
            helpMessage(sender);
            return false;
        }

        String targetName = args[0];

        if (!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(targetName);

        if (ValkyaCore.getInstance().getBanManager().isBanned(targetUUID)) {
            sender.sendMessage(ValkyaCore.PREFIX + " §cCe joueur est déjà banni !");
            return false;
        }

        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        if (args[1].equalsIgnoreCase("perm") || args[1].equalsIgnoreCase("perma") || args[1].equalsIgnoreCase("permanent") || args[1].equalsIgnoreCase("p")) {
            if (!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsManager().banPermUse))
                return false;
            if (!ValkyaCore.getInstance().getBanManager().ban(targetUUID, sender, -1, reason.toString(), args))
                return false;
            sender.sendMessage(ValkyaCore.PREFIX + " §eVous avez banni §6" + targetName + " §c(Permanent) §epour : §6" + ChatColor.translateAlternateColorCodes('&', String.join(" ", reason.toString())));
            ValkyaCore.getInstance().getBanManager().ban(targetUUID, sender, -1, reason.toString(), args);
            return false;
        }

        if (!args[1].contains(":")) {
            helpMessage(sender);
            return false;
        }

        int duration = 0;
        try {
            duration = Integer.parseInt(args[1].split(":")[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ValkyaCore.PREFIX + " §cVous devez entrer un nombre pour la durée du ban !");
            return false;
        }

        if (!TimeUnit.existFromShortcut(args[1].split(":")[1])) {
            sender.sendMessage(ValkyaCore.PREFIX + " §cCette unité de temps n'existe pas !");
            for (TimeUnit units : TimeUnit.values()) {
                sender.sendMessage(ValkyaCore.PREFIX + " §b" + units.getName() + " §f: §e" + units.getShortcut());
            }
            return false;
        }

        TimeUnit unit = TimeUnit.getFromShortcut(args[1].split(":")[1]);
        long bantime = unit.getToSecond() * duration;

        if (!ValkyaCore.getInstance().getBanManager().ban(targetUUID, sender, bantime, reason.toString(), args))
            return false;
        sender.sendMessage(ValkyaCore.PREFIX + "§eVous avez banni §6 " + targetName + " §c(" + duration + " " + unit.getName() + ") §epour : §6" + ChatColor.translateAlternateColorCodes('&', String.join(" ", reason.toString())));
        ValkyaCore.getInstance().getBanManager().ban(targetUUID, sender, bantime, reason.toString(), args);
        return false;
    }

    private void helpMessage(CommandSender sender) {
        sender.sendMessage(ValkyaCore.PREFIX + " §c/ban <joueur> perm <raison>");
        sender.sendMessage(ValkyaCore.PREFIX + " §c/ban <joueur> <durée>:<unité> <raison>");
    }
}
