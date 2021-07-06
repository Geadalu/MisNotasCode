/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mainpackage.DBConnection;

/**
 * Clase competencia
 * @author lucia
 */
public class Competencia {
    private int idCompetencia;
    private String nombre;
    private String descripcion;
    
    /**
     * Constructor usado por ControladorCompetencia
     * @param idCompetencia
     * @param nombre
     * @param descripcion 
     */
    public Competencia(int idCompetencia, String nombre, String descripcion){
        this.idCompetencia = idCompetencia;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    /**
     * Constructor usado por ControladorPrueba
     * @param idCompetencia 
     */
    public Competencia(int idCompetencia){
        this.idCompetencia = idCompetencia;
    }
    
    /**
     * Carga una competencia
     * @param idCompetencia
     * @throws SQLException 
     */
    public void cargarCompetencia(int idCompetencia) throws SQLException {
        String sqlBuscarCompetencia = "SELECT nombre, descripcion FROM competencia WHERE idCompetencia = " + idCompetencia;
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultCompetencias = st.executeQuery(sqlBuscarCompetencia);

        if (resultCompetencias.next()) {
            this.nombre = resultCompetencias.getString("nombre");
            this.descripcion = resultCompetencias.getString("descripcion");
        }
    }

    public int getIdCompetencia() {
        return idCompetencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
