package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {
    public BroadcastCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            helpMessage(sender);
        }else{
            if(sender instanceof Player){
                if (!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().broadcastUse)) return false;
                    Bukkit.broadcastMessage(ValkyaCore.PREFIX + " §r" + ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
                    return false;
            }else{
                Bukkit.broadcastMessage(ValkyaCore.PREFIX + " §r" + ChatColor.translateAlternateColorCodes('&', String.join(" ", args)));
                return false;
            }
        }
        return false;
    }

    private void helpMessage(CommandSender player){
        player.sendMessage(ValkyaCore.PREFIX + " §e/broadcast <messages>");
    }
}
