/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package controladores;

import auxiliar.AuxiliarMethods;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import noname.DBConnection;

/**
 *
 * @author lucia
 */
public class ControladorMaestro {
    int idMaestro;
    
    public boolean comprobarCredenciales (int idMaestro, String contraseña) throws SQLException{
        String sqlCredenciales = "SELECT * FROM datossesion WHERE idMaestro = "+idMaestro+" AND password ='"+contraseña+"';";
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultPruebas = st.executeQuery(sqlCredenciales);
        
        if (resultPruebas.next()){
            this.idMaestro = idMaestro;
            return true;
        } else {
            AuxiliarMethods.showWarning("Usuario o contraseña incorrecto/s.");
            return false;
        }
        
    }

    public void cargarAsignaturas(int idMaestro){
        
    }
}
