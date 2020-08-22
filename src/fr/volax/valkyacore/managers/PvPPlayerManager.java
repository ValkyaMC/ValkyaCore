/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.managers;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PvPPlayerManager {
    private List<PvPPlayer> players;

    public PvPPlayerManager() {
        players = Collections.synchronizedList(new ArrayList<>());
    }

    public boolean doesPlayerExist(Player player){
        if(getPvPPlayer(player.getUniqueId()) == null){
            return false;
        }
        return true;
    }

    public synchronized PvPPlayer newPvPPlayer(Player player){
        PvPPlayer newPlayer = new PvPPlayer(player);
        getPlayersList().add(newPlayer);
        return newPlayer;
    }

    public synchronized List<PvPPlayer> getPlayersList(){
        return players;
    }

    public PvPPlayer getPvPPlayer(Player player){
        return getPvPPlayer(player.getUniqueId());
    }


    public PvPPlayer getPvPPlayer(UUID uuid){
        for(PvPPlayer pvPPlayer : getPlayersList()){
            if(pvPPlayer.getUuid().equals(uuid)) {
                return pvPPlayer;
            }
        }
        return null;
    }

    public void remove(PvPPlayer uhcPlayer){
        getPlayersList().remove(uhcPlayer);
    }

    public void resetTime(PvPPlayer pvPPlayer){
        pvPPlayer.setTimeToPvP(15);
    }

    public void resetTime(Player player){
        getPvPPlayer(player).setTimeToPvP(15);
    }
}
