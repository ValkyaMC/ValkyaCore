package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CombatCommand implements CommandExecutor {
    public CombatCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player)sender;

        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(player)){
            player.sendMessage(ValkyaCore.PREFIX + " §eVous êtes en combat pendant encore §6"+ ValkyaCore.getInstance().getPvPPlayerManager().getPvPPlayer(player).getTimeToPvP() +" §eseconde(s).");
            return false;
        }else{
            player.sendMessage(ValkyaCore.PREFIX + " §eVous n'êtes pas en combat !");
            return false;
        }
    }
}
