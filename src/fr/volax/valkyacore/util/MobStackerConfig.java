/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.util;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MobStackerConfig {
    public static int stackRadius = 1;
    public static Set<EntityType> mobsToStack = new HashSet<EntityType>();
    public static int updateTickDelay = 20;
    public static Set<World> disabledWorlds = new HashSet<>();
    public static Set<String> disabledRegions = new HashSet<>();

    public static boolean worldguardEnabled = false;
    public static boolean stackOnlySpawnerMobs = false;
    public static boolean stackTamedMobs = false;
    public static boolean stackLeachedMobs = false;
    public static boolean killMobStackOnFall = true;
    public static int maxAllowedInStack = 500;

    public static String stackMobsDispalyName = "§6%number% §eMobs";
    public static int indexLocation = 0;



    public static void reloadConfig() {
        stackRadius = ConfigBuilder.getCInt("StackRadius", ConfigType.MOBSTACKER.getConfigName());
        compileEntityTypesList((List<String>) ConfigBuilder.getCList("MobTypes", ConfigType.MOBSTACKER.getConfigName())); // Load EntityTypes list (mobTypes)
        updateTickDelay = ConfigBuilder.getCInt("UpdateTickDelay", ConfigType.MOBSTACKER.getConfigName());
        maxAllowedInStack = ConfigBuilder.getCInt("MaxAllowedInStack", ConfigType.MOBSTACKER.getConfigName());
        String stackFormat = ConfigBuilder.getCString("StackFormat", ConfigType.MOBSTACKER.getConfigName());
        stackMobsDispalyName = ChatColor.translateAlternateColorCodes('&',stackFormat);
        for(String s : stackFormat.split(" ")){
            if(s.contains("%number%")) break;
            else indexLocation++;
        }

        compileWorldList((List<String>) ConfigBuilder.getCList("DisabledWorlds", ConfigType.MOBSTACKER.getConfigName())); // Load Worlds
        stackOnlySpawnerMobs = ConfigBuilder.getCBool("MergeOnlySpawnerMobs", ConfigType.MOBSTACKER.getConfigName());
        stackTamedMobs = ConfigBuilder.getCBool("MergeTamedMobs", ConfigType.MOBSTACKER.getConfigName());
        stackLeachedMobs = ConfigBuilder.getCBool("MergeLeashedMobs", ConfigType.MOBSTACKER.getConfigName());
        killMobStackOnFall = ConfigBuilder.getCBool("killMobStackOnFall", ConfigType.MOBSTACKER.getConfigName());
        compileRegionList((List<String>) ConfigBuilder.getCList("WorldGuardRegions", ConfigType.MOBSTACKER.getConfigName())); // Load EntityTypes list (mobTypes)


    }

    /*
     * Helping Methods
     */
    private static void compileEntityTypesList(List<String> list) {
        if (list == null || list.size() == 0) return; // List may be nothing

        for (String entityName : list) {
            try {
                EntityType entityType = EntityType.valueOf(entityName.toUpperCase());
                mobsToStack.add(entityType);
            } catch (IllegalArgumentException ex) {
                System.out.println("Valkya Mob Stacker");
                System.out.println("Mob invalide: "+ entityName);
            }
        }
    }

    private static void compileWorldList(List<String> list) {
        if (list == null || list.size() == 0) return; // List may be nothing

        for (String worldName : list) {
            World world = Bukkit.getWorld(worldName);
            if(world == null){
                System.out.println("Valkya Mob Stacker");
                System.out.println("Monde invalide: "+ worldName);
                continue;
            }else{
                disabledWorlds.add(world);
            }
        }
    }

    private static void compileRegionList(List<String> list) {
        if (list == null || list.size() == 0){
            worldguardEnabled = false;
            return;
        }
        worldguardEnabled = true;
        for (String regionName : list) {
            disabledRegions.add(regionName);
        }
    }
}
