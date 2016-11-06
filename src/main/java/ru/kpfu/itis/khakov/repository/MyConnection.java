package ru.kpfu.itis.khakov.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    /**
     * Настраиваем соединение с базой данных
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        HsqldbServer.getServer();
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection conn = DriverManager.getConnection(
            "jdbc:hsqldb:hsql://localhost:9001/db", "SA", "");
        return conn;
    }
}
