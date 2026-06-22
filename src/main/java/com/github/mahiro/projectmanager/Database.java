package com.github.mahiro.projectmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{
    private static final String url = "jdbc:postgresql://localhost:5432/postgres?options=-c%20client_encoding=UTF8";
    private static final String username = "postgres";
    private static final String password = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}