package net.mcatlas.economy.storage;

import java.util.concurrent.Callable;
import net.mcatlas.economy.account.Account;

public interface AccountStorage {

    public Callable<Void> store(Account var1);

    public Callable<Account> fetch(String var1);

    default public void close() {
    }

}

