package fr.volax.valkyacore.managers;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;

public class CommandsManager {
    public static void registers(ValkyaCore instance) {
        instance.getCommand("staff").setExecutor(new StaffCommand());
        instance.getCommand("getmat").setExecutor(new GetMatCommand());
        instance.getCommand("xpbottle").setExecutor(new XPBottleCommand());
        PluginCommand cmdec = Bukkit.getServer().getPluginCommand("ec");
        if(cmdec.getPlugin().getDescription().getName().equals("CustomEnderChest")){
            cmdec.setExecutor(new EcCommand());
        }
        instance.getCommand("ban").setExecutor(new BanCommand());
        instance.getCommand("unban").setExecutor(new UnBanCommand());
        instance.getCommand("check").setExecutor(new CheckCommand());
        instance.getCommand("kick").setExecutor(new KickCommand());
        instance.getCommand("mute").setExecutor(new MuteCommand());
        instance.getCommand("unmute").setExecutor(new UnMuteCommand());
        //instance.getCommand("mod").setExecutor(new ModCommand());
        instance.getCommand("report").setExecutor(new ReportCommand());
        //instance.getCommand("failles").setExecutor(new FaillesCommand());
    }
}
