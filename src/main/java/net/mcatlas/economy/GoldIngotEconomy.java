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
        // player account
        if (player.isOnline()) {
            PlayerInventoryAccount pia = new PlayerInventoryAccount(Bukkit.getPlayer(player.getUniqueId()));
            return pia;
        } else {
            return EconomyPlugin.get().getAccountStorage().get(player.getName().toLowerCase());
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
        return (int) amount + " gold ingot" + (amount == 1.0 ? "" : "s");
    }

    public String currencyNamePlural() {
        return "gold ingots";
    }

    public String currencyNameSingular() {
        return "gold ingot";
    }

    public boolean hasAccount(OfflinePlayer player) {
        return true;
    }

    public boolean hasAccount(String playerName) {
        return true;
    }

    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return true;
    }

    public boolean hasAccount(String playerName, String worldName) {
        return true;
    }

    public double getBalance(OfflinePlayer player) {
        Account account = getAccount(player);
        if (account == null) {
            return 0.0;
        }
        return account.getBalance();
    }

    public double getBalance(String playerName) {
        return this.getBalance(Bukkit.getOfflinePlayer(playerName.toLowerCase()));
    }

    public double getBalance(OfflinePlayer player, String world) {
        return this.getBalance(player);
    }

    public double getBalance(String playerName, String world) {
        return this.getBalance(playerName.toLowerCase());
    }

    public boolean has(OfflinePlayer player, double amount) {
        return this.getBalance(player) >= amount;
    }

    public boolean has(String playerName, double amount) {
        return this.getBalance(playerName.toLowerCase()) >= amount;
    }

    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return this.has(player, amount);
    }

    public boolean has(String playerName, String worldName, double amount) {
        return this.has(playerName.toLowerCase(), amount);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        Account account = getAccount(player);
        if (account == null) {
            return new EconomyResponse(0.0, this.getBalance(player), EconomyResponse.ResponseType.FAILURE, "could not load account");
        }
        if ((double) account.getBalance() < amount) {
            return new EconomyResponse(0.0, this.getBalance(player), EconomyResponse.ResponseType.FAILURE, "not enough gold ingots");
        }
        account.subtract((int) amount);
        return new EconomyResponse(amount, this.getBalance(player), EconomyResponse.ResponseType.SUCCESS, "");
    }

    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return this.withdrawPlayer(Bukkit.getOfflinePlayer(playerName.toLowerCase()), amount);
    }

    public EconomyResponse withdrawPlayer(String playerName, String world, double amount) {
        return this.withdrawPlayer(playerName.toLowerCase(), amount);
    }

    public EconomyResponse withdrawPlayer(OfflinePlayer player, String world, double amount) {
        return this.withdrawPlayer(player, amount);
    }

    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        Account account = getAccount(player);
        if (account == null) {
            return new EconomyResponse(0.0, this.getBalance(player), EconomyResponse.ResponseType.FAILURE, "could not load account");
        }
        account.add((int) amount);
        return new EconomyResponse(amount, this.getBalance(player), EconomyResponse.ResponseType.SUCCESS, "");
    }

    public EconomyResponse depositPlayer(String playerName, double amount) {
        return this.depositPlayer(Bukkit.getOfflinePlayer(playerName.toLowerCase()), amount);
    }

    public EconomyResponse depositPlayer(String playerName, String world, double amount) {
        return this.depositPlayer(playerName.toLowerCase(), amount);
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
        return new ArrayList<>();
    }

    public boolean createPlayerAccount(OfflinePlayer player) {
        return true;
    }

    public boolean createPlayerAccount(String playerName) {
        return true;
    }

    public boolean createPlayerAccount(OfflinePlayer player, String world) {
        return true;
    }

    public boolean createPlayerAccount(String playerName, String world) {
        return true;
    }

}

