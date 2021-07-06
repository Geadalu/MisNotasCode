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
import java.util.ArrayList;
import java.util.HashMap;
import mainpackage.DBConnection;
import objects.Competencia;

/**
 * Controlador de las comptencias
 * @author lucia
 */
public class ControladorCompetencia {

    private HashMap<Integer, ArrayList<Competencia>> competenciasAsignatura; //idAsignatura, competencias

    public ControladorCompetencia() {
        this.competenciasAsignatura = new HashMap<>();
        
    }

    /**
     * Carga todas las competencias de una asignatura
     *
     * @param idAsignatura
     * @throws SQLException
     */
    public void cargarCompetenciasAsignatura(int idAsignatura) throws SQLException {
        if (this.competenciasAsignatura.get(idAsignatura) == null) {
            String sqlCompetencias = "SELECT idCompetencia FROM competenciasporasignatura WHERE idAsignatura = " + idAsignatura;
            ArrayList<Competencia> competencias = new ArrayList<>();

            Statement st = DBConnection.getConnection().createStatement();
            ResultSet resultCompetencias = st.executeQuery(sqlCompetencias);

            while (resultCompetencias.next()) {
                Competencia c = new Competencia(resultCompetencias.getInt("idCompetencia"));
                c.cargarCompetencia(c.getIdCompetencia());
                competencias.add(c);
            }
            competenciasAsignatura.put(idAsignatura, competencias);
        }
    }

    public HashMap<Integer, ArrayList<Competencia>> getCompetenciasAsignatura() {
        return competenciasAsignatura;
    }

    public void setCompetenciasAsignatura(HashMap<Integer, ArrayList<Competencia>> competenciasAsignatura) {
        this.competenciasAsignatura = competenciasAsignatura;
    }
}
