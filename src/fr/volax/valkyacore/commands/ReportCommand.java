package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import fr.volax.valkyacore.tools.ConfigBuilder;
import fr.volax.valkyacore.tools.ConfigType;
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
        player.sendMessage(ConfigBuilder.getCString("messages.report.have-been-reported", ConfigType.MESSAGES).replaceAll("%player%", target.getName()).replaceAll("%reason%", reason));
        sendToMods(reason, target.getName(), player.getName());
        return false;
    }

    private void helpMessage(Player player) {
        player.sendMessage(ConfigBuilder.getCString("messages.report.help-messsage", ConfigType.MESSAGES));
    }

    private void sendToMods(String reason, String targetName, String playerName) {
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            if (players.hasPermission(new PermissionsManager().reportReceive)) {
                players.sendMessage(ConfigBuilder.getCString("messages.report.report-message", ConfigType.MESSAGES).replaceAll("%reportedPlayer%", targetName).replaceAll("%reason%", reason).replaceAll("%player%", playerName));
            }
        }
    }
}
