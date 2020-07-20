package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.MobStackerConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnEntityDamage implements Listener {
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
