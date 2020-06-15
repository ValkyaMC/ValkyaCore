package fr.volax.valkyacore.tools;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.utils.FileManager;

public class ConfigBuilder {
    public static FileManager configs = new FileManager(ValkyaCore.getInstance());

    /**
     * Return un Object dans une config custom
     *
     * @param path La direction du boolean à get
     * @param config Object dans la quel il faut get la value
     * @return Contenue de "value" dans la config
     */
    public static Object getC(String path, ConfigType config) {
        return configs.getConfig(config.getConfigName()).get(path);
    }

    /**
     * Modifier une donnée dans une config custom
     *
     * @param direction La direction de la value à modifier
     * @param data Object à modifier dans la config
     * @param config Config dans la quel il faut modifier la value
     */
    public static void setC(String direction, Object data, ConfigType config) {
        configs.getConfig(config.getConfigName()).set(direction, data);
        configs.getConfig(config.getConfigName()).save();
        configs.getConfig(config.getConfigName()).reload();
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
