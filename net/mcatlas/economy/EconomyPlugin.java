package net.mcatlas.economy;

import java.util.logging.Logger;
import net.mcatlas.economy.GoldIngotEconomy;
import net.mcatlas.economy.storage.AccountStorage;
import net.mcatlas.economy.storage.MySQLAccountStorage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyPlugin extends JavaPlugin {

    private AccountStorage accountStorage;
    private static Economy econ = null;
    private static EconomyPlugin instance;

    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        String mysqlHost = this.getConfig().getString("storage.mysql.host");
        int mysqlPort = this.getConfig().getInt("storage.mysql.port");
        String mysqlDatabase = this.getConfig().getString("storage.mysql.database");
        String mysqlUsername = this.getConfig().getString("storage.mysql.username");
        char[] mysqlPassword = this.getConfig().getString("storage.mysql.password").toCharArray();
        this.accountStorage = new MySQLAccountStorage(mysqlHost, mysqlPort, mysqlDatabase, mysqlUsername, mysqlPassword);
        EconomyPlugin.registerVault();
        this.setupEconomy();
    }

    public void onDisable() {
        this.accountStorage.close();
        instance = null;
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = (Economy)rsp.getProvider();
        return econ != null;
    }

    public AccountStorage getAccountStorage() {
        return this.accountStorage;
    }

    public static Economy getEcon() {
        return econ;
    }

    private static void registerVault() {
        Plugin vaultPlugin = instance.getServer().getPluginManager().getPlugin("Vault");
        if (vaultPlugin == null) {
            instance.getLogger().warning("Vault is not installed.");
            return;
        }
        instance.getServer().getServicesManager().register(Economy.class, new GoldIngotEconomy(), instance, ServicePriority.Highest);
    }

    public static EconomyPlugin get() {
        return instance;
    }
}

