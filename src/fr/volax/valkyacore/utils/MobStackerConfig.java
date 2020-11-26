/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.utils;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tools.ConfigType;
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
        stackRadius = ValkyaCore.getInstance().getConfigBuilder().getInt("StackRadius", ConfigType.MOBSTACKER.getConfigName());
        compileEntityTypesList(ValkyaCore.getInstance().getConfigBuilder().getListString("MobTypes", ConfigType.MOBSTACKER.getConfigName())); // Load EntityTypes list (mobTypes)
        updateTickDelay = ValkyaCore.getInstance().getConfigBuilder().getInt("UpdateTickDelay", ConfigType.MOBSTACKER.getConfigName());
        maxAllowedInStack = ValkyaCore.getInstance().getConfigBuilder().getInt("MaxAllowedInStack", ConfigType.MOBSTACKER.getConfigName());
        String stackFormat = ValkyaCore.getInstance().getConfigBuilder().getString("StackFormat", ConfigType.MOBSTACKER.getConfigName());
        stackMobsDispalyName = ChatColor.translateAlternateColorCodes('&',stackFormat);
        for(String s : stackFormat.split(" ")){
            if(s.contains("%number%")) break;
            else indexLocation++;
        }

        compileWorldList( ValkyaCore.getInstance().getConfigBuilder().getListString("DisabledWorlds", ConfigType.MOBSTACKER.getConfigName())); // Load Worlds
        stackOnlySpawnerMobs = ValkyaCore.getInstance().getConfigBuilder().getBoolean("MergeOnlySpawnerMobs", ConfigType.MOBSTACKER.getConfigName());
        stackTamedMobs = ValkyaCore.getInstance().getConfigBuilder().getBoolean("MergeTamedMobs", ConfigType.MOBSTACKER.getConfigName());
        stackLeachedMobs = ValkyaCore.getInstance().getConfigBuilder().getBoolean("MergeLeashedMobs", ConfigType.MOBSTACKER.getConfigName());
        killMobStackOnFall = ValkyaCore.getInstance().getConfigBuilder().getBoolean("killMobStackOnFall", ConfigType.MOBSTACKER.getConfigName());
        compileRegionList( ValkyaCore.getInstance().getConfigBuilder().getListString("WorldGuardRegions", ConfigType.MOBSTACKER.getConfigName())); // Load EntityTypes list (mobTypes)
    }

    private static void compileEntityTypesList(List<String> list) {
        if (list == null || list.size() == 0) return;

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
        if (list == null || list.size() == 0) return;

        for (String worldName : list) {
            World world = Bukkit.getWorld(worldName);
            if(world == null){
                System.out.println("Valkya Mob Stacker");
                System.out.println("Monde invalide: "+ worldName);
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
        disabledRegions.addAll(list);
    }
}
