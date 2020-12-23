package net.mcatlas.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BalanceCommand implements CommandExecutor {

    private Map<Player, Long> recentUsages;

    public BalanceCommand() {
        recentUsages = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String labelw, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (recentUsages.containsKey(player)) {
            long lastBetTime = recentUsages.get(player);
            long currentTime = System.currentTimeMillis();

            if (lastBetTime + TimeUnit.SECONDS.toMillis(15) > currentTime) {
                player.sendMessage(ChatColor.RED + "Please wait before using this command again.");
                return false;
            }
        }

        recentUsages.put(player, System.currentTimeMillis());

        int balance = (int) EconomyPlugin.getEconomy().getBalance(player);

        Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " has " + ChatColor.GREEN + balance + ChatColor.GOLD + " gold ingots on them");

        return true;
    }

}
