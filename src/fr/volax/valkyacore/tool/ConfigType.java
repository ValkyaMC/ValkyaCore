package fr.volax.valkyacore.tool;

public enum ConfigType {
    MESSAGES("messages.yml"),
    PORTALS("portals.yml"),
    COOLDOWNCHAT("cooldownchat.yml");

    public String configName;

    ConfigType(String configName) {
        this.configName = configName;
    }

    public String getConfigName() {
        return configName;
    }
}
