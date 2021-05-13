package net.mcatlas.economy.account;

import net.mcatlas.economy.EconomyPlugin;

public class SimpleAccount implements Account {

    private final String holder;
    private int balance;

    public SimpleAccount(String holder, int balance) {
        this.holder = holder;
        this.balance = balance;
    }

    public String getHolder() {
        return this.holder;
    }

    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public void subtract(int amount) {
        this.balance -= amount;
        this.save();
    }

    @Override
    public void add(int amount) {
        this.balance += amount;
        this.save();
    }

    private void save() {
        EconomyPlugin.get().getAccountStorage().save(this);
    }

}

