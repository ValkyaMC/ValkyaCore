/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.ValkyaUtils;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PvPPlayerEvent implements Listener {
    @EventHandler
    public void onFight(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;

        if(!ConfigBuilder.getBoolean("anti-deco-combat.activated")) return;
        Player damager = (Player) event.getDamager();
        Player damaged = (Player) event.getEntity();

        for(String regions : (List<String>)ConfigBuilder.getList("anti-deco-combat.disabled-region")){
            if(isInRegion(damaged, regions) || isInRegion(damager, regions)) return;
        }
        if(ValkyaCore.getInstance().getStaffMod().isInStaffMode(damaged) || ValkyaCore.getInstance().getStaffMod().isInStaffMode(damager)) return;

        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(damaged)){
            ValkyaCore.getInstance().getPvPPlayerManager().resetTime(damaged);
        }else{
            ValkyaCore.getInstance().getPvPPlayerManager().newPvPPlayer(damaged);
            ValkyaUtils.sendChat(damaged,"§eVous êtes maintenant en combat !");
        }

        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(damager)){
            ValkyaCore.getInstance().getPvPPlayerManager().resetTime(damager);
        }else{
            ValkyaCore.getInstance().getPvPPlayerManager().newPvPPlayer(damager);
            ValkyaUtils.sendChat(damager, "§eVous êtes maintenant en combat !");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        if(!ConfigBuilder.getBoolean("anti-deco-combat.activated")) return;
        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(event.getPlayer())){
            event.getPlayer().setHealth(0);
            ValkyaCore.getInstance().getPvPPlayerManager().remove(ValkyaCore.getInstance().getPvPPlayerManager().getPvPPlayer(event.getPlayer()));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(!ConfigBuilder.getBoolean("anti-deco-combat.activated")) return;
        if(ValkyaCore.getInstance().getPvPPlayerManager().doesPlayerExist(event.getEntity())){
            ValkyaCore.getInstance().getPvPPlayerManager().remove(ValkyaCore.getInstance().getPvPPlayerManager().getPvPPlayer(event.getEntity()));
            ValkyaUtils.sendChat(event.getEntity(), "§eVous n'êtes plus en combat !");
        }
    }

    protected boolean isInRegion(Player player, String region) {
        com.sk89q.worldedit.Vector v = new com.sk89q.worldedit.Vector(player.getLocation().getX(), player.getLocation().getBlockY(), player.getLocation().getZ());
        return WorldGuardPlugin.inst().getRegionManager(player.getLocation().getWorld()).getApplicableRegionsIDs(v).contains(region);
    }
}
