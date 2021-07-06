/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package mainpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton para la conexión con la base de datos
 * @author lucia
 */
public class DBConnection {

    private static Connection connection;

    /**
     * Se conecta a la base de datos
     */
    private static void connect() {
        Connection connection = null;
        try {
            // Conectamos con la base de datos
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/database",
                    "root", "admin");
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "¡Hola, base de datos!" : "No he podido conectarme.");
        } catch (SQLException sqle) {
            System.out.println("¡Uy! Disculpa, ha pasado esto: " + sqle);
        }

        DBConnection.connection = connection;

    }

    /**
     * Recoge la conexión cuando se necesita
     * @return 
     */
    public static Connection getConnection(){
        if (connection == null) {
            DBConnection.connect();
        }
        return DBConnection.connection;
    }

    /**
     * Cierra la conexión
     * @return
     * @throws SQLException 
     */
    public static boolean closeConnection() throws SQLException {
        DBConnection.connection.close();
        return connection.isClosed();
    }
}