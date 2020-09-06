/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.ReportManager;
import fr.volax.valkyacore.util.PermissionsHelper;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class de la commande /report
 *
 * @author Volax
 * @since 1.1.00
 * @version 1.0
 * @see CommandManager
 * @see ReportManager
 */
public class ReportCommand implements CommandExecutor {
    ReportCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    /**
     * Commande de /report
     *
     * @see ReportManager#report(Player, CommandSender, String, String[])
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player) sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, ValkyaCore.getInstance().getPermissionsHelper().reportUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.report")) return false;
        if (args.length <= 1) {
            helpMessage(player);
            return false;
        }

        if (!ValkyaCore.getInstance().getPlayerUtils().isOnlinePlayer(player, args[0])) return false;
        Player target = Bukkit.getPlayer(args[0]);

        StringBuilder reason = new StringBuilder();
        for (int i = 1; i < args.length; i++) reason.append(args[i]).append(" ");

        player.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.report.have-been-reported", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", target.getName()).replaceAll("%reason%", reason.toString()));
        sendToMods(reason.toString(), target.getName(), player.getName());
        ValkyaCore.getInstance().getReportManager().report(target, sender, reason.toString(), args);
        return false;
    }

    private void helpMessage(Player player) {
        player.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.report.help-message", ConfigType.MESSAGES.getConfigName()));
    }

    private void sendToMods(String reason, String targetName, String playerName) {
        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            if (players.hasPermission(new PermissionsHelper().reportReceive)) {
                players.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.report.report-message", ConfigType.MESSAGES.getConfigName()).replaceAll("%reportedPlayer%", targetName).replaceAll("%reason%", reason).replaceAll("%player%", playerName));
            }
        }
    }
}
