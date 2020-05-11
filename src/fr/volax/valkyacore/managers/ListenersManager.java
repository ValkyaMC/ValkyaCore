package fr.volax.valkyacore.managers;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenersManager {
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
        //pm.registerEvents(new ModCancels(), instance);
        //pm.registerEvents(new ModItemsInteract(), instance);
        //pm.registerEvents(new ModerationQuit(), instance);
        //pm.registerEvents(new FailleChestInteract(), instance);

        pm.registerEvents(new GuiManager(), instance);
    }
}
