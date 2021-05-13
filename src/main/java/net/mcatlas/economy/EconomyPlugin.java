package net.mcatlas.economy;

import net.mcatlas.economy.storage.MySQLSimpleAccountStorage;
import net.mcatlas.economy.storage.SimpleAccountStorage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyPlugin extends JavaPlugin {

    private static Economy economy;
    private static EconomyPlugin instance;

    private SimpleAccountStorage accountStorage;

    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        String mysqlHost = this.getConfig().getString("storage.mysql.host");
        int mysqlPort = this.getConfig().getInt("storage.mysql.port");
        String mysqlDatabase = this.getConfig().getString("storage.mysql.database");
        String mysqlUsername = this.getConfig().getString("storage.mysql.username");
        String mysqlPassword = this.getConfig().getString("storage.mysql.password");

        this.accountStorage = new MySQLSimpleAccountStorage(mysqlHost, mysqlPort, mysqlDatabase, mysqlUsername, mysqlPassword);

        EconomyPlugin.registerVault();

        if (!setupEconomy()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Loading accounts into cache");
        accountStorage.queryAllIntoCache().join();
        getLogger().info("Loaded accounts into cache (" + accountStorage.getCacheSize() + ")");
    }

    public void onDisable() {
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

        economy = (Economy) rsp.getProvider();

        return economy != null;
    }

    public SimpleAccountStorage getAccountStorage() {
        return accountStorage;
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

    public static Economy getEconomy() {
        return economy;
    }

}

