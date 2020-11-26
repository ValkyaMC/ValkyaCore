/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.utils;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ValkyaUtils {
    public static void sendChat(CommandSender player, String string){
        player.sendMessage(ValkyaCore.getPREFIX() + " " + string);
    }

    public static void broadcast(String string){
        Bukkit.broadcastMessage(ValkyaCore.getPREFIX() + " " + string);
    }
}
