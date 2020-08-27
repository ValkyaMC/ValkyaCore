/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.util;

import fr.volax.volaxapi.tool.config.ConfigBuilder;
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

    public Permission cooldownChatBypass = new Permission(ConfigBuilder.getString("permissions.cooldownchat-bypass"));

    public Permission xpDeath = new Permission(ConfigBuilder.getString("permissions.xp-death"));

    public Permission reportUse = new Permission(ConfigBuilder.getString("permissions.report-use"));
    public Permission reportReceive = new Permission(ConfigBuilder.getString("permissions.report-receive"));


    public Permission gamemodeChange = new Permission(ConfigBuilder.getString("permissions.gamemode-change"));
    public Permission gamemodeChangeOther = new Permission(ConfigBuilder.getString("permissions.gamemode-change-other"));
    public Permission gamemodeNotify = new Permission(ConfigBuilder.getString("permissions.gamemode-notify"));

    public Permission repairHand = new Permission(ConfigBuilder.getString("permissions.repair-hand"));
    public Permission repairAll = new Permission(ConfigBuilder.getString("permissions.repair-all"));
    public Permission repairBypassCooldown = new Permission(ConfigBuilder.getString("permissions.repair-bypass-cooldown"));

    public Permission broadcastUse = new Permission(ConfigBuilder.getString("permissions.broadcast-use"));

    public Permission chatOffBypass = new Permission(ConfigBuilder.getString("permissions.chat-off-bypass"));
    public Permission chatChangeSet = new Permission(ConfigBuilder.getString("permissions.chat-changeset-use"));
    public Permission chatClear = new Permission(ConfigBuilder.getString("permissions.chat-clear"));

    public Permission kickAllUse = new Permission(ConfigBuilder.getString("permissions.kickall-use"));
    public Permission kickAllBypass = new Permission(ConfigBuilder.getString("permissions.kickall-bypass"));

    public Permission valkyaConfigReloadUse = new Permission(ConfigBuilder.getString("permissions.valkyaconfigreload-use"));

    public Permission staffmodeUse = new Permission(ConfigBuilder.getString("permissions.staffmode-use"));
    public Permission staffmodeListUse = new Permission(ConfigBuilder.getString("permissions.staffmodlist-use"));
    public Permission freezeBypass = new Permission(ConfigBuilder.getString("permissions.freeze-bypass"));
}

