/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import org.bukkit.Bukkit;

public class CommandManager {
    public static void registers() {
        new GetMatCommand("getmat");
        new XPBottleCommand("xpbottle");
        new BanCommand("ban");
        new UnBanCommand("unban");
        new CheckCommand("check");
        new KickCommand("kick");
        new MuteCommand("mute");
        new UnMuteCommand("unmute");
        new ReportCommand("report");
        new GamemodeCommand(new String[]{"gamemode", "gma", "gmc", "gms"});
        new KDCommand("kd");
        new RepairCommand("repair");
        new BroadcastCommand("broadcast");
        new RTPCommand("rtp");
        new ChatCommand("chat");
        new CombatCommand("combat");
        new KickAllCommand("kickall");
        new CheckerCommand("checker");
        new ValkyaConfigReloadCommand("valkyaconfigreload");
        new StaffModCommand("staffmod");
        new StaffModListCommand("staffmodlist");
        new ReportLogCommand("reportlog");

        if(Bukkit.getServer().getPluginCommand("ec").getPlugin().getDescription().getName().equals("CustomEnderChest")) Bukkit.getServer().getPluginCommand("ec").setExecutor(new EcCommand("ec"));
    }
}
