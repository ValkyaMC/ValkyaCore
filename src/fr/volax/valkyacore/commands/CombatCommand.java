/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.utils.ValkyaUtils;
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
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.combat")) return false;
        Player player = (Player)sender;

        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(player)){
            ValkyaUtils.sendChat(player, "§eVous êtes en combat pendant encore §6"+ ValkyaCore.getInstance().getPvPPlayerManager().getPvPPlayer(player).getTimeToPvP() +" §eseconde(s).");
        }else{
            ValkyaUtils.sendChat(player, "§eVous n'êtes pas en combat !");
        }
        return false;
    }
}
