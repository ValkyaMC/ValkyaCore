package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.StaffInventory;
import fr.volax.valkyacore.util.ValkyaUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class StaffModListCommand implements CommandExecutor {
    public StaffModListCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().staffmodeListUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.staffmodlist")) return false;

        if(ValkyaCore.getInstance().mode.isEmpty()){
            ValkyaUtils.sendChat(sender, "§eIl n'y a actuellement aucun staff en staffmod !");
            return false;
        }else{
            StringBuilder mods = new StringBuilder();

            for(Map.Entry<Player, StaffInventory> player : ValkyaCore.getInstance().mode.entrySet()){
                mods.append(player.getKey().getName()).append(" ");
            }
            ValkyaUtils.sendChat(sender, "§eIl y a actuellement §6" + mods + " §een staffmod !");
        }
        return false;
    }
}
