package net.mcatlas.economy.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import net.mcatlas.economy.account.Account;
import net.mcatlas.economy.account.SimpleAccount;
import net.mcatlas.economy.storage.SQLAccountStorage;

public class MySQLAccountStorage implements SQLAccountStorage {

    private Connection connection;
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final char[] password;

    public MySQLAccountStorage(String host, int port, String database, String username, char[] password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        try (PreparedStatement create = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS simple_accounts (holder VARCHAR(255) NOT NULL, balance INT DEFAULT 0, PRIMARY KEY(holder));");){
            create.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?useSSL=false", this.username, new String(this.password));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return this.connection;
    }

    @Override
    public synchronized Callable<Void> store(Account account) {
        return () -> {
            if (account instanceof SimpleAccount) {
                SimpleAccount simpleAccount = (SimpleAccount)account;
                try (PreparedStatement insert = this.getConnection().prepareStatement("INSERT INTO simple_accounts (holder, balance) VALUES (?, ?) ON DUPLICATE KEY UPDATE balance = ?;");){
                    insert.setString(1, simpleAccount.getHolder());
                    insert.setInt(2, simpleAccount.getBalance());
                    insert.setInt(3, simpleAccount.getBalance());
                    insert.execute();
                }
            }
            return null;
        };
    }

    @Override
    public synchronized Callable<Account> fetch(String holder) {
        return () -> {
            try (PreparedStatement query = this.getConnection().prepareStatement("SELECT balance FROM simple_accounts WHERE holder = ?;");){
                query.setString(1, holder);
                try (ResultSet rs = query.executeQuery();){
                    if (rs.next()) {
                        SimpleAccount simpleAccount = new SimpleAccount(holder, rs.getInt("balance"));
                        return simpleAccount;
                    }
                }
            }
            return new SimpleAccount(holder, 0);
        };
    }
}

