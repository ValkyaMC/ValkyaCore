/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.volax.valkyacore.commands.CommandManager;
import fr.volax.valkyacore.listener.BlockListener;
import fr.volax.valkyacore.listener.ListenerManager;
import fr.volax.valkyacore.listener.PlayerListener;
import fr.volax.valkyacore.managers.*;
import fr.volax.valkyacore.obsidianbreaker.*;
import fr.volax.valkyacore.spawners.SpawnersManager;
import fr.volax.valkyacore.tools.StaffInventory;
import fr.volax.valkyacore.utils.*;
import fr.volax.volaxapi.VolaxAPI;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import fr.volax.volaxapi.tool.config.FileManager;
import fr.volax.volaxapi.tool.database.Database;
import fr.volax.volaxapi.tool.gui.GuiManager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ValkyaCore for Valkya PvP-Faction Modded
 * @author Volax
 */
public class ValkyaCore extends JavaPlugin {
    @Getter private static ValkyaCore instance;
    @Getter private BanManager banManager;
    @Getter private MuteManager muteManager;
    @Getter private ReportManager reportManager;
    @Getter private PlayerUtils playerUtils;
    @Getter private PermissionsHelper permissionsHelper;
    public Database sql;
    @Getter private GuiManager guiManager;
    @Getter private EntityStackerManager entityStacker;
    @Getter private PvPPlayerManager pvPPlayerManager;
    @Getter private StackEntity stackEntity;
    public static Economy economy;
    @Getter private StorageHandler storage;
    @Getter private StaffMod staffMod;
    private BukkitTask regenRunner;
    @Getter private SpawnersManager spawnersManager;
    @Getter private ConfigBuilder configBuilder;

    public Map<Player, StaffInventory> mode;
    public List<UUID> frozen;
    public Map<Inventory, UUID> admin;
    public HashMap<UUID, Long> repair;
    public HashMap<String, Long> cooldown;

    public final String PREFIX = "§6Valkya »", LOGGER_PREFIX = "["+ this.getName()+"-Logger]";

