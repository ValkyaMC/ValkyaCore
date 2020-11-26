/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.utils;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.permissions.Permission;

public class PermissionsHelper {
    public Permission getMatUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.getmat-use"));
    public Permission banUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.ban-use"));
    public Permission banPermUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.ban-perm-use"));
    public Permission banBypass = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.ban-bypass"));
    public Permission unbanUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.unban-use"));
    public Permission checkUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.check-use"));
    public Permission kickUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.kick-use"));
    public Permission kickBypass = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.kick-bypass"));
    public Permission muteUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.mute-use"));
    public Permission mutePermUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.mute-perm-use"));
    public Permission muteBypass = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.mute-bypass"));
    public Permission unmuteUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.unmute-use"));
    public Permission xpBottleUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.xpbottle-use"));
    public Permission xpBottleGive = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.xpbottle-give"));
    public Permission cooldownChatBypass = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.cooldownchat-bypass"));
    public Permission xpDeath = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.xp-death"));
    public Permission reportUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.report-use"));
    public Permission reportReceive = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.report-receive"));
    public Permission gamemodeChange = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.gamemode-change"));
    public Permission gamemodeChangeOther = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.gamemode-change-other"));
    public Permission gamemodeNotify = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.gamemode-notify"));
    public Permission repairHand = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.repair-hand"));
    public Permission repairAll = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.repair-all"));
    public Permission repairBypassCooldown = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.repair-bypass-cooldown"));
    public Permission broadcastUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.broadcast-use"));
    public Permission chatOffBypass = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.chat-off-bypass"));
    public Permission chatChangeSet = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.chat-changeset-use"));
    public Permission chatClear = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.chat-clear"));
    public Permission kickAllUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.kickall-use"));
    public Permission kickAllBypass = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.kickall-bypass"));
    public Permission valkyaConfigReloadUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.valkyaconfigreload-use"));
    public Permission staffmodeUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.staffmode-use"));
    public Permission staffmodeListUse = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.staffmodlist-use"));
    public Permission spawnersAdd = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.spawners-add"));
    public Permission spawnersRemove = new Permission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.spawners-remove"));
}

