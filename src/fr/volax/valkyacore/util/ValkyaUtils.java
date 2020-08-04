package fr.volax.valkyacore.util;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ValkyaUtils {
    public static void sendChat(CommandSender player, String string){
        player.sendMessage(ValkyaCore.getPREFIX() + " " + string);
    }

    public static void broadcast(String string){
        Bukkit.broadcastMessage(ValkyaCore.getPREFIX() + " " + string);
    }
}
