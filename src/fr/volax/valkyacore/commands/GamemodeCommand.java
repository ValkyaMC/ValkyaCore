package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.PermissionsHelper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    GamemodeCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChange)) return false;

        if(args.length == 0){
            helpMessage(sender);
            return false;
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("SURVIVAL") || args[0].equals("0")){
                if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
                Player player = (Player)sender;

                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de passer en gamemode §6Survie§e.");
                return false;
            }

            if(args[0].equalsIgnoreCase("CREATIVE") || args[0].equals("1")){
                if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
                Player player = (Player)sender;

                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de passer en gamemode §6Créatif§e.");
                return false;
            }
            if(args[0].equalsIgnoreCase("ADVENTURE") || args[0].equals("2")){
                if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
                Player player = (Player)sender;

                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de passer en gamemode §6Aventure§e.");
                return false;
            }else{
                helpMessage(sender);
            }
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("SURVIVAL") || args[0].equals("0")){
                Player target = Bukkit.getPlayer(args[1]);
                if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChangeOther)) return false;
                if(target == null) {
                    sender.sendMessage(ValkyaCore.PREFIX + " §eCe joueur n'est pas en ligne !");
                    return false;
                }

                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(ValkyaCore.PREFIX + " §eVous venez de passer en gamemode §6Survie§e.");
                sender.sendMessage(ValkyaCore.PREFIX + " §eVous venez de passer §6" + target.getName() + " §een gamemode §6Survie§e.");
                return false;
            }

            if(args[0].equalsIgnoreCase("CREATIVE") || args[0].equals("1")){
                Player target = Bukkit.getPlayer(args[1]);
                if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChangeOther)) return false;
                if(target == null) {
                    sender.sendMessage(ValkyaCore.PREFIX + " §eCe joueur n'est pas en ligne !");
                    return false;
                }

                target.setGameMode(GameMode.CREATIVE);
                target.sendMessage(ValkyaCore.PREFIX + " §eVous venez de passer en gamemode §6Créatif§e.");
                sender.sendMessage(ValkyaCore.PREFIX + " §eVous venez de passer §6" + target.getName() + " §een gamemode §6Créatif§e.");
                return false;
            }
            if(args[0].equalsIgnoreCase("ADVENTURE") || args[0].equals("2")){
                Player target = Bukkit.getPlayer(args[1]);
                if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().gamemodeChangeOther)) return false;
                if(target == null) {
                    sender.sendMessage(ValkyaCore.PREFIX + " §eCe joueur n'est pas en ligne !");
                    return false;
                }

                target.setGameMode(GameMode.ADVENTURE);
                target.sendMessage(ValkyaCore.PREFIX + " §eVous venez de passer en gamemode §6Aventure§e.");
                sender.sendMessage(ValkyaCore.PREFIX + " §eVous venez de passer §6" + target.getName() + " §een gamemode §6Aventure§e.");
                return false;
            }else{
                helpMessage(sender);
            }
        }else{
            helpMessage(sender);
            return false;
        }
        return false;
    }

    private void helpMessage(CommandSender player){
        player.sendMessage(ValkyaCore.PREFIX + " §e/gamemode <SURVIVAL|CREATIVE|ADVENTURE> [<joueur>]");
    }
}
