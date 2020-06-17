package fr.volax.valkyacore.util;

import fr.volax.valkyacore.tool.ConfigBuilder;
import fr.volax.valkyacore.tool.ConfigType;
import org.bukkit.Bukkit;

import java.sql.*;

public class Database {
    public Connection connection;
    public String urlbase, host, database, user, pass;

    public Database(String urlbase, String host, String database, String user, String pass){
        this.urlbase = urlbase;
        this.host = host;
        this.database = database;
        this.user = user;
        this.pass = pass;

    }

    /**
     * Fonction qui permet à la connection à la BDD
     */
    public void connection(){
        if(!isConnected()){
            try {
                connection = DriverManager.getConnection(urlbase + host + "/" + database,user,pass);
                Bukkit.getConsoleSender().sendMessage(ConfigBuilder.getCString("messages.logger.connected-bdd", ConfigType.MESSAGES));
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Fonction qui permet à la déconnection de la BDD
     */
    public void disconnect(){
        if(isConnected()){
            try {
                connection.close();
                Bukkit.getConsoleSender().sendMessage(ConfigBuilder.getCString("messages.logger.unconnected-bdd", ConfigType.MESSAGES));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Fonction qui permet de retourner la connection à la bdd
     * @return la connection à la bdd
     */
    public boolean isConnected(){
        return connection != null;
    }
}
