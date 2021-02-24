package com.firstpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    private static void connect() {
        Connection connection = null;
        try {
            // Conectamos con la base de datos
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/database",
                    "root", "admin");
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "¡Hola, base de datos!" : "No he podido conectarme.");
        } catch (java.sql.SQLException sqle) {
            System.out.println("¡Uy! Disculpa, ha pasado esto: " + sqle);
        }

        DBConnection.connection = connection;

    }

    public static Connection getConnection(){
        if (connection == null) {
            DBConnection.connect();
        }
        return DBConnection.connection;
    }

    public static boolean closeConnection() throws SQLException {
        DBConnection.connection.close();
        return connection.isClosed();
    }
}