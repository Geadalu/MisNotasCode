/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package objects;

import auxiliar.AuxiliarMethods;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import noname.DBConnection;

/**
 *
 * @author lucia
 */
public class Maestro {

    String idMaestro;
    String nombre;
    String contraseña;
    HashMap<Integer, String> asignaturas;
    ArrayList<Integer> cursos;
    ImageIcon imagen;

    /**
     * Constructor con la contraseña, usado desde el login
     *
     * @param idMaestro
     * @param contraseña
     */
    public Maestro(String idMaestro, String contraseña) {
        this.idMaestro = idMaestro;
        this.contraseña = contraseña;
        this.asignaturas = new HashMap<>();
        this.cursos = new ArrayList<>();
        this.imagen = new ImageIcon("assets/personGrande.png"); //imagen por defecto
    }
    
    /**
     * Constructor sin contraseña, usado para recuperar contraseña
     * @param idMaestro 
     */
    public Maestro(String idMaestro){
        this.idMaestro = idMaestro;
    }

    public void cargarMaestro() throws SQLException {
        String sqlNombre = "SELECT nombre FROM datossesion WHERE idMaestro = '" + this.idMaestro + "'";
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultNombre = st.executeQuery(sqlNombre);

        if (resultNombre.next()) {
            this.nombre = resultNombre.getString("nombre");
        }
        this.imagen = new ImageIcon("assets/personGrande.png");
        cargarAsignaturas();
    }

    public boolean comprobarCredenciales() throws SQLException {
        String sqlCredenciales = "SELECT * FROM datossesion WHERE idMaestro = '" + this.idMaestro + "' AND contraseña ='" + this.contraseña + "';";
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultCredenciales = st.executeQuery(sqlCredenciales);

        if (resultCredenciales.next()) {
            return true;
        } else {
            AuxiliarMethods.showWarning("Usuario o contraseña incorrecto/s.");
            return false;
        }

    }
    
    public String recuperarContraseña(String usuario) throws SQLException {
        String sqlCredenciales = "SELECT contraseña FROM datossesion WHERE idMaestro = '"+usuario+"';";
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultCredenciales = st.executeQuery(sqlCredenciales);
        
        if (resultCredenciales.next()) {
            return resultCredenciales.getString("contraseña");
        } else {
            return null;
        }
    }

    public void cargarAsignaturas() throws SQLException {
        String sqlAsignaturas = "SELECT idAsignatura FROM maestro WHERE idMaestro = '" + this.idMaestro + "'";
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
        if (!contraseña.equals("")) {
            this.setContraseña(contraseña);
        }

        this.setNombre(nombre);
        String sqlMaestro = "UPDATE datossesion SET contraseña = '"
                + this.getContraseña() + "', nombre = '"
                + nombre + "' WHERE idMaestro = '" + this.idMaestro + "'";
        Statement st = DBConnection.getConnection().createStatement();

        st.executeUpdate(sqlMaestro);
    }

    public String getIdMaestro() {
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

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }
}
