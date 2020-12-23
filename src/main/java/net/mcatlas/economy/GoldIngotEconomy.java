package net.mcatlas.economy;

import net.mcatlas.economy.account.Account;
import net.mcatlas.economy.account.PlayerInventoryAccount;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class GoldIngotEconomy implements Economy {

    private static Account getAccount(OfflinePlayer player) {
        if (player.isOnline()) {
            return new PlayerInventoryAccount(Bukkit.getPlayer(player.getUniqueId()));
        } else if (!player.hasPlayedBefore()) {
            return EconomyPlugin.get().getAccountStorage().query(player.getName()).join();
        } else {
            return null;
        }
    }

    public boolean isEnabled() {
        return EconomyPlugin.get() != null;
    }

    public String getName() {
        return "mcatlas-economy";
    }

    public boolean hasBankSupport() {
        return false;
    }

    public int fractionalDigits() {
        return 0;
    }

    public String format(double amount) {
        return (int)amount + " gold ingot" + (amount == 1.0 ? "" : "s");
    }

    public String currencyNamePlural() {
        return "gold ingots";
    }

    public String currencyNameSingular() {
        return "gold ingot";
    }

    public boolean hasAccount(OfflinePlayer player) {
        return player.hasPlayedBefore();
    }

    public boolean hasAccount(String playerName) {
        return this.hasAccount(Bukkit.getOfflinePlayer((String)playerName));
    }

    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return this.hasAccount(player);
    }

    public boolean hasAccount(String playerName, String worldName) {
        return this.hasAccount(playerName);
    }

    public double getBalance(OfflinePlayer player) {
        Account account = GoldIngotEconomy.getAccount(player);
        if (account == null) {
            return 0.0;
        }
        return account.getBalance();
    }

    public double getBalance(String playerName) {
        return this.getBalance(Bukkit.getOfflinePlayer((String)playerName));
    }

    public double getBalance(OfflinePlayer player, String world) {
        return this.getBalance(player);
    }

    public double getBalance(String playerName, String world) {
        return this.getBalance(playerName);
    }

    public boolean has(OfflinePlayer player, double amount) {
        return this.getBalance(player) >= amount;
    }

    public boolean has(String playerName, double amount) {
        return this.getBalance(playerName) >= amount;
    }

    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return this.has(player, amount);
    }

    public boolean has(String playerName, String worldName, double amount) {
        return this.has(playerName, amount);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        Account account = GoldIngotEconomy.getAccount(player);
        if (account == null) {
            return new EconomyResponse(0.0, this.getBalance(player), EconomyResponse.ResponseType.FAILURE, "could not load account");
        }
        if ((double)account.getBalance() < amount) {
            return new EconomyResponse(0.0, this.getBalance(player), EconomyResponse.ResponseType.FAILURE, "not enough gold ingots");
        }
        account.subtract((int)amount);
        return new EconomyResponse(amount, this.getBalance(player), EconomyResponse.ResponseType.SUCCESS, "");
    }

    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return this.withdrawPlayer(Bukkit.getOfflinePlayer((String)playerName), amount);
    }

    public EconomyResponse withdrawPlayer(String playerName, String world, double amount) {
        return this.withdrawPlayer(playerName, amount);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, String world, double amount) {
        return this.withdrawPlayer(player, amount);
    }

    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        Account account = GoldIngotEconomy.getAccount(player);
        if (account == null) {
            return new EconomyResponse(0.0, this.getBalance(player), EconomyResponse.ResponseType.FAILURE, "could not load account");
        }
        account.add((int)amount);
        return new EconomyResponse(amount, this.getBalance(player), EconomyResponse.ResponseType.SUCCESS, "");
    }

    public EconomyResponse depositPlayer(String playerName, double amount) {
        return this.depositPlayer(Bukkit.getOfflinePlayer((String)playerName), amount);
    }

    public EconomyResponse depositPlayer(String playerName, String world, double amount) {
        return this.depositPlayer(playerName, amount);
    }

    public EconomyResponse depositPlayer(OfflinePlayer player, String world, double amount) {
        return this.depositPlayer(player, amount);
    }

    public EconomyResponse createBank(String s, String s1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public EconomyResponse deleteBank(String s) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public EconomyResponse bankBalance(String s) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public EconomyResponse bankHas(String s, double v) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public EconomyResponse bankWithdraw(String s, double v) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public EconomyResponse bankDeposit(String s, double v) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public EconomyResponse isBankOwner(String s, String s1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public EconomyResponse isBankMember(String s, String s1) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Bank accounts are not supported.");
    }

    public List<String> getBanks() {
        return new ArrayList<String>();
    }

    public boolean createPlayerAccount(OfflinePlayer player) {
        return true;
    }

    public boolean createPlayerAccount(String playerName) {
        return this.createPlayerAccount(Bukkit.getOfflinePlayer((String)playerName));
    }

    public boolean createPlayerAccount(OfflinePlayer player, String world) {
        return this.createPlayerAccount(player);
    }

    public boolean createPlayerAccount(String playerName, String world) {
        return this.createPlayerAccount(playerName);
    }

}

