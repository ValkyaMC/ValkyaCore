package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.PermissionsHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetMatCommand implements CommandExecutor {
    GetMatCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player)sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsHelper().getMatUse)) return false;

        player.sendMessage(ValkyaCore.PREFIX + " §r" + player.getItemInHand().getType().name());
        return false;
    }
}
