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
import mainpackage.DBConnection;
import objects.Competencia;
import objects.Nota;
import objects.Prueba;

/**
 * Controlador de las pruebas por asignatura y las competencias  que tiene cada una
 * @author lucia
 */
public class ControladorPrueba {

    private HashMap<Integer, HashMap<Integer, ArrayList<Prueba>>> pruebasAsignatura; //idAsignatura, pruebasTrimestres(trimestre, lista de pruebas)
    private HashMap<Integer, ArrayList<Competencia>> competenciasPrueba; //idPrueba, lista de competencias

   
    
    public ControladorPrueba() {
        this.competenciasPrueba = new HashMap<>();
        this.pruebasAsignatura = new HashMap<>();
        this.pruebasAsignatura.put(211, inicializarHashMap()); //trimestres de conocimiento del medio 1ºA
        this.pruebasAsignatura.put(131, inicializarHashMap()); //trimestres de matemáticas 3ºA
        this.pruebasAsignatura.put(331, inicializarHashMap()); //trimestres de lengua 3ºA
        this.pruebasAsignatura.put(441, inicializarHashMap()); //trimestres de religión 4ºA
    }
    
    private HashMap inicializarHashMap(){
        HashMap<Integer, ArrayList<Prueba>> trimestres = new HashMap<>();
        trimestres.put(1, new ArrayList<>());
        trimestres.put(2, new ArrayList<>());
        trimestres.put(3, new ArrayList<>());
        return trimestres;
    }

    /**
     * Carga las pruebas de una asignatura
     * @param asignatura
     * @throws SQLException 
     */
    public void cargarPruebasAsignatura(int asignatura) throws SQLException {
        HashMap listaTrimestres = this.pruebasAsignatura.get(asignatura); //hashMap de arraylists que contiene trimestre, listadePruebas

        listaTrimestres.put(1, cargarPruebasTrimestre(asignatura, 1));
        listaTrimestres.put(2, cargarPruebasTrimestre(asignatura, 2));
        listaTrimestres.put(3, cargarPruebasTrimestre(asignatura, 3));
        
        pruebasAsignatura.put(asignatura, listaTrimestres);
        
    }
    
    /**
     * Carga las pruebas de un trimestre
     * @param asignatura
     * @param trimestre
     * @return
     * @throws SQLException
     * @throws NullPointerException 
     */
    public ArrayList<Prueba> cargarPruebasTrimestre(int asignatura, int trimestre) throws SQLException, NullPointerException {
        ArrayList<Prueba> pruebasTrimestre = new ArrayList<>();
        if (this.pruebasAsignatura.get(asignatura).get(trimestre) == null || this.pruebasAsignatura.get(asignatura).get(trimestre).isEmpty()) { //si no hay pruebas en el primer trimestre
            String sqlPrueba = "SELECT idPrueba FROM prueba WHERE idAsignatura = " + asignatura + " AND trimestre = "+trimestre;
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet resultPruebas = st.executeQuery(sqlPrueba);

            while (resultPruebas.next()) {
                int idPrueba = resultPruebas.getInt("idPrueba");
                Prueba p = new Prueba();
                p.cargarPrueba(idPrueba);
                cargarCompetenciasPrueba(p.getIdPrueba());
                pruebasTrimestre.add(p);
            }
        }
        return pruebasTrimestre;
    }
    
    /**
     * Carga las competencias de una prueba
     * @param prueba
     * @throws SQLException 
     */
    public void cargarCompetenciasPrueba(int prueba) throws SQLException {
        if (this.competenciasPrueba.get(prueba) == null) {
            ArrayList<Competencia> listaCompetencias = new ArrayList<>();
            String sqlCompetencias = "SELECT idCompetencia FROM competenciasporprueba WHERE idPrueba = "+prueba;
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet resultCompetencias = st.executeQuery(sqlCompetencias);
            
            while (resultCompetencias.next()) {
                Competencia c = new Competencia(resultCompetencias.getInt("idCompetencia"));
                c.cargarCompetencia(c.getIdCompetencia());
                listaCompetencias.add(c);
            }
            competenciasPrueba.put(prueba, listaCompetencias);
        }
    }
    
    /**
     * Borra una prueba de la base de datos y todas sus referencias
     * @param idPrueba
     * @param trimestre
     * @param asignatura
     * @throws SQLException 
     */
    public void borrarPrueba(int idPrueba, int trimestre, int asignatura) throws SQLException {
        String sqlPrueba = "DELETE FROM prueba WHERE idPrueba = "+idPrueba;
        Statement st = DBConnection.getConnection().createStatement();
        st.executeUpdate(sqlPrueba);
        
        sqlPrueba = "DELETE FROM competenciasporprueba WHERE idPrueba = "+idPrueba;
        st.executeUpdate(sqlPrueba);
        
        sqlPrueba = "DELETE FROM nota WHERE idPrueba = "+idPrueba;
        st.executeUpdate(sqlPrueba);
        
        ArrayList<Prueba> pruebitas = (ArrayList<Prueba>) this.pruebasAsignatura.get(asignatura).get(trimestre).clone();
        for (Prueba p : this.pruebasAsignatura.get(asignatura).get(trimestre)){
            if (p.getIdPrueba() == idPrueba){
                pruebitas.remove(this.pruebasAsignatura.get(asignatura).get(trimestre).indexOf(p));
            }
        }
        this.setPruebasAsignatura(pruebasAsignatura);
        
        competenciasPrueba.remove(idPrueba);
        
    }
    
    /**
     * Updatea una prueba en la base de datos
     * @param prueba
     * @throws SQLException 
     */
    public void updatePrueba(Prueba prueba) throws SQLException{
        Statement st = DBConnection.getConnection().createStatement();
        String sqlNotas = "UPDATE prueba SET "
                + "titulo = '" + prueba.getTitulo() + "', "
                + "etiqueta = '" + prueba.getEtiqueta() + "', "
                + "fecha = '" + prueba.getFecha() +"', "
                + "peso = '" + prueba.getPeso() + "' "
                + " WHERE idPrueba = " + prueba.getIdPrueba();
        
        st.executeUpdate(sqlNotas);
    }
    
    public void añadirCompetenciaAPrueba(Competencia comp, Prueba p){
        competenciasPrueba.get(p.idPrueba).add(comp);
    }

    public void añadirPruebaAAsignatura(Prueba prueba, int trimestre, int asignatura) {
        this.pruebasAsignatura.get(asignatura).get(trimestre).add(prueba);
    }
    
     public HashMap<Integer, ArrayList<Competencia>> getCompetenciasPrueba() {
        return competenciasPrueba;
    }
    

    public void setCompetenciasPrueba(HashMap<Integer, ArrayList<Competencia>> competenciasPrueba) {
        this.competenciasPrueba = competenciasPrueba;
    }

    public HashMap<Integer, HashMap<Integer, ArrayList<Prueba>>> getPruebasAsignatura() {
        return pruebasAsignatura;
    }

    public void setPruebasAsignatura(HashMap<Integer, HashMap<Integer, ArrayList<Prueba>>> pruebasAsignatura) {
        this.pruebasAsignatura = pruebasAsignatura;
    }
    

}
