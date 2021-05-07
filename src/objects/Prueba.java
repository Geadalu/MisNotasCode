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
import noname.DBConnection;

/**
 *
 * @author lucia
 */
public class Prueba {

    public int idPrueba;


    int idAsignatura;
    String nombrePrueba;
    String fechaPrueba;
    int trimestre;
    int peso;
    ArrayList competenciasPrueba;

    public Prueba(int idAsignatura, String nombrePrueba, String fechaPrueba, int trimestre, int peso, ArrayList competenciasPrueba) {
        this.idAsignatura = idAsignatura;
        this.nombrePrueba = nombrePrueba;
        this.fechaPrueba = fechaPrueba;
        this.trimestre = trimestre;
        this.peso = peso;
        this.competenciasPrueba = competenciasPrueba;
    }
    
    public Prueba(){
        
    }
    
    public void cargarPrueba(int idPrueba) throws SQLException {
        String sqlPruebas = "SELECT * FROM prueba WHERE idPrueba = " + idPrueba;

        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultPruebas = st.executeQuery(sqlPruebas);

        while (resultPruebas.next()) {
            this.idPrueba = resultPruebas.getInt("idPrueba");
            this.idAsignatura = resultPruebas.getInt("idAsignatura");
            this.nombrePrueba = resultPruebas.getString("nombrePrueba");
            this.fechaPrueba = resultPruebas.getString("fechaPrueba");
            this.trimestre = resultPruebas.getInt("trimestre");
            this.peso = resultPruebas.getInt("peso");

        }
    }

    public int commitNuevaPrueba() throws SQLException {
        String sqlPrueba = "INSERT INTO prueba (idAsignatura, nombrePrueba, fechaPrueba, trimestre, peso) VALUES ('"
                + this.getIdAsignatura() + "', '"
                + this.getNombrePrueba() + "', '"
                + this.getFechaPrueba() + "', '"
                + this.getTrimestre() + "', '"
                + this.getPeso() + "'"
                + ")";

        Statement st = DBConnection.getConnection().createStatement();
        st.executeUpdate(sqlPrueba);
        
        sqlPrueba = "SELECT idPrueba FROM prueba WHERE idAsignatura = '"
                +this.getIdAsignatura()+"' AND nombrePrueba = '"
                +this.getNombrePrueba()+"' AND fechaPrueba = '"
                +this.getFechaPrueba()+"';";
        st = DBConnection.getConnection().createStatement();
        ResultSet resultidPrueba = st.executeQuery(sqlPrueba);
        
        if (resultidPrueba.next()){
            return resultidPrueba.getInt("idPrueba");
        } else {
            return 0;
        }

    }
    
    public void commitCompetencias() throws SQLException{
        Statement st;
        for (int i=0; i<this.competenciasPrueba.size(); i++){
            String sqlCompetencias = "INSERT INTO competenciasporprueba VALUES ("+this.competenciasPrueba.get(i)+", "+this.idPrueba+");";
            
            st = DBConnection.getConnection().createStatement();
            st.executeUpdate(sqlCompetencias);
        }
        
        
        
    }

    public int getIdPrueba() {
        return idPrueba;
    }
    
    public int getIdAsignatura() {
        return idAsignatura;
    }

    public String getNombrePrueba() {
        return nombrePrueba;
    }

    public String getFechaPrueba() {
        return fechaPrueba;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public int getPeso() {
        return peso;
    }
    
     public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public void setNombrePrueba(String nombrePrueba) {
        this.nombrePrueba = nombrePrueba;
    }

    public void setFechaPrueba(String fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    
        public void setIdPrueba(int idPrueba) {
        this.idPrueba = idPrueba;
    }

    public void setCompetenciasPrueba(ArrayList competenciasPrueba) {
        this.competenciasPrueba = competenciasPrueba;
    }

}
