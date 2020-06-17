package fr.volax.valkyacore.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;

public class FileManager {
    private final JavaPlugin plugin;
    private HashMap<String, Config> configs = new HashMap();

    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Config getConfig(String name) {
        if (!this.configs.containsKey(name)) {
            this.configs.put(name, new Config(name));
        }
        return (Config)this.configs.get(name);
    }

    public Config saveConfig(String name) {
        return getConfig(name).save();
    }

    public Config reloadConfig(String name) {
        return getConfig(name).reload();
    }

    public class Config {
        private String name;
        private File file;
        private YamlConfiguration config;

        public Config(String name) {
            this.name = name;
        }

        public Config save() {
            if ((this.config == null) || (this.file == null)) {
                return this;
            }
            try {
                if (this.config.getConfigurationSection("").getKeys(true).size() != 0) {
                    this.config.save(this.file);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return this;
        }

        public YamlConfiguration get() {
            if (this.config == null) {
                reload();
            }
            return this.config;
        }


        public Config saveDefaultConfig() {
            this.file = new File(FileManager.this.plugin.getDataFolder(), this.name);
            FileManager.this.plugin.saveResource(this.name, false);
            return this;
        }

        public Config reload() {
            if (this.file == null) {
                this.file = new File(FileManager.this.plugin.getDataFolder(), this.name);
            }
            this.config = YamlConfiguration.loadConfiguration(this.file);
            try {
                Reader defConfigStream = new InputStreamReader(FileManager.this.plugin.getResource(this.name), "UTF8");
                if (defConfigStream != null) {
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                    this.config.setDefaults(defConfig);
                }
            }
            catch (UnsupportedEncodingException|NullPointerException ignored) {}
            return this;
        }

        public Config copyDefaults(boolean force) {
            get().options().copyDefaults(force);
            return this;
        }

        public Config set(String key, Object value) {
            get().set(key, value);
            return this;
        }

        public Object get(String key) {
            return get().get(key);
        }
    }
}

