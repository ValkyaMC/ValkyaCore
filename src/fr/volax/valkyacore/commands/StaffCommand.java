package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import fr.volax.valkyacore.tools.ConfigBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player) sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsManager().staffUse)) return false;

        if (args.length == 0) {
            //player.openInventory(Managers.getManagers().getInventoriesManager().getStaffInventory());
            return false;
        } else if (args[0].equalsIgnoreCase("spamchat") || args[0].equalsIgnoreCase("cooldownchat")) {
            if (!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, new PermissionsManager().cooldownChatModif)) return false;
            if (args.length != 2) {
                    player.sendMessage(ValkyaCore.PREFIX + " §eLe cooldownchat est actuellement défini sur §6" + ConfigBuilder.getCInt("cooldownchat.time", "cooldownchat.yml") + " §eseconde(s) !");
            } else {
                if (ValkyaCore.getInstance().isInt(args[1])) {
                    ConfigBuilder.setCInt("cooldownchat.time", Integer.parseInt(args[1]), "cooldownchat.yml");
                    player.sendMessage(ValkyaCore.PREFIX + ConfigBuilder.getCString("messages.cooldownchat-set", "cooldownchat.yml").replaceAll("%secondes%", args[1]));
                } else {
                    player.sendMessage(ValkyaCore.PREFIX + ConfigBuilder.getCString("messages.pas-chiffre", "cooldownchat.yml").replaceAll("%arg1%", args[1]));
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
    }
}
