package fr.volax.valkyacore;

import fr.volax.valkyacore.managers.InventoriesManager;
import fr.volax.valkyacore.managers.*;
import fr.volax.valkyacore.tools.ConfigBuilder;
import fr.volax.valkyacore.tools.GuiBuilder;
import fr.volax.valkyacore.utils.Database;
import fr.volax.valkyacore.utils.PlayerUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ValkyaCore for Valkya
 *
 * @author Volax
 */
public class ValkyaCore extends JavaPlugin {
    private static ValkyaCore instance;
    private BanManager banManager;
    private MuteManager muteManager;
    private PlayerUtils playerUtils;
    private InventoriesManager inventoriesManager;
    public Database sql;
    private GuiManager guiManager;

    private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;
    public Map<Inventory, UUID> admin;
    public HashMap<UUID, Long> cooldown;
    public ArrayList<UUID> staff;
    public HashMap<UUID, PlayerManager> players;

    public static String PREFIX, LOGGER_PREFIX, pluginName;

    @Override
    public void onEnable() {
        PREFIX = "§6Valkya »";
        LOGGER_PREFIX = "[Valkya-Logger]";
        pluginName = this.getName();

        //********************************************
        // Load des Evénements et des Commandes
        //********************************************
        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dEnregistrement des events et commandes...");
        ListenersManager.registers(this);
        CommandsManager.registers(this);


        //********************************************
        // Setup des instances
        //********************************************
        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dSetup des instances et des HashMaps/ArrayLists...");
        instance = this;
        banManager = new BanManager();
        muteManager = new MuteManager();
        playerUtils = new PlayerUtils();
        inventoriesManager = new InventoriesManager();
        guiManager = new GuiManager();

        registeredMenus = new HashMap<>();
        cooldown = new HashMap<>();
        staff = new ArrayList<>();
        players = new HashMap<>();
        admin = new HashMap<>();
        //********************************************
        // Sauvegarde config.yml
        //********************************************
        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dSauvegarde et enregistrements des configs...");
        this.saveDefaultConfig();


        //********************************************
        // Sauvegarde des config custom
        //********************************************
        ConfigBuilder.configs.getConfig("messages.yml").saveDefaultConfig();
        ConfigBuilder.configs.getConfig("portals.yml").saveDefaultConfig();
        ConfigBuilder.configs.getConfig("failles.yml").saveDefaultConfig();


        //********************************************
        // Setup & connexion du mysql
        //********************************************
        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dSetup et connexion au mysql...");
        sql = new Database("jdbc:mysql://", ConfigBuilder.getString("sql.host"), ConfigBuilder.getString("sql.database"), ConfigBuilder.getString("sql.user"), ConfigBuilder.getString("sql.pass"));
        sql.connection();


        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dChargement des menus...");
        //guiManager.addMenu(new MenuStaffInventory());

        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dChargement du " + pluginName + " fini !");
    }

    @Override
    public void onDisable() {
        sql.disconnect();
    }

    /**
     * @param value Le String à vérifier
     * @return Es-ce que value est un int
     */
    public boolean isInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param x,y,z Coordonnées d'un coffre
     * @return Es-ce que le coffre existe ?
     */
    public boolean isNull(int x, int y, int z) {
        return x == 0 && y == 0 && z == 0;
    }

    public static ValkyaCore getInstance() {
        return instance;
    }

    public BanManager getBanManager() {
        return banManager;
    }

    public MuteManager getMuteManager() {
        return muteManager;
    }

    public PlayerUtils getPlayerUtils() {
        return playerUtils;
    }

    public InventoriesManager getInventoriesManager() {
        return inventoriesManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }
}
