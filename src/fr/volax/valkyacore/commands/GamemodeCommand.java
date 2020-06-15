package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GamemodeCommand implements CommandExecutor {
    GamemodeCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //TODO faire la commande
        return false;
    }
}
