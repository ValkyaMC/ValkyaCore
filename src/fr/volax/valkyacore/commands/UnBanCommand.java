package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.PermissionsHelper;
import fr.volax.valkyacore.tool.ConfigBuilder;
import fr.volax.valkyacore.tool.ConfigType;
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
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsHelper().unbanUse)) return false;

        if(args.length != 1){
            sender.sendMessage(ConfigBuilder.getCString("messages.unban.help-message", ConfigType.MESSAGES));
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(args[0]);

        if(!ValkyaCore.getInstance().getBanManager().isBanned(targetUUID)){
            sender.sendMessage(ConfigBuilder.getCString("messages.unban.no-ban", ConfigType.MESSAGES));
            return false;
        }

        ValkyaCore.getInstance().getBanManager().unban(targetUUID);
        sender.sendMessage(ConfigBuilder.getCString("messages.unban.unban", ConfigType.MESSAGES).replaceAll("%player%", args[0]));
        return false;
    }
}
