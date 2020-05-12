package fr.volax.valkyacore.tools;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.FileManager;

public class ConfigBuilder {
    public static FileManager configs = new FileManager(ValkyaCore.getInstance());

    /**
     * Return un String dans une config custom
     *
     * @param value  La direction du string à get
     * @param config Config dans la quel il faut get la value
     * @return Contenue de "value" dans la config
     */
    public static String getCString(String value, ConfigType config) {
        return configs.getConfig(config.getConfigName()).get().getString(value).replaceAll("&", "§").replaceAll("%prefix%", ValkyaCore.PREFIX).replaceAll("%logger_prefix%", ValkyaCore.LOGGER_PREFIX);
    }

    /**
     * Return un int dans une config custom
     *
     * @param value  La direction du int à get
     * @param config Config dans la quel il faut get la value
     * @return Contenue de "value" dans la config
     */
    public static int getCInt(String value, ConfigType config) {
        return configs.getConfig(config.getConfigName()).get().getInt(value);
    }

    /**
     * Return un Boolean dans une config custom
     *
     * @param value  La direction du boolean à get
     * @param config Config dans la quel il faut get la value
     * @return Contenue de "value" dans la config
     */
    public static Boolean getCBool(String value, ConfigType config) {
        return configs.getConfig(config.getConfigName()).get().getBoolean(value);
    }

    /**
     * Modifier une donnée dans une config custom
     *
     * @param value  La direction de la value à modifier
     * @param data   String à modifier dans la config
     * @param config Config dans la quel il faut modifier la value
     */
    public static void setCString(String value, String data, ConfigType config) {
        configs.getConfig(config.getConfigName()).set(value, data);
        configs.getConfig(config.getConfigName()).save();
    }

    /**
     * Modifier une donnée dans une config custom
     *
     * @param value  La direction de la value à modifier
     * @param data   int à modifier dans la config
     * @param config Config dans la quel il faut modifier la value
     */
    public static void setCInt(String value, int data, ConfigType config) {
        configs.getConfig(config.getConfigName()).set(value, data);
        configs.getConfig(config.getConfigName()).save();
        configs.getConfig(config.getConfigName()).reload();
    }

    /**
     * Modifier une donnée dans une config custom
     *
     * @param value  La direction de la value à modifier
     * @param data   Boolean à modifier dans la config
     * @param config Config dans la quel il faut modifier la value
     */
    public static void setCBool(String value, boolean data, ConfigType config) {
        configs.getConfig(config.getConfigName()).set(value, data);
        configs.getConfig(config.getConfigName()).save();
    }

    /**
     * Return un String dans la config principal
     *
     * @param value La direction du string à get
     * @return Contenue de "value" dans la config
     */
    public static String getString(String value) {
        return ValkyaCore.getInstance().getConfig().getString(value).replaceAll("&", "§").replaceAll("%prefix%", ValkyaCore.PREFIX).replaceAll("%logger_prefix%", ValkyaCore.LOGGER_PREFIX);
    }

    /**
     * Return un int dans la config principal
     *
     * @param value La direction du int à get
     * @return Contenue de "value" dans la config
     */
    public static int getInt(String value) {
        return ValkyaCore.getInstance().getConfig().getInt(value);
    }

    /**
     * Return un Boolean dans la config principal
     *
     * @param value La direction du boolean à get
     * @return Contenue de "value" dans la config
     */
    public static boolean getBoolean(String value) {
        return ValkyaCore.getInstance().getConfig().getBoolean(value);
    }

    /**
     * Modifier une donnée dans la config principale
     *
     * @param value La direction de la donnée à modifier
     * @param data  Donnée à modifier dans la config
     */
    public static void set(String value, String data) {
        ValkyaCore.getInstance().getConfig().set(value, data);
        ValkyaCore.getInstance().saveConfig();
        ValkyaCore.getInstance().reloadConfig();
    }
}
