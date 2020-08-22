/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.managers;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.MobStackerConfig;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;

import java.util.ArrayList;
import java.util.Set;

public class EntityStackerManager {
    private int mobStackRadius;
    private Set<EntityType> entitiesToStack;
    private ArrayList<LivingEntity> validEnity = new ArrayList<>();
    private ArrayList<LivingEntity> entitiesToMultiplyOnDeath = new ArrayList<>();

    private ArrayList<String> instantKillPlayers = new ArrayList<>();

    public EntityStackerManager(int mobStackRadius, Set<EntityType> entitiesToStack) {
        this.mobStackRadius = mobStackRadius;
        this.entitiesToStack = entitiesToStack;
        startEntityClock();

    }

    private void startEntityClock(){
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ValkyaCore.getInstance(), new Runnable() {
            public void run() {
                // Iterate through all worlds
                for (World world : Bukkit.getServer().getWorlds()) {
                    // Iterate through all entities in this world (if not disabled)
                    if(MobStackerConfig.disabledWorlds.contains(world)) continue;
                    for (LivingEntity entity : world.getLivingEntities()) {
                        if(!checkEntity(entity)) continue;
                        // Iterate through all entities in range
                        for (Entity nearby : entity.getNearbyEntities(mobStackRadius, mobStackRadius, mobStackRadius)) {

                            if(checkEntity(nearby)) {
                                ValkyaCore.getInstance().getStackEntity().stack(entity, (LivingEntity) nearby);
                            }
                        }
                    }
                }

            }
        }, 20L, MobStackerConfig.updateTickDelay);

    }


    public ArrayList<LivingEntity> getEntitiesToMultiplyOnDeath() {
        return entitiesToMultiplyOnDeath;
    }

    private boolean checkEntity(Entity entity){
        if(!(entity instanceof LivingEntity)){
            return false;
        }
        if(!entity.isValid()){
            return false;
        }
        if (entity.getType() == EntityType.PLAYER) {
            return false;
        }
        if(!entitiesToStack.contains(entity.getType())){
            return false;
        }
        if(MobStackerConfig.worldguardEnabled) {
            ApplicableRegionSet region = WGBukkit.getRegionManager(entity.getWorld()).getApplicableRegions(entity.getLocation());
            for(ProtectedRegion r : region.getRegions()){
                for(String s : MobStackerConfig.disabledRegions){
                    if(r.getId().equalsIgnoreCase(s)){
                        return false;
                    }
                }
            }
        }
        if(((LivingEntity) entity).isLeashed() && !MobStackerConfig.stackLeachedMobs){
            return false;
        }
        if(entity instanceof Tameable){
            if(!MobStackerConfig.stackTamedMobs){
                return false;
            }
        }
        if(MobStackerConfig.stackOnlySpawnerMobs){
            if (!validEnity.contains((LivingEntity) entity)){
                return false;
            }
        }
        if(entity.getType() == EntityType.SLIME){
            return false;
        }
        return true;
    }

    public ArrayList<LivingEntity> getValidEntity() {
        return this.validEnity;
    }

    public ArrayList<String> getInstantKillPlayers() {
        return instantKillPlayers;
    }
}
