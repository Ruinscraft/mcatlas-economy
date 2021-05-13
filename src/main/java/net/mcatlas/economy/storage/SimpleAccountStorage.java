package net.mcatlas.economy.storage;

import net.mcatlas.economy.account.SimpleAccount;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SimpleAccountStorage {

    private Map<String, SimpleAccount> cache;

    public SimpleAccountStorage() {
        cache = new ConcurrentHashMap<>();
    }

    public SimpleAccount get(String holder) {
        return cache.get(holder);
    }

    public abstract CompletableFuture<Void> save(SimpleAccount account);

    public CompletableFuture<SimpleAccount> query(String holder) {
        if (cache.containsKey(holder)) {
            return CompletableFuture.completedFuture(cache.get(holder));
        } else {
            return _query(holder);
        }
    }

    public CompletableFuture<Void> queryAllIntoCache() {
        return queryAll().thenAccept(simpleAccounts -> {
            for (SimpleAccount account : simpleAccounts) {
                cache.put(account.getHolder(), account);
            }
        });
    }

    public int getCacheSize() {
        return cache.size();
    }

    // Holder can be a player UUID, a town name, etc
    protected abstract CompletableFuture<SimpleAccount> _query(String holder);

    protected abstract CompletableFuture<List<SimpleAccount>> queryAll();

}
