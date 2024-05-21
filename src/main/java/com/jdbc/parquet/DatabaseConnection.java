package com.jdbc.parquet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String mysqlUrl = "jdbc:mysql://localhost/parquet";
            connection = DriverManager.getConnection(mysqlUrl, "root", "051002");
        }
        return connection;
    }
}
