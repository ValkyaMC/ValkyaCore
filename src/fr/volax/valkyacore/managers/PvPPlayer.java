package fr.volax.valkyacore.managers;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PvPPlayer {
    private Player player;
    private UUID uuid;
    private double timeToPvP;
    private int task;

    public PvPPlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.timeToPvP = 15;
        startTimer();
    }

    public int startTimer(){
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(ValkyaCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                timeToPvP = timeToPvP - 0.25;

                if(timeToPvP <= 0){
                    ValkyaCore.getInstance().getPvPPlayerManager().remove(ValkyaCore.getInstance().getPvPPlayerManager().getPvPPlayer(player));
                    player.sendMessage(ValkyaCore.PREFIX + " §eVous n'êtes plus en combat !");
                    Bukkit.getScheduler().cancelTask(task);
                    ValkyaCore.getInstance().getPvPPlayerManager().remove(ValkyaCore.getInstance().getPvPPlayerManager().getPvPPlayer(player));
                }
            }
        },20,5);
        return task;
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getTimeToPvP() {
        return timeToPvP;
    }

    public void setTimeToPvP(int timeToPvP) {
        this.timeToPvP = timeToPvP;
    }
}
