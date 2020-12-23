package net.mcatlas.economy.storage;

import net.mcatlas.economy.account.SimpleAccount;

import java.util.concurrent.CompletableFuture;

public interface SimpleAccountStorage {

    CompletableFuture<Void> save(SimpleAccount account);

    // Holder can be a player UUID, a town name, etc
    CompletableFuture<SimpleAccount> query(String holder);

}
