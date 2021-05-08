/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package objects;

import auxiliar.AuxiliarMethods;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import noname.DBConnection;

/**
 *
 * @author lucia
 */
public class Maestro {

    int idMaestro;
    String nombre;
    String contraseña;

    /**
     * Constructor usado para pruebas, sin contraseña
     * @param idMaestro 
     */
    public Maestro(int idMaestro) {
        this.idMaestro = idMaestro;
    }
    
    /**
     * Constructor con la contraseña, usado desde el login
     * @param idMaestro
     * @param contraseña 
     */
    public Maestro(int idMaestro, String contraseña){
        this.idMaestro = idMaestro;
        this.contraseña = contraseña;
    }
    
    public void cargarMaestro() throws SQLException {
        String sqlNombre = "SELECT nombre FROM datossesion WHERE idMaestro = " + this.idMaestro;
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultNombre = st.executeQuery(sqlNombre);

        if (resultNombre.next()) {
            this.nombre = resultNombre.getString("nombre");
        }
    }

    public boolean comprobarCredenciales() throws SQLException {
        String sqlCredenciales = "SELECT * FROM datossesion WHERE idMaestro = " + this.idMaestro + " AND password ='" + this.contraseña + "';";
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultCredenciales = st.executeQuery(sqlCredenciales);

        if (resultCredenciales.next()) {
            return true;
        } else {
            AuxiliarMethods.showWarning("Usuario o contraseña incorrecto/s.");
            return false;
        }

    }

    public void cargarAsignaturas() {
            
    }


    public void updateMaestro(String nombre, String contraseña) throws SQLException {
        this.setContraseña(contraseña);
        this.setNombre(nombre);
        String sqlMaestro = "UPDATE datossesion SET contraseña = '"
                + contraseña +"', nombre = '" 
                + nombre + "' WHERE idMaestro = "+this.idMaestro;
        Statement st = DBConnection.getConnection().createStatement();

        st.executeUpdate(sqlMaestro);
    }

    public int getIdMaestro() {
        return idMaestro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
