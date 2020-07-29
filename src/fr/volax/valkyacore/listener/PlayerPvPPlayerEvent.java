package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerPvPPlayerEvent implements Listener {
    @EventHandler
    public void onFight(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;

        Player damager = (Player) event.getDamager();
        Player damaged = (Player) event.getEntity();

        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(damaged)){
            ValkyaCore.getInstance().getPvPPlayerManager().resetTime(damaged);
        }else{
            ValkyaCore.getInstance().getPvPPlayerManager().newPvPPlayer(damaged);
            damaged.sendMessage(ValkyaCore.PREFIX + " §eVous êtes maintenant en combat !");
        }

        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(damager)){
            ValkyaCore.getInstance().getPvPPlayerManager().resetTime(damager);
        }else{
            ValkyaCore.getInstance().getPvPPlayerManager().newPvPPlayer(damager);
            damager.sendMessage(ValkyaCore.PREFIX + " §eVous êtes maintenant en combat !");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(event.getPlayer())){
            event.getPlayer().setHealth(0);
            ValkyaCore.getInstance().getPvPPlayerManager().remove(ValkyaCore.getInstance().getPvPPlayerManager().getPvPPlayer(event.getPlayer()));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(event.getEntity())){
            ValkyaCore.getInstance().getPvPPlayerManager().remove(ValkyaCore.getInstance().getPvPPlayerManager().getPvPPlayer(event.getEntity()));
            event.getEntity().sendMessage(ValkyaCore.PREFIX + " §eVous n'êtes plus en combat !");
        }
    }
}
