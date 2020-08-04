package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.ValkyaUtils;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KDCommand implements CommandExecutor {
    KDCommand(String name) {
        ValkyaCore.getInstance().getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player)sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, ValkyaCore.getInstance().getPermissionsHelper().kdrUse)) return false;

        if(args.length == 0){
            ValkyaUtils.sendChat(player,"§eStatistiques de §6"+ player.getName() +"§e:");
            ValkyaUtils.sendChat(player,"§eNombre de Kills: §6" + player.getStatistic(Statistic.PLAYER_KILLS) + "§e.");
            ValkyaUtils.sendChat(player,"§eNombre de Morts: §6" + player.getStatistic(Statistic.DEATHS) + "§e.");
            return false;
        }else if(args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                ValkyaUtils.sendChat(player,"§eCe joueur n'est pas en ligne !");
                return false;
            }
            ValkyaUtils.sendChat(player,"§eStatistiques de §6"+ args[0] +"§e:");
            ValkyaUtils.sendChat(player,"§eNombre de Kills: §6" + target.getStatistic(Statistic.PLAYER_KILLS) + "§e.");
            ValkyaUtils.sendChat(player,"§eNombre de Morts: §6" + target.getStatistic(Statistic.DEATHS) + "§e.");
        }else{
            helpMessage(player);
        }
        return false;
    }

    private void helpMessage(CommandSender player){
        ValkyaUtils.sendChat(player,"§e/kdr [<joueur>]");
    }
}
