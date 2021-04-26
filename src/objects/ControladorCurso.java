/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package objects;

import auxiliar.AuxiliarMethods;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import noname.DBConnection;

/**
 *
 * @author lucia
 */
public class ControladorCurso {
    
    private final HashMap<String, Integer> correspLetras;
    private final HashMap<Integer, String> cursos;

    
    public ControladorCurso(){
        this.cursos = new HashMap<>();
        this.correspLetras = new HashMap<>();
        rellenarLetras();
    }
    
    private void rellenarLetras(){
        correspLetras.put("A", 1);
        correspLetras.put("B", 2);
        correspLetras.put("C", 3);
        correspLetras.put("D", 4);
        correspLetras.put("E", 5);
        correspLetras.put("F", 6);
        correspLetras.put("G", 7);
        correspLetras.put("H", 8);
        correspLetras.put("I", 9);
    }
    
    public void cargarCursos() throws SQLException {
        if (this.cursos.isEmpty()) {
            String sqlCursos = "SELECT * FROM curso";
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet resultCursos = st.executeQuery(sqlCursos);

            while (resultCursos.next()) {
                cursos.put(resultCursos.getInt("idCurso"), resultCursos.getString("nombreCurso"));
            }
        }
    }

    public void commitNuevoCurso(String nombreCurso){
        String letraCurso = String.valueOf(nombreCurso.charAt(2));
        String numCurso = String.valueOf(nombreCurso.charAt(0));
        String curso = numCurso+correspLetras.get(letraCurso);
         String sqlCurso = "INSERT INTO curso (idCurso, nombreCurso) VALUES ('" 
                + curso +"', '"
                + nombreCurso +"'"
                + ")";
        try {
            Statement st = DBConnection.getConnection().createStatement();
            st.executeUpdate(sqlCurso);
        } catch (SQLIntegrityConstraintViolationException e) {
            AuxiliarMethods.showWarning("Ese curso ya existe.");
        } catch (SQLException e){
            AuxiliarMethods.showWarning("Letra no válida. Consulte con un administrador.\nLetras válidas: A->I");
        }
    }
    
    public HashMap<Integer, String> getCursos() {
        return cursos;
    }
    
}
