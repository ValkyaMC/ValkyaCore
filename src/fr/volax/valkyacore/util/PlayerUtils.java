package fr.volax.valkyacore.util;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigBuilder;
import fr.volax.valkyacore.tool.ConfigType;
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
            sender.sendMessage(ConfigBuilder.getCString("messages.no-player", ConfigType.MESSAGES));
            return false;
        }
        return true;
    }

    public boolean isOnlinePlayer(CommandSender sender, String target){
        if(!doesPlayerExist(sender, target)) return false;
        if(Bukkit.getPlayer(target) == null){
            sender.sendMessage(ConfigBuilder.getCString("messages.not-online", ConfigType.MESSAGES));
            return false;
        }
        return true;

    }

    public boolean doesPlayerExist(CommandSender sender, String target){
        if(!exist(target)){
            sender.sendMessage( ConfigBuilder.getCString("messages.never-join", ConfigType.MESSAGES));
            return false;
        }
        return true;
    }

    /**
     * Permet de vérifier si player a la permission
     * @param permission Permission à tester sur le joueur
     * @return false = player n'a pas la permission | true = player a la permission
     */
    public boolean hasPerm(CommandSender sender, Permission permission){
        if(!sender.hasPermission(permission)){
            sender.sendMessage(ConfigBuilder.getCString("messages.no-perm", ConfigType.MESSAGES));
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
                PreparedStatement update = ValkyaCore.getInstance().sql.connection.prepareStatement("UPDATE users SET playerName=? WHERE playerUUID=?");
                update.setString(1, player.getName());
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();
                update.close();

            } else {
                PreparedStatement insert = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO users (playerUUID, playerName) VALUES (?, ?)");
                insert.setString(1, player.getUniqueId().toString());
                insert.setString(2, player.getName());
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

}
