package fr.volax.valkyacore.managers;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.utils.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class MuteManager {
    public boolean mute(UUID playeruuid, CommandSender moderator, long endInSeconds, String reason, String[] args){
        if(isMuted(playeruuid)) return false;

        long endToMillis = endInSeconds * 1000;
        long end = endToMillis + System.currentTimeMillis();

        if(endInSeconds == -1){
            end = -1;
        }

        Player playerP = Bukkit.getPlayer(playeruuid);

        if(playerP != null){
            if(playerP.hasPermission(new PermissionsManager().muteBypass)){
                moderator.sendMessage("§6Valorion » §cVous ne pouvez pas mute ce joueur !");
                return false;
            }
        }

        if(moderator instanceof Player){
            Player moderatorP = (Player) moderator;
            try {
                PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO mutes (playerName, playerUUID, moderatorName, moderatorUUID, end, reason) VALUES (?,?,?,?,?,?)");
                query.setString(1, args[0]);
                query.setString(2, playeruuid.toString());
                query.setString(3, moderatorP.getName());
                query.setString(4, moderatorP.getUniqueId().toString());
                query.setLong(5, end);
                query.setString(6, reason);
                query.executeUpdate();
                PreparedStatement queryLogs = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO logs (playerName, playerUUID, moderatorName, moderatorUUID, type, reason) VALUES (?,?,?,?,?,?)");
                queryLogs.setString(1, args[0]);
                queryLogs.setString(2, playeruuid.toString());
                queryLogs.setString(3, moderatorP.getName());
                queryLogs.setString(4, moderatorP.getUniqueId().toString());
                queryLogs.setString(5, "mute");
                queryLogs.setString(6, reason);
                queryLogs.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(playerP != null){
                playerP.sendMessage("§6Valorion » §cVous avez été mute ! §6Raison:§f " + ChatColor.translateAlternateColorCodes('&', String.join(" ", reason)) + "\n" + "§6Valorion » §aTemps restant: §f" + getTimeLeft(playeruuid));
                return true;
            }
        }else{
            try {
                int randomint = new Random().nextInt(10 * 100000);
                PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO mutes (playerName, playerUUID, moderatorName, moderatorUUID, end, reason) VALUES (?,?,?,?,?,?)");
                query.setString(1, args[0]);
                query.setString(2, playeruuid.toString());
                query.setString(3, "Console");
                query.setString(4, "ConsoleUUID." + randomint);
                query.setLong(5, end);
                query.setString(6, reason);
                query.executeUpdate();
                PreparedStatement queryLogs = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO logs (playerName, playerUUID, moderatorName, moderatorUUID, type, reason) VALUES (?,?,?,?,?,?)");
                queryLogs.setString(1, args[0]);
                queryLogs.setString(2, playeruuid.toString());
                queryLogs.setString(3, "Console");
                queryLogs.setString(4, "ConsoleUUID." + randomint);
                queryLogs.setString(5, "mute");
                queryLogs.setString(6, reason);
                queryLogs.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(playerP != null){
                playerP.sendMessage("§cVous avez été mute ! \n" + "§6Raison:§f " + ChatColor.translateAlternateColorCodes('&', String.join(" ", reason)) + "\n" + "§aTemps restant: §f" + getTimeLeft(playeruuid));
                return true;
            }
        }
        return false;
    }

    public void unmute(UUID uuid){
        if(!isMuted(uuid)) return;

        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("DELETE FROM mutes WHERE playerUUID=?");
            query.setString(1, uuid.toString());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isMuted(UUID uuid){
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM mutes WHERE playerUUID=?");
            query.setString(1, uuid.toString());
            ResultSet rs = query.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void checkDuration(UUID uuid){
        if(!isMuted(uuid)) return;
        if(getEnd(uuid) == -1) return;

        if(getEnd(uuid) < System.currentTimeMillis()){
            unmute(uuid);
        }
    }

    public long getEnd(UUID uuid){
        if(!isMuted(uuid)) return 0;
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM mutes WHERE playerUUID=?");
            query.setString(1, uuid.toString());
            ResultSet rs = query.executeQuery();
            if(rs.next()){
                return rs.getLong("end");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getTimeLeft(UUID uuid){
        if(!isMuted(uuid)) return "§cNon Mute";
        if(getEnd(uuid) == -1 ) return "§fPermanent";

        long timeLeft = (getEnd(uuid) - System.currentTimeMillis()) / 1000;
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

    public String getReason(UUID uuid){
        if(!isMuted(uuid)) return "§cNon Mute";
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM mutes WHERE playerUUID=?");
            query.setString(1, uuid.toString());
            ResultSet rs = query.executeQuery();
            if(rs.next()){
                String reason = rs.getString("reason");
                return ChatColor.translateAlternateColorCodes('&', String.join(" ", reason));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "§cNon Mute";
    }
}
