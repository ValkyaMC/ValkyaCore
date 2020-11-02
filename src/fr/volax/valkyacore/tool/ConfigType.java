/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.tool;

import fr.volax.valkyacore.ValkyaCore;

public enum ConfigType {
    MESSAGES("messages.yml"),
    PORTALS("portals.yml"),
    COOLDOWNCHAT("cooldownchat.yml"),
    MOBSTACKER("mobstacker.yml"),
    GAMECHAT("gamechat.yml"),
    OBSIDIANBREAKER("obsidianbreaker.yml"),
    BANITEMS("banitems.yml");

    public String configName;

    ConfigType(String configName) {
        this.configName = configName;
        ValkyaCore.getInstance().getConfigBuilder().configs.getConfig(configName).saveDefaultConfig();
    }

    public String getConfigName() {
        return configName;
    }
}
