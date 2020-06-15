package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.utils.PermissionsHelper;
import fr.volax.valkyacore.tools.ConfigBuilder;
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
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsHelper().unmuteUse)) return false;

        if(args.length != 1){
            sender.sendMessage(ConfigBuilder.getCString("messages.unmute.help-message", ConfigType.MESSAGES));
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(args[0]);

        if(!ValkyaCore.getInstance().getMuteManager().isMuted(targetUUID)){
            sender.sendMessage(ConfigBuilder.getCString("messages.unmute.no-mute", ConfigType.MESSAGES));
            return false;
        }

        ValkyaCore.getInstance().getMuteManager().unmute(targetUUID);
        sender.sendMessage(ConfigBuilder.getCString("messages.unmute.unmute", ConfigType.MESSAGES).replaceAll("%player%", args[0]));
        return false;
    }
}
