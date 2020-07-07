package fr.volax.valkyacore.util;

import fr.volax.valkyacore.tool.ConfigBuilder;
import org.bukkit.Statistic;
import org.bukkit.permissions.Permission;

public class PermissionsHelper {
    public Permission getMatUse = new Permission(ConfigBuilder.getString("permissions.getmat-use"));

    public Permission banUse = new Permission(ConfigBuilder.getString("permissions.ban-use"));
    public Permission banPermUse = new Permission(ConfigBuilder.getString("permissions.ban-perm-use"));
    public Permission banBypass = new Permission(ConfigBuilder.getString("permissions.ban-bypass"));

    public Permission unbanUse = new Permission(ConfigBuilder.getString("permissions.unban-use"));

    public Permission checkUse = new Permission(ConfigBuilder.getString("permissions.check-use"));

    public Permission kickUse = new Permission(ConfigBuilder.getString("permissions.kick-use"));
    public Permission kickBypass = new Permission(ConfigBuilder.getString("permissions.kick-bypass"));


    public Permission muteUse = new Permission(ConfigBuilder.getString("permissions.mute-use"));
    public Permission mutePermUse = new Permission(ConfigBuilder.getString("permissions.mute-perm-use"));
    public Permission muteBypass = new Permission(ConfigBuilder.getString("permissions.mute-bypass"));

    public Permission unmuteUse = new Permission(ConfigBuilder.getString("permissions.unmute-use"));

    public Permission xpBottleUse = new Permission(ConfigBuilder.getString("permissions.xpbottle-use"));
    public Permission xpBottleGive = new Permission(ConfigBuilder.getString("permissions.xpbottle-give"));

    public Permission staffUse = new Permission(ConfigBuilder.getString("permissions.staff-use"));

    public Permission cooldownChatModif = new Permission(ConfigBuilder.getString("permissions.cooldownchat-modif"));
    public Permission cooldownChatBypass = new Permission(ConfigBuilder.getString("permissions.cooldownchat-bypass"));

    public Permission xpDeath = new Permission(ConfigBuilder.getString("permissions.xp-death"));

    public Permission reportUse = new Permission(ConfigBuilder.getString("permissions.report-use"));
    public Permission reportReceive = new Permission(ConfigBuilder.getString("permissions.report-receive"));

    public Permission moderationChat = new Permission(ConfigBuilder.getString("permissions.moderation-chat"));

    public Permission gamemodeChange = new Permission(ConfigBuilder.getString("permissions.gamemode-change"));
    public Permission gamemodeChangeOther = new Permission(ConfigBuilder.getString("permissions.gamemode-change-other"));
    public Permission gamemodeNotify = new Permission(ConfigBuilder.getString("permissions.gamemode-notify"));
    
    public Permission kdrUse = new Permission(ConfigBuilder.getString("permissions.kdr-use"));

    public Permission repairHand = new Permission(ConfigBuilder.getString("permissions.repair-hand"));
    public Permission repairAll = new Permission(ConfigBuilder.getString("permissions.repair-all"));
    public Permission repairBypassCooldown = new Permission(ConfigBuilder.getString("permissions.repair-bypass-cooldown"));
}
