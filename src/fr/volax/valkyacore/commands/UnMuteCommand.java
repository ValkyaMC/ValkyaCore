package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class UnMuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsManager().unmuteUse)) return false;

        if(args.length != 1){
            sender.sendMessage(ValkyaCore.PREFIX + " §c/unmute <joueur>");
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(args[0]);

        if(!ValkyaCore.getInstance().getMuteManager().isMuted(targetUUID)){
            sender.sendMessage(ValkyaCore.PREFIX + " §cCe joueur n'est pas mute !");
            return false;
        }

        ValkyaCore.getInstance().getMuteManager().unmute(targetUUID);
        sender.sendMessage(ValkyaCore.PREFIX + " §aVous avez unmute §6" + args[0]);
        return false;
    }
}
