/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.valkyacore.util.ValkyaUtils;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ValkyaConfigReloadCommand implements CommandExecutor {
    public ValkyaConfigReloadCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().valkyaConfigReloadUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.valkyaconfigreload")) return false;
        ValkyaCore.getInstance().reloadConfig();
        ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).reload();
        ConfigBuilder.configs.getConfig(ConfigType.MESSAGES.getConfigName()).reload();
        ConfigBuilder.configs.getConfig(ConfigType.MOBSTACKER.getConfigName()).reload();
        ConfigBuilder.configs.getConfig(ConfigType.PORTALS.getConfigName()).reload();
        ConfigBuilder.configs.getConfig(ConfigType.BANITEMS.getConfigName()).reload();
        ConfigBuilder.configs.getConfig(ConfigType.COOLDOWNCHAT.getConfigName()).reload();
        ConfigBuilder.configs.getConfig(ConfigType.GAMECHAT.getConfigName()).reload();
        ValkyaUtils.sendChat(sender, "§eVous venez de reload toutes les configs du ValkyaCore");
        return false;
    }
}
