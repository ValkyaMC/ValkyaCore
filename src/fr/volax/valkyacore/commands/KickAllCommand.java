package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KickAllCommand implements CommandExecutor {
    public KickAllCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().kickAllUse)) return false;
        if(args.length < 1){
            helpMessage(sender);
            return false;
        }

        String kickMessage = ChatColor.translateAlternateColorCodes('&', String.join(" ", args));

        List<Player> players = new ArrayList<>();

        for(Player allPlayers : Bukkit.getOnlinePlayers()){
            if(!allPlayers.hasPermission(ValkyaCore.getInstance().getPermissionsHelper().kickAllBypass))
            if(allPlayers != sender)
                players.add(allPlayers);
        }

        if(players.isEmpty()){
            sender.sendMessage(ValkyaCore.PREFIX + " §eIl n'y a aucun joueur à kick !");
            return false;
        }

        players.forEach(player -> player.kickPlayer(kickMessage));
        sender.sendMessage(ValkyaCore.PREFIX + " §eVous venez de kick tout les joueurs du serveur pour la raison: §r" + kickMessage);
        return false;
    }

    private void helpMessage(CommandSender sender) {
        sender.sendMessage(ValkyaCore.PREFIX + " §e/kickall <message>");
    }
}
