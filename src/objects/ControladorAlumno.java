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
public class ControladorAlumno {

    private HashMap<Integer, ArrayList<Alumno>> alumnosCurso;

    public ControladorAlumno() {
        this.alumnosCurso = new HashMap<>();
        this.alumnosCurso.put(1, new ArrayList<>());
        this.alumnosCurso.put(2, new ArrayList<>());
        this.alumnosCurso.put(3, new ArrayList<>());
        this.alumnosCurso.put(4, new ArrayList<>());
    }

    public void cargarAlumnosCurso(int curso) throws SQLException {
        int posicion = 0;
        if (this.alumnosCurso.get(curso).isEmpty()) {
            String alumnos = "SELECT idAlumno, apellidos FROM alumno WHERE idCurso = " + curso + " ORDER BY apellidos ASC";
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet resultAlumnos = st.executeQuery(alumnos);

            while (resultAlumnos.next()) {
                int idAlumno = resultAlumnos.getInt("idAlumno");
                Alumno a = new Alumno();
                a.cargarAlumno(idAlumno);
                a.setPosicion(posicion);
                this.alumnosCurso.get(curso).add(a);
                posicion++;
            }
        }
    }
    
    public void añadirAlumnoACurso(Alumno alumno, int curso){
        this.alumnosCurso.get(curso).add(alumno);
    }
    
    public HashMap<Integer, ArrayList<Alumno>> getAlumnosCurso() {
        return alumnosCurso;
    }
    
    public boolean apellidoRepetido(Alumno alumno1, int curso){
        for (Alumno alumno2 : this.alumnosCurso.get(curso)){
            if (alumno1.getApellidos().equals(alumno2.getApellidos())){
                return true;
            }
        }
        return false;
    }
    
    

}
