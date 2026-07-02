package com.github.mahiro.projectmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?options=-c%20client_encoding=UTF8";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    // ドライバをこのクラスのクラスローダーで明示的にロードしておく。
    // HttpServerのディスパッチャスレッドなどcontext classloaderが異なるスレッドから
    // 呼ばれてもDriverManagerが解決できるようにするため（ServiceLoaderの自動検出に頼らない）。
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    //インスタンス化防止
    private Database(){

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}