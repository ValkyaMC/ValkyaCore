package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class CheckCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsManager().checkUse)) return false;

        if(args.length != 1){
            sender.sendMessage(ValkyaCore.PREFIX + " §c/check <joueur>");
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().doesPlayerExist(sender, args[0])) return false;
        UUID targetUUID = ValkyaCore.getInstance().getPlayerUtils().getUUID(args[0]);

        sender.sendMessage("§7-----------------------------------------------------");
        sender.sendMessage("§ePseudo : §b" + args[0]);
        sender.sendMessage("§eUUID : §b" + targetUUID.toString());
        sender.sendMessage("§eMute : " + (ValkyaCore.getInstance().getMuteManager().isMuted(targetUUID) ? "§a✔" : "§c✖"));

        if(ValkyaCore.getInstance().getMuteManager().isMuted(targetUUID)){
            sender.sendMessage("");
            sender.sendMessage("§6Raison : §c" + ValkyaCore.getInstance().getMuteManager().getReason(targetUUID));
            sender.sendMessage("§6Temps restant : §f" + ValkyaCore.getInstance().getMuteManager().getTimeLeft(targetUUID));
            sender.sendMessage("");
        }


        sender.sendMessage("§eBanni : " + (ValkyaCore.getInstance().getBanManager().isBanned(targetUUID) ? "§a✔" : "§c✖"));

        if(ValkyaCore.getInstance().getBanManager().isBanned(targetUUID)){
            sender.sendMessage("");
            sender.sendMessage("§6Raison : §c" + ValkyaCore.getInstance().getBanManager().getReason(targetUUID));
            sender.sendMessage("§6Temps restant : §f" + ValkyaCore.getInstance().getBanManager().getTimeLeft(targetUUID));
        }

        sender.sendMessage("§7-----------------------------------------------------");

        return false;
    }
}
