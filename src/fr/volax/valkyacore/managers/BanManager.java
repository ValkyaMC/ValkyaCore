/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.managers;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tools.ConfigType;
import fr.volax.volaxapi.tool.time.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BanManager {
    public void ban(UUID playeruuid, CommandSender moderator, long endInSeconds, String reason, String[] args) {
        if (isBanned(playeruuid)) return;

        long endToMillis = endInSeconds * 1000;
        long end = endToMillis + System.currentTimeMillis();

        if (endInSeconds == -1) {
            end = -1;
        }

        Player playerP = Bukkit.getPlayer(playeruuid);

        if (playerP != null) {
            if (playerP.hasPermission(ValkyaCore.getInstance().getPermissionsHelper().banBypass)) {
                moderator.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.cant-ban", ConfigType.MESSAGES.getConfigName()));
                return;
            }
        }

        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO bans (playerName, playerUUID, moderatorName, end, reason) VALUES (?,?,?,?,?)");
            query.setString(1, args[0]);
            query.setString(2, playeruuid.toString());
            query.setString(3, moderator.getName());
            query.setLong(4, end);
            query.setString(5, reason);
            query.executeUpdate();
            PreparedStatement queryLogs = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO logs (playerName, playerUUID, moderatorName, type, reason) VALUES (?,?,?,?,?)");
            queryLogs.setString(1, args[0]);
            queryLogs.setString(2, playeruuid.toString());
            queryLogs.setString(3, moderator.getName());
            queryLogs.setString(4, "ban");
            queryLogs.setString(5, reason);
            queryLogs.executeUpdate();

            Bukkit.broadcastMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.have-been-permaban", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", ValkyaCore.getInstance().getPlayerUtils().getName(playeruuid)).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', String.join(" ", reason.toString()))));
            if (playerP != null) playerP.kickPlayer(ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.banned-player-join", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ValkyaCore.getInstance().getBanManager().getReason(playeruuid)).replaceAll("%time%", ValkyaCore.getInstance().getBanManager().getTimeLeft(playeruuid)));
            } catch (SQLException e) { e.printStackTrace(); }
    }

    public void tempBan(UUID playeruuid, CommandSender moderator, long endInSeconds, String reason, String[] args, TimeUnit unit, long duration) {
        if (isBanned(playeruuid)) return;

        long endToMillis = endInSeconds * 1000;
        long end = endToMillis + System.currentTimeMillis();
        Player playerP = Bukkit.getPlayer(playeruuid);

        if (playerP != null) {
            if (playerP.hasPermission(ValkyaCore.getInstance().getPermissionsHelper().banBypass)) {
                moderator.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.cant-ban", ConfigType.MESSAGES.getConfigName()));
                return;
            }
        }

        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO bans (playerName, playerUUID, moderatorName, end, reason) VALUES (?,?,?,?,?)");
            query.setString(1, args[0]);
            query.setString(2, playeruuid.toString());
            query.setString(3, moderator.getName());
            query.setLong(4, end);
            query.setString(5, reason);
            query.executeUpdate();
            PreparedStatement queryLogs = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO logs (playerName, playerUUID, moderatorName, type, reason) VALUES (?,?,?,?,?)");
            queryLogs.setString(1, args[0]);
            queryLogs.setString(2, playeruuid.toString());
            queryLogs.setString(3, moderator.getName());
            queryLogs.setString(4, "ban");
            queryLogs.setString(5, reason);
            queryLogs.executeUpdate();

            Bukkit.broadcastMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.have-been-tempban", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", ValkyaCore.getInstance().getPlayerUtils().getName(playeruuid)).replaceAll("%duration%", duration + " " + unit.getName()).replaceAll("%reason%", ChatColor.translateAlternateColorCodes('&', String.join(" ", reason.toString()))));
            if (playerP != null) playerP.kickPlayer(ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.banned-player-join", ConfigType.MESSAGES.getConfigName()).replaceAll("%reason%", ValkyaCore.getInstance().getBanManager().getReason(playeruuid)).replaceAll("%time%", ValkyaCore.getInstance().getBanManager().getTimeLeft(playeruuid)));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void unban(UUID uuid) {
        if (!isBanned(uuid)) return;

        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("DELETE FROM bans WHERE playerUUID=?");
            query.setString(1, uuid.toString());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isBanned(UUID uuid) {
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM bans WHERE playerUUID=?");
            query.setString(1, uuid.toString());
            ResultSet rs = query.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void checkDuration(UUID uuid) {
        if (!isBanned(uuid)) return;
        if (getEnd(uuid) == -1) return;

        if (getEnd(uuid) < System.currentTimeMillis()) {
            unban(uuid);
        }
    }

    public long getEnd(UUID uuid) {
        if (!isBanned(uuid)) return 0;
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM bans WHERE playerUUID=?");
            query.setString(1, uuid.toString());
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                return rs.getLong("end");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getTimeLeft(UUID uuid) {
        if (!isBanned(uuid)) return ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.not-ban", ConfigType.MESSAGES.getConfigName());
        if (getEnd(uuid) == -1) return ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.permanent", ConfigType.MESSAGES.getConfigName());

        long timeLeft = (getEnd(uuid) - System.currentTimeMillis()) / 1000;
        int mois = 0;
        int jours = 0;
        int heures = 0;
        int minutes = 0;
        int secondes = 0;

        while (timeLeft >= TimeUnit.MOIS.getToSecond()) {
            mois++;
            timeLeft -= TimeUnit.MOIS.getToSecond();
        }

        while (timeLeft >= TimeUnit.JOUR.getToSecond()) {
            jours++;
            timeLeft -= TimeUnit.JOUR.getToSecond();
        }

        while (timeLeft >= TimeUnit.HEURE.getToSecond()) {
            heures++;
            timeLeft -= TimeUnit.HEURE.getToSecond();
        }

        while (timeLeft >= TimeUnit.MINUTE.getToSecond()) {
            minutes++;
            timeLeft -= TimeUnit.MINUTE.getToSecond();
        }

        while (timeLeft >= TimeUnit.SECONDE.getToSecond()) {
            secondes++;
            timeLeft -= TimeUnit.SECONDE.getToSecond();
        }

        return mois + " " + TimeUnit.MOIS.getName() + ", " + jours + " " + TimeUnit.JOUR.getName() + ", " + heures + " " + TimeUnit.HEURE.getName() + ", " + minutes + " " + TimeUnit.MINUTE.getName() + ", " + secondes + " " + TimeUnit.SECONDE.getName();
    }

    public String getReason(UUID uuid) {
        if (!isBanned(uuid)) return ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.not-ban", ConfigType.MESSAGES.getConfigName());
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM bans WHERE playerUUID=?");
            query.setString(1, uuid.toString());
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                String reason = rs.getString("reason");
                return ChatColor.translateAlternateColorCodes('&', String.join(" ", reason));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ValkyaCore.getInstance().getConfigBuilder().getString("messages.ban.not-ban", ConfigType.MESSAGES.getConfigName());
    }
}
