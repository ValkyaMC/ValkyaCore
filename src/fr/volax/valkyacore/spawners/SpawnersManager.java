/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.spawners;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpawnersManager {
    public void addSpawner(Spawner spawner){
        PreparedStatement query = null;
        try {
            query = ValkyaCore.getInstance().sql.connection.prepareStatement("INSERT INTO spawners (owner, materialID, state, isDestroy) VALUES (?,?,?,?)");
            query.setString(1, spawner.getOwner());
            query.setInt(2, spawner.getId());
            query.setString(3, spawner.getState().getName());
            query.setBoolean(4, spawner.isDestroy());
            query.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*
     * spawner.setDestroy(false);
     * spawner.setState(SpawnersState.PLACED);
     */
    public void placeBlock(Spawner spawner){
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("UPDATE spawners set(X=?, Y=?, Z=?, worldName=?, owner=?, isDestroy=?, id=?, state=?) WHERE id=?");
            query.setDouble(1, spawner.getX());
            query.setDouble(2, spawner.getY());
            query.setDouble(3, spawner.getZ());
            query.setString(4, spawner.getWorld().getName());
            query.setString(5, spawner.getOwner());
            query.setBoolean(6, spawner.isDestroy());
            query.setInt(7, spawner.getMaterialId());
            query.setString(8, spawner.getState().getName());
            query.setInt(9, spawner.getId());
            query.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void breakBlock(Spawner spawner){
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("UPDATE spawners set(X=?, Y=?, Z=?, worldName=?, owner=?, isDestroy=?, id=?, transit=?) WHERE X=?,Y=?,Z=?,world=?");
            query.setDouble(1, spawner.getX());
            query.setDouble(2, spawner.getY());
            query.setDouble(3, spawner.getZ());
            query.setString(4, spawner.getWorld().getName());
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDestroy(boolean isDestroy, double X, double Y, double Z, World world){
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("UPDATE spawners SET isDestroy=? WHERE (X=?, Y=?, Z=?, worldName=?)");
            query.setBoolean(1, isDestroy);
            query.setDouble(2, X);
            query.setDouble(3, Y);
            query.setDouble(4, Z);
            query.setString(5, world.getName());
            query.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public boolean isDestroy(double X, double Y, double Z, World world){
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT isDestroy FROM spawners WHERE (X=?, Y=?, Z=?, worldName=?)");
            query.setDouble(1, X);
            query.setDouble(2, Y);
            query.setDouble(3, Z);
            query.setString(4, world.getName());
            ResultSet rs = query.executeQuery();

            if(rs.next()) return rs.getBoolean("isDestroy");
        } catch (SQLException e) { e.printStackTrace(); }
        throw new NullPointerException("Le spawner n'a pas d'informations dans la BDD !");
    }

    public boolean exist(double X, double Y, double Z, World world){
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM spawners WHERE (X=?, Y=?, Z=?, worldName=?)");
            query.setDouble(1, X);
            query.setDouble(2, Y);
            query.setDouble(3, Z);
            query.setString(4, world.getName());
            ResultSet rs = query.executeQuery();

            if(rs.next()) return rs.next();
        } catch (SQLException e) { e.printStackTrace(); }
        throw new NullPointerException("Le spawner n'a pas d'informations dans la BDD !");
    }

    public String getOwner(double X, double Y, double Z, World world){
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT owner FROM spawners WHERE (X=?, Y=?, Z=?, worldName=?)");
            query.setDouble(1, X);
            query.setDouble(2, Y);
            query.setDouble(3, Z);
            query.setString(4, world.getName());
            ResultSet rs = query.executeQuery();

            if(rs.next()) return rs.getString("owner");
        } catch (SQLException e) { e.printStackTrace(); }
        throw new NullPointerException("Le spawner n'a pas d'informations dans la BDD !");
    }

    public void setOwner(String owner, double X, double Y, double Z, World world){
        try {
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("UPDATE spawners SET owner=? WHERE (X=?, Y=?, Z=?, worldName=?)");
            query.setString(1, owner);
            query.setDouble(2, X);
            query.setDouble(3, Y);
            query.setDouble(4, Z);
            query.setString(5, world.getName());
            query.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Location> getSpawnersList(String owner){
        try{
            PreparedStatement query = ValkyaCore.getInstance().sql.connection.prepareStatement("SELECT * FROM spawners WHERE owner=?");
            query.setString(1, owner);
            ResultSet rs = query.executeQuery();
            List<Location> locations = new ArrayList<>();
            while(rs.next()){
                Location location = new Location(Bukkit.getWorld(rs.getString("world")), rs.getDouble("X"), rs.getDouble("Y"), rs.getDouble("Z"));
                locations.add(location);
            }
            return locations;
        }catch (SQLException e){ e.printStackTrace();}
        throw new NullPointerException("Le spawner n'a pas d'informations dans la BDD !");
    }
}
