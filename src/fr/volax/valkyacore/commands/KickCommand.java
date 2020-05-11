package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, new PermissionsManager().kickUse)) return false;

        if(args.length < 2){
            helpMessage(sender);
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().isOnlinePlayer(sender, args[0])) return false;
        Player player = Bukkit.getPlayer(args[0]);

        String reason = "";
        for(int i = 1; i < args.length; i++){
            reason += args[i] + " ";
        }
        if(player.hasPermission(new PermissionsManager().kickBypass)){
            sender.sendMessage(ValkyaCore.PREFIX + " §cVous ne pouvez pas kick ce joueur !");
            return false;
        }
        player.kickPlayer("§cVous avez été kick ! \n" + "\n" + "§6Raison:§f " + ChatColor.translateAlternateColorCodes('&', String.join(" ", reason)));
        sender.sendMessage(ValkyaCore.PREFIX + " §aVous avez kick §e" + player.getName() + " §apour §e" + ChatColor.translateAlternateColorCodes('&', String.join(" ", reason)));
        return false;
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage(ValkyaCore.PREFIX + " §c/kick <joueur> <raison>");
    }
}
