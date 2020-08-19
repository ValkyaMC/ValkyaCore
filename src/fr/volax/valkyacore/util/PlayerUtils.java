package fr.volax.valkyacore.util;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class PlayerUtils {
    /**
     * Permet de vérifier si sender est un joueur
     * @param sender Joueur pour la vérification
     * @return false = sender n'est pas un joueur | true = sender est un joueur
     */
    public boolean isPlayer(CommandSender sender){
        if(!(sender instanceof Player)){
            sender.sendMessage(ConfigBuilder.getCString("messages.no-player", ConfigType.MESSAGES.getConfigName()));
            return false;
        }
        return true;
    }

    public boolean isAutoCommand(CommandSender sender, String command){
        if(!ConfigBuilder.getString(command).equalsIgnoreCase("true")){
            sender.sendMessage("§cCette commande est actuellement désactivé sur ce serveur !");
            return false;
        }else
            return true;
    }

    public boolean isOnlinePlayer(CommandSender sender, String target){
        if(!doesPlayerExist(sender, target)) return false;
        if(Bukkit.getPlayer(target) == null){
            sender.sendMessage(ConfigBuilder.getCString("messages.not-online", ConfigType.MESSAGES.getConfigName()));
            return false;
        }
        return true;

    }

    public boolean doesPlayerExist(CommandSender sender, String target){
        if(!exist(target)){
            sender.sendMessage( ConfigBuilder.getCString("messages.never-join", ConfigType.MESSAGES.getConfigName()));
            return false;
        }
        return true;
    }

    /**
     * Permet de vérifier si player a la permission
     * @param permission Permission à tester sur le joueur
     * @return Es-ce que le joueur à la permission
     */
    public boolean hasPerm(CommandSender sender, Permission permission){
        if(!sender.hasPermission(permission)){
            sender.sendMessage(ConfigBuilder.getCString("messages.no-perm", ConfigType.MESSAGES.getConfigName()));
            return false;
        }
        return true;
    }

    public void update(Player player){
        try {
            PreparedStatement sts = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT playerName FROM users WHERE playerUUID=?");
            sts.setString(1, player.getUniqueId().toString());
            ResultSet rs = sts.executeQuery();

            if(rs.next()){
                PreparedStatement update = ValkyaCore.getInstance().sql.connection.prepareStatement("UPDATE users SET playerName=?, lastIP=? WHERE playerUUID=?");
                update.setString(1, player.getName());
                update.setString(2, player.getAddress().getHostName());
                update.setString(3, player.getUniqueId().toString());
                update.executeUpdate();
                update.close();
            } else {
                PreparedStatement insert = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO users (playerUUID, playerName, firstIP, lastIP, reportNumber, haveKit) VALUES (?,?, ?, ?, ?, ?)");
                insert.setString(1, player.getUniqueId().toString());
                insert.setString(2, player.getName());
                insert.setString(3, player.getAddress().getHostName());
                insert.setString(4, player.getAddress().getHostName());
                insert.setInt(5, 0);
                insert.setString(6, "false");
                insert.executeUpdate();
                insert.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exist(String playerName){
        try {
            PreparedStatement sts = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM users WHERE playerName=?");
            sts.setString(1, playerName);
            ResultSet rs = sts.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UUID getUUID(String playerName){
        try {
            PreparedStatement sts = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT playerUUID FROM users WHERE playerName=?");
            sts.setString(1, playerName);
            ResultSet rs = sts.executeQuery();

            if(rs.next()){
                return UUID.fromString(rs.getString("playerUUID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Le joueur n'a pas d'informations dans la BDD !");
    }

    public String getAddress(String playerName){
        try {
            PreparedStatement sts = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT lastIP FROM users WHERE playerName=?");
            sts.setString(1, playerName);
            ResultSet rs = sts.executeQuery();

            if(rs.next()){
                return rs.getString("lastIP");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Le joueur n'a pas d'informations dans la BDD !");
    }

    public int getReportNumber(String playerName){
        try {
            PreparedStatement sts = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT reportNumber FROM users WHERE playerName=?");
            sts.setString(1, playerName);
            ResultSet rs = sts.executeQuery();

            if(rs.next())
                return rs.getInt("reportNumber");
        } catch (SQLException e) { e.printStackTrace(); }
        throw new NullPointerException("Le joueur n'a pas d'informations dans la BDD !");
    }

    public void incrementReportNumber(String playerName){
        try {
            PreparedStatement update = ValkyaCore.getInstance().sql.connection.prepareStatement("UPDATE users SET reportNumber=? WHERE playerUUID=?");
            update.setInt(1, (getReportNumber(playerName) + 1));
            update.setString(2, Bukkit.getPlayer(playerName).getUniqueId().toString());
            update.executeUpdate();
            update.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void decrementReportNumber(String playerName){
        try {
            PreparedStatement update = ValkyaCore.getInstance().sql.connection.prepareStatement("UPDATE users SET reportNumber=? WHERE playerUUID=?");
            update.setInt(1, (getReportNumber(playerName) - 1));
            update.setString(2, Bukkit.getPlayer(playerName).getUniqueId().toString());
            update.executeUpdate();
            update.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getFirstLoginKit(String playerName){
        try {
            PreparedStatement sts = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT haveKit FROM users WHERE playerName=?");
            sts.setString(1, playerName);
            ResultSet rs = sts.executeQuery();

            if(rs.next())
                return rs.getString("haveKit");
        } catch (SQLException e) { e.printStackTrace(); }
        throw new NullPointerException("Le joueur n'a pas d'informations dans la BDD !");
    }

    public void setFirstLoginKit(String playerName, String dddd){
        try {
            PreparedStatement update = ValkyaCore.getInstance().sql.connection.prepareStatement("UPDATE users SET haveKit=? WHERE playerName=?");
            update.setString(1,  dddd);
            update.setString(2, playerName);
            update.executeUpdate();
            update.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
