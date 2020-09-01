/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.commands.ReportLogCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {
    public static void registers(ValkyaCore instance) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new ChatEvent(), instance);
        pm.registerEvents(new GlobalEvents(), instance);
        pm.registerEvents(new PvPPlayerEvent(), instance);

        pm.registerEvents(new BlockListener(), instance);
        pm.registerEvents(new PlayerListener(), instance);
        pm.registerEvents(new BanItemListener(), instance);
        pm.registerEvents(new ModerationListener(), instance);

        pm.registerEvents(new PortalPlayerInteract(), instance);
        pm.registerEvents(new XPListener(), instance);

        pm.registerEvents(new GlobalPlayerJoin(), instance);
        pm.registerEvents(new GlobalPlayerQuit(), instance);

        pm.registerEvents(new HeadOnDeath(), instance);
        pm.registerEvents(new DisableMoreTotems(), instance);
        pm.registerEvents(new InteractEvent(), instance);
        pm.registerEvents(new ReportLogCommand("reportlog"), instance);

        pm.registerEvents(new SpawnersEvent(), instance);
    }
}
