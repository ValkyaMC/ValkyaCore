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
    public static String getCString(String value, String config) {
        return configs.getConfig(config).get().getString(value).replaceAll("&", "§");
    }

    /**
     * Return un int dans une config custom
     *
     * @param value  La direction du int à get
     * @param config Config dans la quel il faut get la value
     * @return Contenue de "value" dans la config
     */
    public static int getCInt(String value, String config) {
        return configs.getConfig(config).get().getInt(value);
    }

    /**
     * Return un Boolean dans une config custom
     *
     * @param value  La direction du boolean à get
     * @param config Config dans la quel il faut get la value
     * @return Contenue de "value" dans la config
     */
    public static Boolean getCBool(String value, String config) {
        return configs.getConfig(config).get().getBoolean(value);
    }

    /**
     * Modifier une donnée dans une config custom
     *
     * @param value  La direction de la value à modifier
     * @param data   String à modifier dans la config
     * @param config Config dans la quel il faut modifier la value
     */
    public static void setCString(String value, String data, String config) {
        configs.getConfig(config).set(value, data);
        configs.getConfig(config).save();
    }

    /**
     * Modifier une donnée dans une config custom
     *
     * @param value  La direction de la value à modifier
     * @param data   int à modifier dans la config
     * @param config Config dans la quel il faut modifier la value
     */
    public static void setCInt(String value, int data, String config) {
        configs.getConfig(config).set(value, data);
        configs.getConfig(config).save();
        configs.getConfig(config).reload();
    }

    /**
     * Modifier une donnée dans une config custom
     *
     * @param value  La direction de la value à modifier
     * @param data   Boolean à modifier dans la config
     * @param config Config dans la quel il faut modifier la value
     */
    public static void setCBool(String value, boolean data, String config) {
        configs.getConfig(config).set(value, data);
        configs.getConfig(config).save();
    }

    /**
     * Return un String dans la config principal
     *
     * @param value La direction du string à get
     * @return Contenue de "value" dans la config
     */
    public static String getString(String value) {
        return ValkyaCore.getInstance().getConfig().getString(value).replaceAll("&", "§");
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
