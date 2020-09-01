/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.SpawnersEnum;
import fr.volax.valkyacore.util.ValkyaUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SpawnersCommand implements CommandExecutor {
    public SpawnersCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            helpMessage(sender);
            return false;
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("gui")){
                //TODO gui avec la liste des spawners
            }else{
                helpMessage(sender);
                return false;
            }
        }else if(args.length == 4){
            if(args[0].equalsIgnoreCase("add")){
                if(ValkyaCore.getInstance().getPlayerUtils().exist(args[1])){
                    String owner = args[1];
                    if(!SpawnersEnum.existFromName(args[2])){
                        for(SpawnersEnum name : SpawnersEnum.values())
                            ValkyaUtils.sendChat(sender,"§b" + name.getDisplayName() + " §f: §e" + name.getName());
                        return false;
                    }else{
                        Material spawner = Material.getMaterial(SpawnersEnum.getIDByName(args[2]));
                        if(!ValkyaCore.getInstance().isInt(args[3])){
                            ValkyaUtils.sendChat(sender, "§eLe chiffre n'est pas valide !");
                            return false;
                        }
                        int number = Integer.parseInt(args[3]);


                    }
                }else{
                    ValkyaUtils.sendChat(sender, "§eLe joueur n'existe pas !");
                    return false;
                }
            }
        }else{
            helpMessage(sender);
            return false;
        }
        return false;
    }

    private void helpMessage(CommandSender sender){
        if(sender.hasPermission(ValkyaCore.getInstance().getPermissionsHelper().spawnersAdd))
            ValkyaUtils.sendChat(sender, "§e/spawners add <Joueur> <Spawner> <Nombre>");
        if(sender.hasPermission(ValkyaCore.getInstance().getPermissionsHelper().spawnersRemove))
            ValkyaUtils.sendChat(sender, "§e/spawners remove <Joueur> <Spawner> <Nombre>");
        ValkyaUtils.sendChat(sender, "§e/spawners transfert <Joueur> <Spawner> <Nombre>");
        ValkyaUtils.sendChat(sender, "§e/spawners gui");
    }
}
