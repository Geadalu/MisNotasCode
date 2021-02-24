package com.firstpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnection {

    public Connection connect() {
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

        return connection;

    }

    public boolean insertDB(String tabla, ArrayList<String> columnas, ArrayList<String> valores){


        return true;
    }

    public boolean deleteDB(String tabla, ArrayList<String> columnas, ArrayList<String> valores){

        return true;
    }

    public boolean updateDB(String tabla, ArrayList<String> columnas, ArrayList<String> valores){

        return true;
    }

}