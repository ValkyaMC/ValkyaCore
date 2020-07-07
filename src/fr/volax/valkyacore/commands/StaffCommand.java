package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.PermissionsHelper;
import fr.volax.valkyacore.tool.ConfigBuilder;
import fr.volax.valkyacore.tool.ConfigType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {

    StaffCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player) sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsHelper().staffUse)) return false;

        player.sendMessage(ValkyaCore.PREFIX + " §eSoon !");
/**
        if (args.length == 0) {
            //player.openInventory(Managers.getManagers().getInventoriesManager().getStaffInventory());
            return false;
        } else if (args[0].equalsIgnoreCase("spamchat") || args[0].equalsIgnoreCase("cooldownchat")) {
            if (!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, new PermissionsHelper().cooldownChatModif)) return false;
            if (args.length != 2) {
                player.sendMessage(ConfigBuilder.getCString("messages.cooldownchat.current-cooldownchat", ConfigType.MESSAGES).replaceAll("%secondes%", String.valueOf(ConfigBuilder.getCInt("cooldownchat.time", ConfigType.COOLDOWNCHAT))));
            } else {
                if (ValkyaCore.getInstance().isInt(args[1])) {
                    ConfigBuilder.setCInt("cooldownchat.time", Integer.parseInt(args[1]), ConfigType.COOLDOWNCHAT);
                    player.sendMessage(ConfigBuilder.getCString("messages.cooldownchat.cooldownchat-set", ConfigType.MESSAGES).replaceAll("%secondes%", args[1]));
                } else {
                    player.sendMessage(ConfigBuilder.getCString("messages.cooldownchat.pas-chiffre", ConfigType.MESSAGES).replaceAll("%arg1%", args[1]));
                    return false;
                }
            }
            return false;

        } else if (args.length == 1) {
            player.openInventory(ValkyaCore.getInstance().getInventoriesManager().getStaffHelpInventory());
            return false;
        }
       //player.openInventory(Managers.getManagers().getInventoriesManager().getStaffInventory());
        return false;
 */
    return false;
    }
}
