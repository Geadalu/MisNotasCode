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
    String titulo;
    String fecha;
    String etiqueta; //título corto de 5 caracteres
    int trimestre;
    int peso;
    ArrayList competenciasPrueba;

    public Prueba(int idAsignatura, String titulo, String etiqueta, String fecha, int trimestre, int peso, ArrayList competenciasPrueba) {
        this.idAsignatura = idAsignatura;
        this.titulo = titulo;
        this.etiqueta = etiqueta;
        this.fecha = fecha;
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
            this.titulo = resultPruebas.getString("titulo");
            this.etiqueta = resultPruebas.getString("etiqueta");
            this.fecha = resultPruebas.getString("fecha");
            this.trimestre = resultPruebas.getInt("trimestre");
            this.peso = resultPruebas.getInt("peso");

        }
    }

    public int commitNuevaPrueba() throws SQLException {
        String sqlPrueba = "INSERT INTO prueba (idAsignatura, titulo, etiqueta, fecha, trimestre, peso) VALUES ('"
                + this.getIdAsignatura() + "', '"
                + this.getTitulo() + "', '"
                + this.getEtiqueta() + "', '"
                + this.getFecha() + "', '"
                + this.getTrimestre() + "', '"
                + this.getPeso() + "'"
                + ")";

        Statement st = DBConnection.getConnection().createStatement();
        st.executeUpdate(sqlPrueba);
        
        //se recoge el idPrueba recién creado
        sqlPrueba = "SELECT idPrueba FROM prueba WHERE idAsignatura = '"
                +this.getIdAsignatura()+"' AND titulo = '"
                +this.getTitulo()+"' AND fecha = '"
                +this.getFecha()+"';";
        st = DBConnection.getConnection().createStatement();
        ResultSet resultidPrueba = st.executeQuery(sqlPrueba);
        
        //y se asigna a la prueba
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

    public String getTitulo() {
        return titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public int getPeso() {
        return peso;
    }
    
    
    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
     public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public void setTitulo(String nombrePrueba) {
        this.titulo = nombrePrueba;
    }

    public void setFecha(String fechaPrueba) {
        this.fecha = fechaPrueba;
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
