package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String USER = "root"; // Cambia esto si es necesario
    private static final String PASSWORD = "root"; // Cambia esto si es necesario

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
