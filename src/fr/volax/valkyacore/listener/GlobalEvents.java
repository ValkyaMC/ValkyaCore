/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.utils.MobStackerConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GlobalEvents implements Listener {
    @EventHandler
    public void onChange(WeatherChangeEvent event){
        if(event.toWeatherState()) event.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            if (MobStackerConfig.mobsToStack.contains(e.getEntityType())) {
                ValkyaCore.getInstance().getEntityStacker().getValidEntity().add(e.getEntity());
            }
        }
    }

    @EventHandler
    public void onDespawnEvent(ItemDespawnEvent e){
        if(e.getEntity() instanceof LivingEntity){
            if (ValkyaCore.getInstance().getEntityStacker().getValidEntity().contains(e.getEntity()))
                ValkyaCore.getInstance().getEntityStacker().getValidEntity().remove(e.getEntity());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof LivingEntity)) {
            return; // Not a living entity.
        }

        LivingEntity entity =  e.getEntity();

        if(entity.getType() == EntityType.SLIME){
            return;
        }


        if (entity.getType() != EntityType.PLAYER) {
            if(ValkyaCore.getInstance().getEntityStacker().getEntitiesToMultiplyOnDeath().contains(entity) || (entity.getKiller() != null && ValkyaCore.getInstance().getEntityStacker().getInstantKillPlayers().contains(entity.getKiller().getName()))){
                ValkyaCore.getInstance().getEntityStacker().getEntitiesToMultiplyOnDeath().remove(entity);
                e.setDroppedExp(e.getDroppedExp() * multiplyDropsReturnExp(entity,e.getDrops()));
                return;

            }
            ValkyaCore.getInstance().getStackEntity().attemptUnstackOne(entity);
        }

        if(MobStackerConfig.stackOnlySpawnerMobs){
            ValkyaCore.getInstance().getEntityStacker().getValidEntity().remove(entity);
        }
    }


    public int multiplyDropsReturnExp(LivingEntity dead, List<ItemStack> drops){
        int amountToMultiply = ValkyaCore.getInstance().getStackEntity().parseAmount(dead.getCustomName());
        if(amountToMultiply <=1) return 1;
        for(ItemStack i : drops){
            ItemStack item = new ItemStack(i);
            item.setAmount(amountToMultiply);
            dead.getWorld().dropItem(dead.getLocation(),item);

        }
        return amountToMultiply;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player) && e.getEntity() instanceof LivingEntity){
            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                if(e.getEntity().getFallDistance() > 15 && e.getFinalDamage() >= 20){
                    if(MobStackerConfig.mobsToStack.contains(e.getEntityType()) && MobStackerConfig.killMobStackOnFall)
                        ValkyaCore.getInstance().getEntityStacker().getEntitiesToMultiplyOnDeath().add((LivingEntity) e.getEntity());
                }
            }
        }
    }
}
