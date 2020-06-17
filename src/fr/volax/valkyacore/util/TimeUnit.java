package fr.volax.valkyacore.util;

import java.util.HashMap;

/**
 * Class pour initialiser le système de durée pour le "sanction system"
 */
public enum TimeUnit {
    SECONDE("seconde(s)" , "sec", 1),
    MINUTE("minute(s)" , "min", 60),
    HEURE("heure(s)" , "h", 60 * 60),
    JOUR("jour(s)" , "j", 60 * 60 * 24),
    MOIS("mois" , "m", 60 * 60 * 24 * 30);

    private String name;
    private String shortcut;
    private long toSecond;

    private static HashMap<String, TimeUnit> ID_SHORTCUT = new HashMap<>();

    TimeUnit(String name, String shortcut, long toSecond){
        this.name = name;
        this.shortcut = shortcut;
        this.toSecond = toSecond;
    }

    static {
        for (TimeUnit units : values()){
            ID_SHORTCUT.put(units.shortcut, units);
        }
    }

    /**
     * Pour obtenir toute les informations d'une unité de temps à partir d'un shortcut
     * @param shortcut String d'un shortcut parmis les enums de temps
     * @return Toutes les informatinos d'une unité de temps
     */
    public static TimeUnit getFromShortcut(String shortcut){
        return ID_SHORTCUT.get(shortcut);
    }

    /**
     * Pour obtenir le nom d'une unité de temps
     * @return Nom d'une unité de temps
     */
    public String getName() {
        return name;
    }

    /**
     * Pour obtenir le shortcut d'une unité de temps
     * @return Shortcut d'une unité de temps
     */
    public String getShortcut() {
        return shortcut;
    }

    /**
     * Pour obtenir le nombre de secondes d'une unité de temps
     * @return Unité de temps en secondes
     */
    public long getToSecond() {
        return toSecond;
    }

    /**
     * Pour vérifier si un shortcut existe
     * @param shortcut shortcut d'une unité de temps
     * @return Contient ou non le shortcut
     */
    public static boolean existFromShortcut(String shortcut){
        return ID_SHORTCUT.containsKey(shortcut);
    }
}