    @Override
    public void onEnable() {
        instance = this;
        VolaxAPI.setInstance(this);
        configBuilder = new ConfigBuilder(new FileManager(this));
        
        if (getServer().getPluginManager().getPlugin("WorldGuard") == null || !(getServer().getPluginManager().getPlugin("WorldGuard") instanceof WorldGuardPlugin))
            MobStackerConfig.worldguardEnabled = false;

        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dSauvegarde et enregistrements des configs...");
        this.saveDefaultConfig();

        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dSetup des instances et des HashMaps/ArrayLists...");
        banManager = new BanManager();
        muteManager = new MuteManager();
        reportManager = new ReportManager();
        playerUtils = new PlayerUtils();
        guiManager = new GuiManager();
        permissionsHelper = new PermissionsHelper();
        MobStackerConfig.reloadConfig();
        entityStacker = new EntityStackerManager(MobStackerConfig.stackRadius, MobStackerConfig.mobsToStack);
        stackEntity = new StackEntity();
        pvPPlayerManager = new PvPPlayerManager();
        BlockListener blockListener = new BlockListener();
        PlayerListener playerListener = new PlayerListener();
        staffMod = new StaffMod(this);
        storage = new StorageHandler();
        spawnersManager = new SpawnersManager();
        cooldown = new HashMap<>();
        repair  = new HashMap<>();
        admin = new HashMap<>();
        mode = new HashMap<>();
        frozen = new ArrayList<>();
        if (setupEconomy()) this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dVault §aON...");
        else this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dVault §cOFF§d, the plugin don't work without Vault try to fix him...");


        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dEnregistrement des events et commandes...");
        ListenerManager.registers(this);
        CommandManager.registers();

        this.getServer().getConsoleSender().sendMessage(LOGGER_PREFIX + " §dSetup et connexion au mysql...");
        sql = new Database("jdbc:mysql://", ValkyaCore.getInstance().getConfigBuilder().getString("sql.host"), ValkyaCore.getInstance().getConfigBuilder().getString("sql.database"), ValkyaCore.getInstance().getConfigBuilder().getString("sql.user"), ValkyaCore.getInstance().getConfigBuilder().getString("sql.pass"));
        sql.connection();

        this.getServer().getConsoleSender().sendMessage("\n\n\n" +
                ":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" +
                "'##::::'##::::'###::::'##:::::::'##:::'##:'##:::'##::::'###:::::'######:::'#######::'########::'########:::::'#######::'##::: ##:\n" +
                " ##:::: ##:::'## ##::: ##::::::: ##::'##::. ##:'##::::'## ##:::'##... ##:'##.... ##: ##.... ##: ##.....:::::'##.... ##: ###:: ##:\n" +
                " ##:::: ##::'##:. ##:: ##::::::: ##:'##::::. ####::::'##:. ##:: ##:::..:: ##:::: ##: ##:::: ##: ##:::::::::: ##:::: ##: ####: ##:\n" +
                " ##:::: ##:'##:::. ##: ##::::::: #####::::::. ##::::'##:::. ##: ##::::::: ##:::: ##: ########:: ######:::::: ##:::: ##: ## ## ##:\n" +
                ". ##:: ##:: #########: ##::::::: ##. ##:::::: ##:::: #########: ##::::::: ##:::: ##: ##.. ##::: ##...::::::: ##:::: ##: ##. ####:\n" +
                ":. ## ##::: ##.... ##: ##::::::: ##:. ##::::: ##:::: ##.... ##: ##::: ##: ##:::: ##: ##::. ##:: ##:::::::::: ##:::: ##: ##:. ###:\n" +
                "::. ###:::: ##:::: ##: ########: ##::. ##:::: ##:::: ##:::: ##:. ######::. #######:: ##:::. ##: ########::::. #######:: ##::. ##:\n" +
                ":::...:::::..:::::..::........::..::::..:::::..:::::..:::::..:::......::::.......:::..:::::..::........::::::.......:::..::::..::\n" +
                ":::::::::::::::::::::'########::'##:::'##::::'##::::'##::::'###::::'##:::::::'##:::'##:'##:::'##::::'###:::::::::::::::::::::::::\n" +
                "::::::::::::::::::::::##.... ##:. ##:'##::::: ##:::: ##:::'## ##::: ##::::::: ##::'##::. ##:'##::::'## ##::::::::::::::::::::::::\n" +
                "::::::::::::::::::::::##:::: ##::. ####:::::: ##:::: ##::'##:. ##:: ##::::::: ##:'##::::. ####::::'##:. ##:::::::::::::::::::::::\n" +
                "::::::::::::::::::::::########::::. ##::::::: ##:::: ##:'##:::. ##: ##::::::: #####::::::. ##::::'##:::. ##::::::::::::::::::::::\n" +
                "::::::::::::::::::::::##.... ##:::: ##:::::::. ##:: ##:: #########: ##::::::: ##. ##:::::: ##:::: #########::::::::::::::::::::::\n" +
                "::::::::::::::::::::::##:::: ##:::: ##::::::::. ## ##::: ##.... ##: ##::::::: ##:. ##::::: ##:::: ##.... ##::::::::::::::::::::::\n" +
                "::::::::::::::::::::::########::::: ##:::::::::. ###:::: ##:::: ##: ########: ##::. ##:::: ##:::: ##:::: ##::::::::::::::::::::::\n" +
                "::::::::::::::::::::::.......::::::..:::::::::::...:::::..:::::..::........::..::::..:::::..:::::..:::::..:::::::::::::::::::::::\n\n");

        scheduleRegenRunner();
    }

    @Override
    public void onDisable() {
        if (sql.isConnected()) sql.disconnect();
        stopGames();
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

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
            economy = economyProvider.getProvider();
        return (economy != null);
    }

    public static String getLOGGER_PREFIX() {
        return getInstance().LOGGER_PREFIX;
    }

    public static String getPREFIX() {
        return getInstance().PREFIX;
    }

    public void scheduleRegenRunner() {
        if (this.regenRunner != null) {
            this.regenRunner.cancel();
            this.regenRunner = null;
        }
        long freq = getConfig().getLong("Regen.Frequency") * 20L * 60L;
        if (freq > 0L)
            this.regenRunner = (new ValkyaCore.RegenRunnable()).runTaskTimerAsynchronously(this, freq, freq);
    }

    class RegenRunnable extends BukkitRunnable {
        public void run() {
            try {
                for (ConcurrentHashMap<String, BlockStatus> map : ValkyaCore.this.storage.damage.values()) {
                    for (BlockStatus status : map.values()) {
                        if (status.isModified()) {
                            status.setModified(false);
                            continue;
                        }
                        status.setDamage(status.getDamage() - (float)ValkyaCore.this.getConfig().getDouble("Regen.Amount"));
                        if (status.getDamage() < 0.001F)
                            ValkyaCore.this.getStorage().removeBlockStatus(status);
                    }
                }
            } catch (Exception ignored) {}
        }
    }
}
