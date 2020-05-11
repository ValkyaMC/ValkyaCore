package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player) sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, new PermissionsManager().reportUse)) return false;
        if (args.length <= 1) {
            helpMessage(player);
            return false;
        }

        if (!ValkyaCore.getInstance().getPlayerUtils().isOnlinePlayer(player, args[0])) return false;
        Player target = Bukkit.getPlayer(args[0]);

        String reason = "";

        for (int i = 1; i < args.length; i++) {
            reason += args[i] + " ";
        }
        player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de signaler §6" + target.getName() + " §epour §6" + reason + " §e.");
        sendToMods(reason, target.getName(), player.getName());
        return false;
    }

    private void helpMessage(Player player) {
        player.sendMessage(ValkyaCore.PREFIX + " §c/report <joueur> <raison(s)>");
    }

    private void sendToMods(String reason, String targetName, String playerName) {
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            if (players.hasPermission(new PermissionsManager().reportReceive)) {
                players.sendMessage(ValkyaCore.PREFIX + " §eLe joueur §6" + targetName + " §ea été signalé pour §6" + reason + "§e(Signalé par: " + playerName + ").");
            }
        }
    }
}
