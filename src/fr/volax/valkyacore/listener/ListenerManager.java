package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {
    public static void registers(ValkyaCore instance) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new DatabasePlayerJoin(), instance);
        pm.registerEvents(new AsyncPlayerChatAntiSpam(), instance);
        pm.registerEvents(new AntiSpamPlayerQuit(), instance);
        pm.registerEvents(new AntiInsulteAsyncPlayerChat(), instance);
        pm.registerEvents(new PortalPlayerInteract(), instance);
        pm.registerEvents(new SanctionPlayerLogin(), instance);
        pm.registerEvents(new SanctionPlayerChat(), instance);
        pm.registerEvents(new XPBottleClic(), instance);
        pm.registerEvents(new XPNoDeath(), instance);
        pm.registerEvents(new GlobalPlayerJoin(), instance);
        pm.registerEvents(new GlobalPlayerQuit(), instance);
        pm.registerEvents(new AntiCommands(), instance);
        pm.registerEvents(new HeadOnDeath(), instance);
        pm.registerEvents(new OnEntityDeath(), instance);
        pm.registerEvents(new OnEntityDamage(), instance);
        pm.registerEvents(new OnEntitySpawn(), instance);
        pm.registerEvents(new OnEntityDespawn(), instance);
        pm.registerEvents(new onTimeChange(), instance);
    }
}
