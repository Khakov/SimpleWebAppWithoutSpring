package ru.kpfu.itis.khakov.repository;

import org.hsqldb.Server;

import java.io.PrintWriter;
import java.sql.*;

public class HsqldbServer {
    private static Server server = null;
    /**
     * Настраиваем сервер базы данных и записываем тестовые данные
     */
    public static Server getServer() throws SQLException, ClassNotFoundException {
        if (server == null) {
            server = new Server();
            server.setAddress("localhost");
            server.setDatabaseName(0, "db");
            server.setDatabasePath(0, "mem:db");
            server.setPort(9001);
            server.setTrace(true);
            server.setLogWriter(new PrintWriter(System.out));
            server.start();
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:hsqldb:hsql://localhost:9001/db", "SA", "");
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE users (\n" +
                    "  id INTEGER PRIMARY KEY NOT NULL GENERATED BY DEFAULT AS IDENTITY,\n" +
                    "  login VARCHAR(255),\n" +
                    "  password VARCHAR(255)\n" +
                    ");");
            statement.execute("INSERT INTO users VALUES (DEFAULT,'admin','21232f297a57a5a743894a0e4a801fc3');");
            statement.close();
            conn.close();
        }
        return server;
    }
}
