package net.mcatlas.economy.account;

public interface Account {

    String getHolder();

    int getBalance();

    void subtract(int var1);

    void add(int var1);

}
