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
import objects.Alumno;
import objects.Nota;

/**
 * Controlador para los alumnos
 * @author lucia
 */
public class ControladorAlumno {

    private HashMap<Integer, ArrayList<Alumno>> alumnosAsignatura; //asignatura, alumnos

    public ControladorAlumno() throws SQLException{
        this.alumnosAsignatura = new HashMap<>();
        inicializarAlumnosAsignatura();
    }
    
    /**
     * Método que pobla el hashmap alumnosAsignatura con las asignaturas de la bd
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

    /**
     * Carga todos los alumnos de una asignatura
     * @param idAsignatura
     * @throws SQLException 
     */
    public void cargarAlumnosAsignatura(int idAsignatura) throws SQLException {
        int posicion = 0;
        if (this.alumnosAsignatura.get(idAsignatura).isEmpty()) {
            String alumnos = "SELECT t1.idAlumno FROM alumno t1, alumnosporasignatura t2 WHERE t1.idAlumno = t2.idAlumno AND t2.idAsignatura = "+idAsignatura;
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet resultAlumnos = st.executeQuery(alumnos);

            while (resultAlumnos.next()) {
                int idAlumno = resultAlumnos.getInt("idAlumno");
                Alumno a = new Alumno();
                a.cargarAlumno(idAlumno);
                a.setPosicion(posicion);
                this.alumnosAsignatura.get(idAsignatura).add(a);
                posicion++;
            }
        }
    }

