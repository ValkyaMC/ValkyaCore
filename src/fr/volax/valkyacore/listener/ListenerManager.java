package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.chatgames.NumberGame;
import fr.volax.valkyacore.commands.ReportLogCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {
    public static void registers(ValkyaCore instance) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new DatabasePlayerJoin(), instance);
        pm.registerEvents(new AsyncPlayerChatAntiSpam(), instance);
        pm.registerEvents(new AntiInsulteAsyncPlayerChat(), instance);
        pm.registerEvents(new PortalPlayerInteract(), instance);
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
        pm.registerEvents(new OnTimeChange(), instance);
        pm.registerEvents(new NumberGame(), instance);
        pm.registerEvents(new AsyncPlayerChatManager(), instance);
        pm.registerEvents(new PvPPlayerEvent(), instance);
        pm.registerEvents(new DisableMoreTotems(), instance);
        pm.registerEvents(new BlockListener(), instance);
        pm.registerEvents(new PlayerListener(), instance);
        pm.registerEvents(new BanItemListener(), instance);
        pm.registerEvents(new ModerationListener(), instance);
        pm.registerEvents(new ReportLogCommand("reportlog"), instance);
    }
}
