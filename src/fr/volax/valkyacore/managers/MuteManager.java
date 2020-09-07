/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.managers;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.valkyacore.util.PermissionsHelper;
import fr.volax.volaxapi.tool.time.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MuteManager {
    public void mute(CommandSender moderator, long endInSeconds, String reason, String[] args){
        String playerName = ValkyaCore.getInstance().getPlayerUtils().getName(args[0]);
        if(isMuted(args[0])) return;

        long endToMillis = endInSeconds * 1000;
        long end = endToMillis + System.currentTimeMillis();

        if(endInSeconds == -1) end = -1;

        Player playerP = Bukkit.getPlayer(playerName);

        if(playerP != null){
            if(playerP.hasPermission(new PermissionsHelper().muteBypass)){
                moderator.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.cant-mute", ConfigType.MESSAGES.getConfigName()));
                return;
            }
        }

        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO mutes (playerName, moderatorName, end, reason) VALUES (?,?,?,?)");
            query.setString(1, playerName);
            query.setString(2, moderator.getName());
            query.setLong(3, end);
            query.setString(4, reason);
            query.executeUpdate();
            PreparedStatement queryLogs = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO logs (playerName, moderatorName, type, reason) VALUES (?,?,?,?)");
            queryLogs.setString(1, playerName);
            queryLogs.setString(2, moderator.getName());
            queryLogs.setString(3, "mute");
            queryLogs.setString(4, reason);
            queryLogs.executeUpdate();

            Bukkit.broadcastMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.have-been-permamute", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", playerName).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', String.join(" ", reason))));
            if(playerP != null) playerP.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.muted-player-message2", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', String.join(" ", reason))).replaceAll("%time%", getTimeLeft(playerName)));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void tempMute(CommandSender moderator, long endInSeconds, String reason, String[] args, TimeUnit unit, long duration){
        String playerName = ValkyaCore.getInstance().getPlayerUtils().getName(args[0]);
        if(isMuted(playerName)) return;

        long end = endInSeconds * 1000 + System.currentTimeMillis();
        Player playerP = Bukkit.getPlayer(playerName);

        if(playerP != null){
            if(playerP.hasPermission(new PermissionsHelper().muteBypass)){
                moderator.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.cant-mute", ConfigType.MESSAGES.getConfigName()));
                return;
            }
        }

        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO mutes (playerName, moderatorName, end, reason) VALUES (?,?,?,?)");
            query.setString(1, playerName);
            query.setString(2, moderator.getName());
            query.setLong(3, end);
            query.setString(4, reason);
            query.executeUpdate();
            PreparedStatement queryLogs = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO logs (playerName, moderatorName, type, reason) VALUES (?,?,?,?)");
            queryLogs.setString(1, playerName);
            queryLogs.setString(2, moderator.getName());
            queryLogs.setString(3, "mute");
            queryLogs.setString(4, reason);
            queryLogs.executeUpdate();

            Bukkit.broadcastMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.have-been-tempmute", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", playerName).replaceAll("%duration%", duration + " " + unit.getName()).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', String.join(" ", reason))));
            if(playerP != null) playerP.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.muted-player-message2", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', String.join(" ", reason))).replaceAll("%time%", getTimeLeft(playerName)));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void unmute(String nameDefault){
        String name = ValkyaCore.getInstance().getPlayerUtils().getName(nameDefault);
        if(!isMuted(name)) return;

        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("DELETE FROM mutes WHERE playerName=?");
            query.setString(1, name);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isMuted(String nameDefault){
        String name = ValkyaCore.getInstance().getPlayerUtils().getName(nameDefault);
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM mutes WHERE playerName=?");
            query.setString(1, name);
            ResultSet rs = query.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void checkDuration(String nameDefault){
        String name = ValkyaCore.getInstance().getPlayerUtils().getName(nameDefault);
        if(!isMuted(name)) return;
        if(getEnd(name) == -1) return;
        if(getEnd(name) < System.currentTimeMillis()) unmute(name);
    }

    public long getEnd(String nameDefault){
        String name = ValkyaCore.getInstance().getPlayerUtils().getName(nameDefault);
        if(!isMuted(name)) return 0;
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM mutes WHERE playerName=?");
            query.setString(1, name);
            ResultSet rs = query.executeQuery();
            if(rs.next()){
                return rs.getLong("end");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getTimeLeft(String nameDefault){
        String name = ValkyaCore.getInstance().getPlayerUtils().getName(nameDefault);
        if(!isMuted(name)) return ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.not-mute", ConfigType.MESSAGES.getConfigName());
        if(getEnd(name) == -1 ) return ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.permanent", ConfigType.MESSAGES.getConfigName());

        long timeLeft = (getEnd(name) - System.currentTimeMillis()) / 1000;
        int mois = 0;
        int jours = 0;
        int heures = 0;
        int minutes = 0;
        int secondes = 0;

        while (timeLeft >= TimeUnit.MOIS.getToSecond()){
            mois++;
            timeLeft -= TimeUnit.MOIS.getToSecond();
        }

        while (timeLeft >= TimeUnit.JOUR.getToSecond()){
            jours++;
            timeLeft -= TimeUnit.JOUR.getToSecond();
        }

        while (timeLeft >= TimeUnit.HEURE.getToSecond()){
            heures++;
            timeLeft -= TimeUnit.HEURE.getToSecond();
        }

        while (timeLeft >= TimeUnit.MINUTE.getToSecond()){
            minutes++;
            timeLeft -= TimeUnit.MINUTE.getToSecond();
        }

        while (timeLeft >= TimeUnit.SECONDE.getToSecond()){
            secondes++;
            timeLeft -= TimeUnit.SECONDE.getToSecond();
        }

        return mois + " " + TimeUnit.MOIS.getName() + ", " + jours + " " + TimeUnit.JOUR.getName() + ", " + heures + " " + TimeUnit.HEURE.getName() + ", " + minutes + " " + TimeUnit.MINUTE.getName() + ", " + secondes + " " + TimeUnit.SECONDE.getName();
    }

    public String getReason(String nameDefault){
        String name = ValkyaCore.getInstance().getPlayerUtils().getName(nameDefault);
        if(!isMuted(name)) return ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.not-mute", ConfigType.MESSAGES.getConfigName());
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM mutes WHERE playerName=?");
            query.setString(1, name);
            ResultSet rs = query.executeQuery();
            if(rs.next()){
                String reason = rs.getString("reason");
                return ChatColor.translateAlternateColorCodes('&', String.join(" ", reason));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ValkyaCore.getInstance().getConfigBuilder().getString("messages.mute.not-mute", ConfigType.MESSAGES.getConfigName());
    }
}
