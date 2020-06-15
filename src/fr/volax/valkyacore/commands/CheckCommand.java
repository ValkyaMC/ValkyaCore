package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.utils.PermissionsHelper;
import fr.volax.valkyacore.tools.ConfigBuilder;
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
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsHelper().checkUse)) return false;

        if(args.length != 1){
            sender.sendMessage(ConfigBuilder.getCString("messages.check.help-message", ConfigType.MESSAGES));
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(args[0]);

        sender.sendMessage(ConfigBuilder.getCString("messages.check.separator", ConfigType.MESSAGES));
        sender.sendMessage(ConfigBuilder.getCString("messages.check.player", ConfigType.MESSAGES).replaceAll("%player%", args[0]));
        sender.sendMessage(ConfigBuilder.getCString("messages.check.uuid", ConfigType.MESSAGES).replaceAll("%uuid%", targetUUID.toString()));
        sender.sendMessage(ConfigBuilder.getCString("messages.check.mute", ConfigType.MESSAGES).replaceAll("%isMute%", ValkyaCore.getInstance().getMuteManager().isMuted(targetUUID) ? "§a✔" : "§c✖"));

        if(ValkyaCore.getInstance().getMuteManager().isMuted(targetUUID)){
            sender.sendMessage("");
            sender.sendMessage(ConfigBuilder.getCString("messages.check.reason", ConfigType.MESSAGES).replaceAll("%reason%", ValkyaCore.getInstance().getMuteManager().getReason(targetUUID)));
            sender.sendMessage(ConfigBuilder.getCString("messages.check.time", ConfigType.MESSAGES).replaceAll("%duration%", ValkyaCore.getInstance().getMuteManager().getTimeLeft(targetUUID)));
            sender.sendMessage("");
        }


        sender.sendMessage(ConfigBuilder.getCString("messages.check.ban", ConfigType.MESSAGES).replaceAll("%isBan%", ValkyaCore.getInstance().getBanManager().isBanned(targetUUID) ? "§a✔" : "§c✖"));

        if(ValkyaCore.getInstance().getBanManager().isBanned(targetUUID)){
            sender.sendMessage("");
            sender.sendMessage(ConfigBuilder.getCString("messages.check.reason", ConfigType.MESSAGES).replaceAll("%reason%", ValkyaCore.getInstance().getBanManager().getReason(targetUUID)));
            sender.sendMessage(ConfigBuilder.getCString("messages.check.time", ConfigType.MESSAGES).replaceAll("%duration%", ValkyaCore.getInstance().getBanManager().getTimeLeft(targetUUID)));
        }

        sender.sendMessage(ConfigBuilder.getCString("messages.check.separator", ConfigType.MESSAGES));

        return false;
    }
}
