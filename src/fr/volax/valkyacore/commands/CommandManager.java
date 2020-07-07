package fr.volax.valkyacore.commands;

import org.bukkit.Bukkit;

public class CommandManager {
    public static void registers() {
        new StaffCommand("staff");
        new GetMatCommand("getmat");
        new XPBottleCommand("xpbottle");
        new BanCommand("ban");
        new UnBanCommand("unban");
        new CheckCommand("check");
        new KickCommand("kick");
        new MuteCommand("mute");
        new UnMuteCommand("unmute");
        new ReportCommand("report");
        new GamemodeCommand("gamemode");
        new KDRCommand("kdr");
        new RepairCommand("repair");

        if(Bukkit.getServer().getPluginCommand("ec").getPlugin().getDescription().getName().equals("CustomEnderChest")) Bukkit.getServer().getPluginCommand("ec").setExecutor(new EcCommand("ec"));
    }
}
