package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EcCommand implements CommandExecutor {
    EcCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.ec")) return false;
        Player player = (Player)sender;
        if(args.length == 0){
            player.performCommand("customec open");
            return false;
        }else if(args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            player.performCommand("customec open " + target.getUniqueId());
            return false;
        }
        return false;
    }
}

