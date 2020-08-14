package fr.volax.valkyacore.managers;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Permet de gérer le système de report bdd avec la commande
 *
 * @author Volax
 * @since 1.1.00
 * @version 1.0
 * @see fr.volax.valkyacore.commands.ReportCommand
 */
public class ReportManager {
    /**
     * Permet d'enregistrer un report dans la base de donnée
     *
     * @param reported Personne qui est report
     * @param reporter Personne qui a report
     * @param reason Les raisons du report
     * @param args Les arguments de la commande /report
     */
    public void report(Player reported, CommandSender reporter, String reason, String[] args) {
        Player reporterP = (Player) reporter;

        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO reports (reportedName, reportedUUID, reporterName, reporterUUID, reason) VALUES (?,?,?,?,?)");
            query.setString(1, args[0]);
            query.setString(2, reported.getUniqueId().toString());
            query.setString(3, reporterP.getName());
            query.setString(4, reporterP.getUniqueId().toString());
            query.setString(5, reason);
            query.executeUpdate();
            ValkyaCore.getInstance().getPlayerUtils().incrementReportNumber(reported.getName());
            PreparedStatement queryLogs = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO logs (reportedName, reportedUUID, reporterName, reporterUUID, reason) VALUES (?,?,?,?,?)");
            queryLogs.setString(1, args[0]);
            query.setString(2, reported.getUniqueId().toString());
            query.setString(3, reporterP.getName());
            query.setString(4, reporterP.getUniqueId().toString());
            queryLogs.setString(5, "report");
            queryLogs.setString(6, reason);
            queryLogs.executeUpdate();
            throw new SQLException("Une erreur est survenue dans la BDD");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
