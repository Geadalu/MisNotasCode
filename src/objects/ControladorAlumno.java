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

    private HashMap<Integer, ArrayList<Alumno>> alumnosCurso; //curso, alumnos

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
    
    public void updateNotasFinales(int curso, int i, int asignatura) throws SQLException {
        Alumno alumno = this.getAlumnosCurso().get(curso).get(i);
        Statement st = DBConnection.getConnection().createStatement();
        String sqlNotas = "UPDATE notafinal SET "
                + "idAsignatura = " + asignatura + ", "
                + "notaTrimestre1 = " + alumno.getNotasFinales().get(asignatura).get(0) + ", "
                + "notaTrimestre2 = " + alumno.getNotasFinales().get(asignatura).get(1) + ", "
                + "notaTrimestre3 = " + alumno.getNotasFinales().get(asignatura).get(2) + ", "
                + "notaFinal = " + alumno.getNotasFinales().get(asignatura).get(3)
                + " WHERE idAlumno = " + alumno.getIdAlumno();

        if(st.executeUpdate(sqlNotas) == 0){
            //Si no quiere meterlo como UPDATE, es que tiene que ser INSERT
            String sqlNotasInsert = "INSERT INTO notafinal (idAlumno, idAsignatura, notaTrimestre1, notaTrimestre2, notaTrimestre3, notaFinal) VALUES ("
                + alumno.getIdAlumno() +", "
                + asignatura + ", "
                + alumno.getNotasFinales().get(asignatura).get(0) + ", "
                + alumno.getNotasFinales().get(asignatura).get(1) + ", "
                + alumno.getNotasFinales().get(asignatura).get(2) + ", "
                + alumno.getNotasFinales().get(asignatura).get(3) +")";
            st.executeUpdate(sqlNotasInsert);
        }
    }
    
    public void updateNotas(int asignatura, int idPrueba, int i, int curso, boolean update) throws SQLException {
        Alumno alumno = this.getAlumnosCurso().get(curso).get(i);
        Statement st = DBConnection.getConnection().createStatement();
        if (alumno.getNotas().get(idPrueba) != null){
            if (update){
            String sqlNotas = "UPDATE nota SET "
                    + "idPrueba = " + idPrueba + ", "
                    + "nota = " + alumno.getNotas().get(idPrueba)
                    + " WHERE idAlumno = " + alumno.getIdAlumno();
            } else { 
                //Si no quiere meterlo como UPDATE, es que tiene que ser INSERT
                String sqlNotasInsert = "INSERT INTO nota (idAlumno, idPrueba, nota) VALUES ("
                    + alumno.getIdAlumno() +", "
                    + idPrueba + ", "
                    + alumno.getNotas().get(idPrueba) +")";
               
                st.executeUpdate(sqlNotasInsert);
            }
        }
    }
    


}
