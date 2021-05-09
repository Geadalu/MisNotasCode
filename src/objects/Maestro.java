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
import java.util.ArrayList;
import java.util.HashMap;
import noname.DBConnection;

/**
 *
 * @author lucia
 */
public class Maestro {

    int idMaestro;
    String nombre;
    String contraseña;
    HashMap<Integer, String> asignaturas;
    ArrayList<Integer> cursos;

    /**
     * Constructor con la contraseña, usado desde el login
     *
     * @param idMaestro
     * @param contraseña
     */
    public Maestro(int idMaestro, String contraseña) {
        this.idMaestro = idMaestro;
        this.contraseña = contraseña;
        this.asignaturas = new HashMap<>();
        this.cursos = new ArrayList<>();
    }

    public void cargarMaestro() throws SQLException {
        String sqlNombre = "SELECT nombre FROM datossesion WHERE idMaestro = " + this.idMaestro;
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultNombre = st.executeQuery(sqlNombre);

        if (resultNombre.next()) {
            this.nombre = resultNombre.getString("nombre");
        }
        cargarAsignaturas();
    }

    public boolean comprobarCredenciales() throws SQLException {
        String sqlCredenciales = "SELECT * FROM datossesion WHERE idMaestro = " + this.idMaestro + " AND contraseña ='" + this.contraseña + "';";
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultCredenciales = st.executeQuery(sqlCredenciales);

        if (resultCredenciales.next()) {
            return true;
        } else {
            AuxiliarMethods.showWarning("Usuario o contraseña incorrecto/s.");
            return false;
        }

    }

    public void cargarAsignaturas() throws SQLException {
        String sqlAsignaturas = "SELECT idAsignatura FROM maestro WHERE idMaestro = " + this.idMaestro;
        String sqlNombreAsignatura;
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultAsignaturas = st.executeQuery(sqlAsignaturas);

        while (resultAsignaturas.next()) {
            int idAsignatura = resultAsignaturas.getInt("idAsignatura");
            sqlNombreAsignatura = "SELECT nombreAsignatura FROM asignatura WHERE idAsignatura = " + idAsignatura;
            Statement st2 = DBConnection.getConnection().createStatement();
            ResultSet resultNombresAsignaturas = st2.executeQuery(sqlNombreAsignatura);
            if (resultNombresAsignaturas.next()) {
                this.asignaturas.put(idAsignatura, resultNombresAsignaturas.getString("nombreAsignatura")); 
                if (!cursos.contains(Integer.parseInt(String.valueOf(String.valueOf(idAsignatura).charAt(1))))) {
                    this.cursos.add(Integer.parseInt(String.valueOf(String.valueOf(idAsignatura).charAt(1))));
                }   
            }
        }
    }

    public void updateMaestro(String nombre, String contraseña) throws SQLException {
        this.setContraseña(contraseña);
        this.setNombre(nombre);
        String sqlMaestro = "UPDATE datossesion SET contraseña = '"
                + contraseña + "', nombre = '"
                + nombre + "' WHERE idMaestro = " + this.idMaestro;
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

    public HashMap<Integer, String> getAsignaturas() {
        return asignaturas;
    }
    
    
    public ArrayList<Integer> getCursos() {
        return cursos;
    }


    

}
