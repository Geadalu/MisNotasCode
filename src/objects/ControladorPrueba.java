/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package objects;

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
public class ControladorPrueba {
    
     private HashMap<Integer, ArrayList<Prueba>> pruebasAsignatura; //idAsignatura, lista de pruebas
     
     public ControladorPrueba() {
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
    
    public void añadirPruebaAAsignatura(Prueba prueba, int asignatura){
        this.pruebasAsignatura.get(asignatura).add(prueba);
    }

    public HashMap<Integer, ArrayList<Prueba>> getPruebasAsignatura() {
        return pruebasAsignatura;
    }
    
}
