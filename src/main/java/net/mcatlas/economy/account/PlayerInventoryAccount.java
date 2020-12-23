package net.mcatlas.economy.account;

import net.mcatlas.economy.Worth;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryAccount implements Account {

    private final Player player;
    private final Inventory inventory;

    public PlayerInventoryAccount(Player player) {
        this.player = player;
        this.inventory = player.getInventory();
    }

    @Override
    public String getHolder() {
        return player.getName();
    }

    @Override
    public int getBalance() {
        int balance = 0;
        for (ItemStack itemStack : this.inventory.getContents()) {
            if (itemStack == null) continue;
            for (Worth worth : Worth.values()) {
                if (worth.getMaterial() != itemStack.getType()) continue;
                balance += worth.getAmount() * itemStack.getAmount();
            }
        }
        return balance;
    }

    @Override
    public void subtract(int amount) {
        int amountLeft = amount;
        for (int i = 0; i < this.inventory.getSize(); ++i) {
            ItemStack stack = this.inventory.getItem(i);
            if (stack == null || stack.getType() != Material.GOLD_INGOT) continue;
            if (amountLeft == 0) break;
            if (amountLeft >= stack.getAmount()) {
                amountLeft -= stack.getAmount();
                stack.setAmount(0);
            } else if (amountLeft < stack.getAmount()) {
                stack.setAmount(stack.getAmount() - amountLeft);
                amountLeft = 0;
            }
            this.inventory.setItem(i, stack);
        }
    }

    @Override
    public void add(int amount) {
        int left;
        for (left = amount; left >= 64; left -= 64) {
            ItemStack fullStack = new ItemStack(Material.GOLD_INGOT);
            fullStack.setAmount(64);
            this.inventory.addItem(new ItemStack[]{fullStack}).values().forEach(stack -> this.player.getWorld().dropItem(this.player.getLocation(), stack));
        }
        ItemStack partialStack = new ItemStack(Material.GOLD_INGOT);
        partialStack.setAmount(left);
        this.inventory.addItem(new ItemStack[]{partialStack}).values().forEach(stack -> this.player.getWorld().dropItem(this.player.getLocation(), stack));
    }

}

