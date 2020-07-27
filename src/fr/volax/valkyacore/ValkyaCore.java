package fr.volax.valkyacore;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.volax.valkyacore.chatgames.NumberGame;
import fr.volax.valkyacore.commands.CommandManager;
import fr.volax.valkyacore.listener.ListenerManager;
import fr.volax.valkyacore.managers.*;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.valkyacore.util.MobStackerConfig;
import fr.volax.valkyacore.util.PermissionsHelper;
import fr.volax.valkyacore.util.PlayerUtils;
import fr.volax.volaxapi.VolaxAPI;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import fr.volax.volaxapi.tool.database.Database;
import fr.volax.volaxapi.tool.gui.GuiManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.*;

/**
 * ValkyaCore for Valkya PvP-Faction Modded
 * @author Volax
 */
public class ValkyaCore extends JavaPlugin {
    private static ValkyaCore instance;
    private BanManager banManager;
    private MuteManager muteManager;
    private PlayerUtils playerUtils;
    private PermissionsHelper permissionsHelper;
    public Database sql;
    public NumberGame numberGame;
    private GuiManager guiManager;
    private EntityStackerManager entityStacker;
    private StackEntity stackEntity;
    public static Economy economy;

    public Map<Inventory, UUID> admin;
    public HashMap<UUID, Long> cooldown;
    public ArrayList<UUID> staff;

    public static String PREFIX, LOGGER_PREFIX, pluginName;
    @Override
    public void onEnable() {
        instance = this;
        VolaxAPI.setInstance(this);

        PREFIX = "§6Valkya »";
        pluginName = this.getName();
        LOGGER_PREFIX = "["+pluginName+"-Logger]";

        if (getServer().getPluginManager().getPlugin("WorldGuard") == null || !(getServer().getPluginManager().getPlugin("WorldGuard") instanceof WorldGuardPlugin)) {
            MobStackerConfig.worldguardEnabled = false;
        }
        //********************************************
        // Sauvegarde config.yml
        //********************************************
        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dSauvegarde et enregistrements des configs...");
        this.saveDefaultConfig();


        //********************************************
        // Setup des instances
        //********************************************
        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dSetup des instances et des HashMaps/ArrayLists...");
        banManager = new BanManager();
        muteManager = new MuteManager();
        playerUtils = new PlayerUtils();
        guiManager = new GuiManager();
        permissionsHelper = new PermissionsHelper();
        MobStackerConfig.reloadConfig();
        entityStacker = new EntityStackerManager(MobStackerConfig.stackRadius, MobStackerConfig.mobsToStack);
        stackEntity = new StackEntity();
        numberGame = new NumberGame();
        cooldown = new HashMap<>();
        staff = new ArrayList<>();
        admin = new HashMap<>();

        if (setupEconomy()) {
            this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dVault §aON...");
        }else{
            this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dVault §cOFF§d, the plugin don't work without Vault try to fix him...");
        }
        //********************************************
        // Load des Evénements et des Commandes
        //********************************************
        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dEnregistrement des events et commandes...");
        ListenerManager.registers(this);
        CommandManager.registers();

        //********************************************
        // Load ChatGames
        //********************************************
        if(ConfigBuilder.getCBool("enabled", ConfigType.GAMECHAT.getConfigName())){
            this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dInitialisation des chat Games...");
            startGames();
        }

        //********************************************
        // Setup & connexion du mysql
        //********************************************
        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dSetup et connexion au mysql...");
        sql = new Database("jdbc:mysql://", ConfigBuilder.getString("sql.host"), ConfigBuilder.getString("sql.database"), ConfigBuilder.getString("sql.user"), ConfigBuilder.getString("sql.pass"));
        sql.connection();

        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dChargement des menus...");
        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dChargement du " + pluginName + " fini !");
    }

    @Override
    public void onDisable() {
        if(sql.isConnected()) sql.disconnect();
        stopGames();
    }

    public void startGames() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                if(LocalDateTime.now().getSecond() == 25){
                    Bukkit.broadcastMessage(PREFIX + " §eJeu de rapidé dans le chat dans §65 §esecondes...");
                }
                if(LocalDateTime.now().getSecond() == 26){
                    Bukkit.broadcastMessage(PREFIX + " §eJeu de rapidé dans le chat dans §64 §esecondes...");
                }
                if(LocalDateTime.now().getSecond() == 27){
                    Bukkit.broadcastMessage(PREFIX + " §eJeu de rapidé dans le chat dans §63 §esecondes...");
                }
                if(LocalDateTime.now().getSecond() == 28){
                    Bukkit.broadcastMessage(PREFIX + " §eJeu de rapidé dans le chat dans §62 §esecondes...");
                }
                if(LocalDateTime.now().getSecond() == 29){
                    Bukkit.broadcastMessage(PREFIX + " §eJeu de rapidé dans le chat dans §61 §eseconde...");
                }
                if(LocalDateTime.now().getSecond() == 30){
                    getNumberGame().newGame();
                }
            }
        },20, 20);
    }

    public void stopGames() {
        Bukkit.getScheduler().cancelTasks(this);
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
    public GuiManager getGuiManager() {
        return guiManager;
    }

    public PermissionsHelper getPermissionsHelper() {
        return permissionsHelper;
    }

    public StackEntity getStackEntity() {
        return stackEntity;
    }

    public EntityStackerManager getEntityStacker() {
        return entityStacker;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
            economy = (Economy)economyProvider.getProvider();
        return (economy != null);
    }

    public NumberGame getNumberGame() {
        return numberGame;
    }
}
