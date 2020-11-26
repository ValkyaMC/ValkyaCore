/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.utils.ValkyaUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    GamemodeCommand(String[] string) {
        for(String command : string)
            ValkyaCore.getInstance().getCommand(command).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChange)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.gamemode")) return false;
        if(cmd.getName().equalsIgnoreCase("gamemode")){
            if(args.length == 0){
                helpMessage(sender);
                return false;
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("SURVIVAL") || args[0].equals("0")){
                    if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
                    Player player = (Player)sender;

                    player.setGameMode(GameMode.SURVIVAL);
                    ValkyaUtils.sendChat(player,"§eVous venez de passer en gamemode §6Survie§e.");
                    return false;
                }

                if(args[0].equalsIgnoreCase("CREATIVE") || args[0].equals("1")){
                    if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
                    Player player = (Player)sender;

                    player.setGameMode(GameMode.CREATIVE);
                    ValkyaUtils.sendChat(player,"§eVous venez de passer en gamemode §6Créatif§e.");
                    return false;
                }
                if(args[0].equalsIgnoreCase("ADVENTURE") || args[0].equals("2")){
                    if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
                    Player player = (Player)sender;

                    player.setGameMode(GameMode.ADVENTURE);
                    ValkyaUtils.sendChat(player,"§eVous venez de passer en gamemode §6Aventure§e.");
                    return false;
                }else{
                    helpMessage(sender);
                }
            }else if(args.length == 2){
                if(args[0].equalsIgnoreCase("SURVIVAL") || args[0].equals("0")){
                    Player target = Bukkit.getPlayer(args[1]);
                    if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChangeOther)) return false;
                    if(target == null) {
                        ValkyaUtils.sendChat(sender,"§eCe joueur n'est pas en ligne !");
                        return false;
                    }

                    target.setGameMode(GameMode.SURVIVAL);
                    ValkyaUtils.sendChat(target,"§eVous venez de passer en gamemode §6Survie§e.");
                    ValkyaUtils.sendChat(sender,"§eVous venez de passer §6" + target.getName() + " §een gamemode §6Survie§e.");
                    return false;
                }

                if(args[0].equalsIgnoreCase("CREATIVE") || args[0].equals("1")){
                    Player target = Bukkit.getPlayer(args[1]);
                    if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChangeOther)) return false;
                    if(target == null) {
                        ValkyaUtils.sendChat(sender,"§eCe joueur n'est pas en ligne !");
                        return false;
                    }

                    target.setGameMode(GameMode.CREATIVE);
                    ValkyaUtils.sendChat(target,"§eVous venez de passer en gamemode §6Créatif§e.");
                    ValkyaUtils.sendChat(sender,"§eVous venez de passer §6" + target.getName() + " §een gamemode §6Créatif§e.");
                    return false;
                }
                if(args[0].equalsIgnoreCase("ADVENTURE") || args[0].equals("2")){
                    Player target = Bukkit.getPlayer(args[1]);
                    if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChangeOther)) return false;
                    if(target == null) {
                        ValkyaUtils.sendChat(sender,"§eCe joueur n'est pas en ligne !");
                        return false;
                    }

                    target.setGameMode(GameMode.ADVENTURE);
                    ValkyaUtils.sendChat(target,"§eVous venez de passer en gamemode §6Aventure§e.");
                    ValkyaUtils.sendChat(sender,"§eVous venez de passer §6" + target.getName() + " §een gamemode §6Aventure§e.");
                    return false;
                }else{
                    helpMessage(sender);
                }
            }else{
                helpMessage(sender);
                return false;
            }
        }else if(cmd.getName().equalsIgnoreCase("gmc")){
            if(args.length == 0){
                if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
                Player player = (Player)sender;

                player.setGameMode(GameMode.CREATIVE);
                ValkyaUtils.sendChat(player,"§eVous venez de passer en gamemode §6Créatif§e.");
                return false;
            }else if(args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChangeOther)) return false;
                if(target == null) {
                    ValkyaUtils.sendChat(sender,"§eCe joueur n'est pas en ligne !");
                    return false;
                }
                target.setGameMode(GameMode.CREATIVE);
                ValkyaUtils.sendChat(target,"§eVous venez de passer en gamemode §6Créatif§e.");
                ValkyaUtils.sendChat(sender,"§eVous venez de passer §6" + target.getName() + " §een gamemode §6Créatif§e.");
                return false;
            }
        }else if(cmd.getName().equalsIgnoreCase("gms")){
            if(args.length == 0){
                if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
                Player player = (Player)sender;

                player.setGameMode(GameMode.SURVIVAL);
                ValkyaUtils.sendChat(player,"§eVous venez de passer en gamemode §6Survie§e.");
                return false;
            }else if(args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChangeOther)) return false;
                if(target == null) {
                    ValkyaUtils.sendChat(sender,"§eCe joueur n'est pas en ligne !");
                    return false;
                }
                target.setGameMode(GameMode.SURVIVAL);
                ValkyaUtils.sendChat(target,"§eVous venez de passer en gamemode §6Survie§e.");
                ValkyaUtils.sendChat(sender,"§eVous venez de passer §6" + target.getName() + " §een gamemode §6Survie§e.");
                return false;
            }
        }else if(cmd.getName().equalsIgnoreCase("gma")){
            if(args.length == 0){
                if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
                Player player = (Player)sender;

                player.setGameMode(GameMode.ADVENTURE);
                ValkyaUtils.sendChat(player,"§eVous venez de passer en gamemode §6Aventure§e.");
                return false;
            }else if(args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChangeOther)) return false;
                if(target == null) {
                    ValkyaUtils.sendChat(sender,"§eCe joueur n'est pas en ligne !");
                    return false;
                }
                target.setGameMode(GameMode.ADVENTURE);
                ValkyaUtils.sendChat(target,"§eVous venez de passer en gamemode §6Aventure§e.");
                ValkyaUtils.sendChat(sender,"§eVous venez de passer §6" + target.getName() + " §een gamemode §6Aventure§e.");
                return false;
            }
        }
        return false;
    }

    private void helpMessage(CommandSender player){
        ValkyaUtils.sendChat(player,"§e/gamemode <SURVIVAL|CREATIVE|ADVENTURE> [<joueur>]");
    }
}
