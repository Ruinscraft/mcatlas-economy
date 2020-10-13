package net.mcatlas.economy.storage;

import java.sql.Connection;
import net.mcatlas.economy.storage.AccountStorage;

public interface SQLAccountStorage extends AccountStorage {

    public Connection getConnection();

}

