package ru.daru_jo.repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ConnectionCenter {
    private static ConnectionCenter Instance;
    private final String URL = "jdbc:sqlite:db\\storage.db";
    private final Map<Connection, Boolean> connectDBs = new HashMap<>();

    private ConnectionCenter() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        for (Map.Entry<Connection, Boolean> entry : connectDBs.entrySet()) {
            if (!entry.getValue()) {
                entry.setValue(true);
                return entry.getKey();
            }
        }
        try {
            Connection connection = DriverManager.getConnection(URL);
            connectDBs.put(connection, true);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void disableConnect(Connection connection) {
        connectDBs.put(connection, false);
    }


    public static ConnectionCenter getInstance() {
        if (Instance == null) {
            Instance = new ConnectionCenter();
        }
        return Instance;
    }

    public static void close() {
        if (Instance == null) {
            return;
        }
        try {
            for (Map.Entry<Connection, Boolean> entry : Instance.connectDBs.entrySet()) {

                entry.getKey().close();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}