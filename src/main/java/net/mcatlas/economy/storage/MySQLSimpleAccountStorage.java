package net.mcatlas.economy.storage;

import net.mcatlas.economy.account.SimpleAccount;

import java.sql.*;
import java.util.concurrent.CompletableFuture;

public class MySQLSimpleAccountStorage implements SimpleAccountStorage {

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    public MySQLSimpleAccountStorage(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS simple_accounts (holder VARCHAR(255) NOT NULL, balance INT DEFAULT 0, PRIMARY KEY(holder));");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public synchronized CompletableFuture<Void> save(SimpleAccount account) {
        System.out.println("Saving economy account for " + account.getHolder());
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                try (PreparedStatement upsert = connection.prepareStatement("INSERT INTO simple_accounts (holder, balance) VALUES (?, ?) ON DUPLICATE KEY UPDATE balance = ?;")) {
                    upsert.setString(1, account.getHolder());
                    upsert.setInt(2, account.getBalance());
                    upsert.setInt(3, account.getBalance());
                    upsert.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public synchronized CompletableFuture<SimpleAccount> query(String holder) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection()) {
                try (PreparedStatement query = connection.prepareStatement("SELECT balance FROM simple_accounts WHERE holder = ?;")) {
                    query.setString(1, holder);

                    try (ResultSet result = query.executeQuery()) {
                        if (result.next()) {
                            SimpleAccount simpleAccount = new SimpleAccount(holder, result.getInt("balance"));
                            return simpleAccount;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

}

