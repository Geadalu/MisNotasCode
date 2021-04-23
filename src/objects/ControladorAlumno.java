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

    private HashMap<Integer, ArrayList<Alumno>> alumnosAsignatura; //asignatura, alumnos

    public ControladorAlumno() throws SQLException{
        this.alumnosAsignatura = new HashMap<>();
        inicializarAlumnosAsignatura();
    }
    
    /**
     * Método que pobla el hashmap alumnosAsignatura
     */
    private void inicializarAlumnosAsignatura() throws SQLException{
        String asignaturas = "SELECT idAsignatura FROM asignatura";
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultAsignaturas;
        resultAsignaturas = st.executeQuery(asignaturas);
        
        while (resultAsignaturas.next()) {
            alumnosAsignatura.put(resultAsignaturas.getInt("idAsignatura"), new ArrayList<>());
        }
    }

    public void cargarAlumnosAsignatura(int asignatura) throws SQLException {
        int posicion = 0;
        if (this.alumnosAsignatura.get(asignatura).isEmpty()) {
            String alumnos = "SELECT t1.idAlumno FROM alumno t1, alumnosporasignatura t2 WHERE t1.idAlumno = t2.idAlumno AND t2.idAsignatura = "+asignatura;
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet resultAlumnos = st.executeQuery(alumnos);

            while (resultAlumnos.next()) {
                int idAlumno = resultAlumnos.getInt("idAlumno");
                Alumno a = new Alumno();
                a.cargarAlumno(idAlumno);
                a.setPosicion(posicion);
                this.alumnosAsignatura.get(asignatura).add(a);
                posicion++;
            }
        }
    }
    
    public void añadirAlumnoAAsignatura(Alumno alumno, int asignatura){
        this.alumnosAsignatura.get(asignatura).add(alumno);
    }
    
    public HashMap<Integer, ArrayList<Alumno>> getAlumnosAsignatura() {
        return alumnosAsignatura;
    }
    
    public void updateNotasFinales(int i, int asignatura) throws SQLException {
        Alumno alumno = this.getAlumnosAsignatura().get(asignatura).get(i);
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
    
    public void updateNotas(int asignatura, int idPrueba, int i, boolean update) throws SQLException {
        Alumno alumno = this.getAlumnosAsignatura().get(asignatura).get(i);
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
