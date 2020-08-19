package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.valkyacore.util.ValkyaUtils;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import fr.volax.volaxapi.tool.time.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class BanCommand implements CommandExecutor {

    BanCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().banUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.ban")) return false;

        if (args.length < 3) {
            helpMessage(sender);
            return false;
        }

        String targetName = args[0];

        if (!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(targetName);

        if (ValkyaCore.getInstance().getBanManager().isBanned(targetUUID)) {
            sender.sendMessage(ConfigBuilder.getCString("messages.ban.already-ban", ConfigType.MESSAGES.getConfigName()));
            return false;
        }

        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        if (args[1].equalsIgnoreCase("perm") || args[1].equalsIgnoreCase("perma") || args[1].equalsIgnoreCase("permanent") || args[1].equalsIgnoreCase("p")) {
            if (!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().banPermUse)) return false;
            if (!ValkyaCore.getInstance().getBanManager().ban(targetUUID, sender, -1, reason.toString(), args)) return false;
            sender.sendMessage(ConfigBuilder.getCString("messages.ban.have-been-permaban", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", targetName).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', String.join(" ", reason.toString()))));
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
            sender.sendMessage(ConfigBuilder.getCString("messages.ban.enter-number", ConfigType.MESSAGES.getConfigName()));
            return false;
        }

        if (!TimeUnit.existFromShortcut(args[1].split(":")[1])) {
            sender.sendMessage(ConfigBuilder.getCString("messages.ban.invalid-format-time", ConfigType.MESSAGES.getConfigName()));
            for (TimeUnit units : TimeUnit.values()) {
                ValkyaUtils.sendChat(sender,"§b" + units.getName() + " §f: §e" + units.getShortcut());
            }
            return false;
        }

        TimeUnit unit = TimeUnit.getFromShortcut(args[1].split(":")[1]);
        long bantime = unit.getToSecond() * duration;

        if (!ValkyaCore.getInstance().getBanManager().ban(targetUUID, sender, bantime, reason.toString(), args))
            return false;
        sender.sendMessage(ConfigBuilder.getCString("messages.ban.have-been-tempban", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", targetName).replaceAll("%duration%", duration + " " + unit.getName()).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', String.join(" ", reason.toString()))));
        ValkyaCore.getInstance().getBanManager().ban(targetUUID, sender, bantime, reason.toString(), args);
        return false;
    }

    private void helpMessage(CommandSender sender) {
        sender.sendMessage(ConfigBuilder.getCString("messages.ban.help-message", ConfigType.MESSAGES.getConfigName()));
    }
}
