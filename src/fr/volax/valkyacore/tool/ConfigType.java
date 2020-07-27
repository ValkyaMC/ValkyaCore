package fr.volax.valkyacore.tool;

import fr.volax.volaxapi.tool.config.ConfigBuilder;

public enum ConfigType {
    MESSAGES("messages.yml"),
    PORTALS("portals.yml"),
    COOLDOWNCHAT("cooldownchat.yml"),
    MOBSTACKER("mobstacker.yml"),
    GAMECHAT("gamechat.yml");

    public String configName;

    ConfigType(String configName) {
        this.configName = configName;
        ConfigBuilder.configs.getConfig(configName).saveDefaultConfig();
    }

    public String getConfigName() {
        return configName;
    }
}
