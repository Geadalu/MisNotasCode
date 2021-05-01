/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package controladores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import noname.DBConnection;
import objects.Competencia;
import objects.Prueba;

/**
 *
 * @author lucia
 */
public class ControladorPrueba {

    private HashMap<Integer, ArrayList<Prueba>> pruebasAsignatura; //idAsignatura, lista de pruebas
    private HashMap<Integer, ArrayList<Competencia>> competenciasPrueba; //idPrueba, lista de competencias

   
    
    public ControladorPrueba() {
        this.competenciasPrueba = new HashMap<>();
        this.pruebasAsignatura = new HashMap<>();
        this.pruebasAsignatura.put(131, new ArrayList<>()); //pruebas de matemáticas 3ºA
        this.pruebasAsignatura.put(132, new ArrayList<>()); //pruebas de matemáticas 3ºB
        this.pruebasAsignatura.put(133, new ArrayList<>()); //pruebas de matemáticas 3ºC
        this.pruebasAsignatura.put(331, new ArrayList<>()); //pruebas de lengua 3ºA
        //TODO cómo controlar el número de asignaturas?
    }

    public void cargarPruebasAsignatura(int asignatura) throws SQLException {
        if (this.pruebasAsignatura.get(asignatura).isEmpty()) {
            String sqlPrueba = "SELECT idPrueba FROM prueba WHERE idAsignatura = " + asignatura;
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet resultPruebas = st.executeQuery(sqlPrueba);

            while (resultPruebas.next()) {
                int idPrueba = resultPruebas.getInt("idPrueba");
                Prueba p = new Prueba();
                p.cargarPrueba(idPrueba);
                this.pruebasAsignatura.get(asignatura).add(p);
            }
        }
    }
    
    public void cargarCompetenciasPrueba(int prueba) throws SQLException {
        if (this.competenciasPrueba.get(prueba).isEmpty()) {
            String sqlCompetencias = "SELECT idCompetencia FROM competenciasporpruebas WHERE idPrueba = "+prueba;
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet resultCompetencias = st.executeQuery(sqlCompetencias);
            
            while (resultCompetencias.next()) {
                competenciasPrueba.get(prueba).add(new Competencia(resultCompetencias.getInt("idCompetencia")));
            }
        }
    }
    
    public void añadirCompetenciaAPrueba(Competencia comp, Prueba p){
        competenciasPrueba.get(p.idPrueba).add(comp);
    }

    public void añadirPruebaAAsignatura(Prueba prueba, int asignatura) {
        this.pruebasAsignatura.get(asignatura).add(prueba);
    }

    public HashMap<Integer, ArrayList<Prueba>> getPruebasAsignatura() {
        return pruebasAsignatura;
    }
    
     public HashMap<Integer, ArrayList<Competencia>> getCompetenciasPrueba() {
        return competenciasPrueba;
    }
     
    public void setPruebasAsignatura(HashMap<Integer, ArrayList<Prueba>> pruebasAsignatura) {
        this.pruebasAsignatura = pruebasAsignatura;
    }

    public void setCompetenciasPrueba(HashMap<Integer, ArrayList<Competencia>> competenciasPrueba) {
        this.competenciasPrueba = competenciasPrueba;
    }

}
