package fr.volax.valkyacore.tools;

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
