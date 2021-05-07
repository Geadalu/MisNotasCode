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
        ResultSet resultCredenciales = st.executeQuery(sqlCredenciales);
        
        if (resultCredenciales.next()){
            this.idMaestro = idMaestro;
            return true;
        } else {
            AuxiliarMethods.showWarning("Usuario o contraseña incorrecto/s.");
            return false;
        }
        
    }

    public void cargarAsignaturas(int idMaestro){
        
    }
    
    public String devolverNombre(int idMaestro) throws SQLException{
        String sqlNombre = "SELECT nombre FROM maestro WHERE idMaestro = "+idMaestro;
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultNombre= st.executeQuery(sqlNombre);
        
        if(resultNombre.next()){
            return resultNombre.getString("nombre");
        } else {
            return "N/A";
        }
    }
    
    public void updateMaestro(int idMaestro, String nombre) throws SQLException{
        String sqlMaestro = "UPDATE maestro SET (idMaestro, nombre) VALUES ("+idMaestro+", "+nombre+");";
        Statement st = DBConnection.getConnection().createStatement();
        
        st.executeUpdate(sqlMaestro);
    }
}
