package com.example.hotel_booking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static volatile DatabaseConnectionManager instance;
    private Connection connection;

    private DatabaseConnectionManager(){
        try {
            String url = "jdbc:postgresql://localhost:5432/OnlineHotelBooking";
            String user = "postgres";
            String password = "admin";
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnectionManager getInstance() {
        if (instance == null) {  // First check (no locking)
            synchronized (DatabaseConnectionManager.class) {
                if (instance == null) {  // Second check (with locking)
                    instance = new DatabaseConnectionManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
