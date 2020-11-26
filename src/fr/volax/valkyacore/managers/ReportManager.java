/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.managers;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.volaxapi.tool.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            PreparedStatement queryLogs = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO logs (playerName, playerUUID, moderatorName, moderatorUUID, type, reason) VALUES (?,?,?,?,?,?)");
            queryLogs.setString(1, args[0]);
            queryLogs.setString(2, reported.getUniqueId().toString());
            queryLogs.setString(3, reporterP.getName());
            queryLogs.setString(4, reporterP.getUniqueId().toString());
            queryLogs.setString(5, "report");
            queryLogs.setString(6, reason);
            queryLogs.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<ItemStack> getAllReport(String playerName){
        try{
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM reports WHERE reportedName=?");
            query.setString(1, playerName);
            ResultSet rs = query.executeQuery();
            List<ItemStack> reports = new ArrayList<>();
            while(rs.next()){
                ItemStack report = new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner(rs.getString("reporterName")).setName("§eReport N°§6" + rs.getInt("id")).setLore("§eReport par §6" + rs.getString("reporterName"), "§eReport pour la raison §6" + rs.getString("reason"), "", "§eClic droit pour supprimer le report").toItemStack();
                reports.add(report);
            }
            return reports;
        }catch (SQLException e){ e.printStackTrace();}
        return null;
    }

    public void deleteReport(int id){
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("DELETE FROM reports WHERE id=?");
            query.setInt(1, id);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
