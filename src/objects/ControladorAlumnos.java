/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class ControladorAlumnos {

    private HashMap<Integer, ArrayList<Alumno>> alumnosCurso;

    public ControladorAlumnos() {
        this.alumnosCurso = new HashMap<>();
        this.alumnosCurso.put(1, new ArrayList<>());
        this.alumnosCurso.put(2, new ArrayList<>());
        this.alumnosCurso.put(3, new ArrayList<>());
        this.alumnosCurso.put(4, new ArrayList<>());
    }

    public void cargarAlumnosCurso(int curso) throws SQLException {
        if (this.alumnosCurso.get(curso).isEmpty()) {
            String alumnos = "SELECT idAlumno FROM alumno WHERE idCurso = " + curso;
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet resultAlumnos = st.executeQuery(alumnos);

            while (resultAlumnos.next()) {
                int idAlumno = resultAlumnos.getInt("idAlumno");
                Alumno a = new Alumno();
                a.cargarAlumno(idAlumno);
                this.alumnosCurso.get(curso).add(a);
            }
        }

    }

    public HashMap<Integer, ArrayList<Alumno>> getAlumnosCurso() {
        return alumnosCurso;
    }

}