    /**
     * Updatea las notas finales en la base de datos de un alumno para una asignatura
     * @param alumno
     * @param asignatura
     * @throws SQLException
     * @throws NullPointerException 
     */
    public void updateNotasFinales(Alumno alumno, int asignatura) throws SQLException, NullPointerException {
        Statement st = DBConnection.getConnection().createStatement();
        boolean update = false;
        
        //Comprobamos que no haya ya una fila en la base de datos primero
        String select = "SELECT * FROM notafinal WHERE idAlumno = "+alumno.getIdAlumno()+" AND idAsignatura = "+asignatura;
        ResultSet resultAlumnos = st.executeQuery(select);
        if(resultAlumnos.next()){
            update = true; //hay fila, entonces es update
        }
        
        if(update){
            String sqlNotas = "UPDATE notafinal SET "
                    + "idAsignatura = " + asignatura + ", "
                    + "notaTrimestre1 = " + alumno.getNotasFinales().get(asignatura).get(0) + ", "
                    + "notaTrimestre2 = " + alumno.getNotasFinales().get(asignatura).get(1) + ", "
                    + "notaTrimestre3 = " + alumno.getNotasFinales().get(asignatura).get(2) + ", "
                    + "notaFinal = " + alumno.getNotasFinales().get(asignatura).get(3) +", "
                    + "comentario = '" + alumno.getComentariosAsignaturas().get(asignatura) + "' "
                    + " WHERE idAlumno = " + alumno.getIdAlumno();
            st.executeUpdate(sqlNotas);
            
        } else {
            
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
    
    /**
     * Updatea las notas en la base de datos de un alumno para una asignatura
     * @param idAsignatura
     * @param idPrueba
     * @param alumno
     * @param update
     * @throws SQLException 
     */
    public void updateNotas(int idAsignatura, int idPrueba, Alumno alumno, boolean update) throws SQLException {
        Statement st = DBConnection.getConnection().createStatement();
        for (Nota n : alumno.getNotas()) {
            if (n.getIdPrueba() == idPrueba && n.getNota() != -1) {
                if (update) {
                    String sqlNotas = "UPDATE nota SET"
                           // + "idPrueba = " + idPrueba + ", "
                            + " nota = " + n.getNota()
                            + ", comentario = '" + n.getComentario()
                            + "' WHERE idAlumno = " + alumno.getIdAlumno() 
                            + " AND idPrueba = " +idPrueba;
                    st.executeUpdate(sqlNotas);
                } else {
                    //Si no quiere meterlo como UPDATE, es que tiene que ser INSERT
                    String sqlNotasInsert = "INSERT INTO nota (idAlumno, idPrueba, nota, comentario) VALUES ("
                            + alumno.getIdAlumno() + ", "
                            + idPrueba + ", "
                            + n.getNota() + ", '"
                            + n.getComentario() + "')";

                    st.executeUpdate(sqlNotasInsert);
                }
            }
        }
    }
    
    
    /**
     * Añade internamente un alumno a una asignatura
     * @param alumno
     * @param idAsignatura 
     */
    public void añadirAlumnoAAsignatura(Alumno alumno, int idAsignatura){
        this.alumnosAsignatura.get(idAsignatura).add(alumno);
    }
    
    /**
     * Añade internamente un alumno a todas las asignaturas de un curso
     * @param alumno
     * @param idAsignatura
     * @throws SQLException 
     */
     public void añadirAlumnoACurso(Alumno alumno, int idAsignatura) throws SQLException {
        Statement st = DBConnection.getConnection().createStatement();
        String curso = String.valueOf(idAsignatura).substring(1);
        String buscarAsignaturas = "SELECT idAsignatura FROM asignatura WHERE idAsignatura LIKE '%"+curso+"' AND optativa = 0";
        ResultSet resultAsignaturas = st.executeQuery(buscarAsignaturas);
        
        while (resultAsignaturas.next()) {
            this.alumnosAsignatura.get(resultAsignaturas.getInt("idAsignatura")).add(alumno);
        }
    }
    
    /**
     * Commit de la base de datos para asignar un alumno a una sola asignatura. Se usa para las optativas.
     * @param idAsignatura
     * @param alumno
     * @throws SQLException 
     */
    public void commitAlumnoAsignatura(Alumno alumno, int idAsignatura) throws SQLException {
        String sqlAlumno = "INSERT INTO alumnosporasignatura (idAlumno, idAsignatura) VALUES ('"
                +alumno.getIdAlumno()+ "', '"
                +idAsignatura+"')";
        
        Statement st = DBConnection.getConnection().createStatement();
        st.executeUpdate(sqlAlumno);
    }
    
    /**
     * Commit de la base de datos para asignar un alumno a todas las asignaturas de un curso
     * @param alumno
     * @param idAsignatura
     * @throws SQLException 
     */
    public void commitAlumnoCurso(Alumno alumno, int idAsignatura) throws SQLException {
        Statement st = DBConnection.getConnection().createStatement();
        String curso = String.valueOf(idAsignatura).substring(1);
        String buscarAsignaturas = "SELECT idAsignatura FROM asignatura WHERE idAsignatura LIKE '%"+curso+"' AND optativa = 0";
        ResultSet resultAsignaturas = st.executeQuery(buscarAsignaturas);
        
        while (resultAsignaturas.next()) {
            String sqlAlumno = "INSERT INTO alumnosporasignatura (idAlumno, idAsignatura) VALUES ('"
                +alumno.getIdAlumno()+ "', '"
                +resultAsignaturas.getInt("idAsignatura")+"')";
            st = DBConnection.getConnection().createStatement();
            st.executeUpdate(sqlAlumno);
        }
    }
    
    /**
     * Busca en la base de datos para saber si una asignatura es optativa o troncal
     * @param idAsignatura
     * @return
     * @throws SQLException 
     */
    public boolean esOptativa(int idAsignatura) throws SQLException {
        Statement st = DBConnection.getConnection().createStatement();
        String optativa = "SELECT optativa FROM asignatura WHERE idAsignatura = "+idAsignatura;
        ResultSet resultOptativa = st.executeQuery(optativa);
        if(resultOptativa.next()) {
            return resultOptativa.getInt("optativa") == 1;
        }
        return false;
    } 
    
    public HashMap<Integer, ArrayList<Alumno>> getAlumnosAsignatura() {
        return alumnosAsignatura;
    }
}
