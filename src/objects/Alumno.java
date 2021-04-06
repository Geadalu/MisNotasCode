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
public class Alumno {

    private int idAlumno;
    private String nombre;
    private String apellidos;
    private String dni;
    private int idCurso;
    private String fechaNacimiento;
    private HashMap<Integer, Double> notas;
    private HashMap<Integer, ArrayList<Double>> notaFinal;

    public Alumno(int idAlumno, String nombre, String apellidos, String dni, int idCurso, String fechaNacimiento) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.idCurso = idCurso;
        this.fechaNacimiento = fechaNacimiento;
        this.notas = new HashMap<>(); //idPrueba, nota
        this.notaFinal = new HashMap<>();
    }

    public Alumno() {
        this.notas = new HashMap<>();
        this.notaFinal = new HashMap<>();
    }

    public void cargarAlumno(int idAlumno) throws SQLException {
        String sqlAlumnos = "SELECT * FROM alumno WHERE idAlumno = "+idAlumno;
         
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultAlumnos = st.executeQuery(sqlAlumnos);

        while (resultAlumnos.next()){
            this.nombre = resultAlumnos.getString("nombre");
            this.apellidos = resultAlumnos.getString("apellidos");
            this.dni = resultAlumnos.getString("dni");
            this.idCurso = resultAlumnos.getInt("idCurso");
            this.fechaNacimiento = resultAlumnos.getString("fechaNacimiento");
        }
        this.cargarNotas(idAlumno);
        this.cargarNotasFinales(idAlumno);
    }
    
    public void cargarNotas(int idAlumno) throws SQLException{
        String sqlNotas = "SELECT * FROM nota WHERE idAlumno = "+idAlumno;
         
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultNotas = st.executeQuery(sqlNotas);
        
        while(resultNotas.next()){
            this.notas.put(resultNotas.getInt("idPrueba"), resultNotas.getDouble("nota"));
        }
    }
    
    public void cargarNotasFinales(int idAlumno) throws SQLException{
        String sqlNotaFinal = "SELECT * FROM notafinal WHERE idAlumno = "+idAlumno;
        
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultNotaFinal = st.executeQuery(sqlNotaFinal);

        while(resultNotaFinal.next()){
            ArrayList<Double> array = new ArrayList<>();
            array.add(resultNotaFinal.getDouble("notaTrimestre1"));
            array.add(resultNotaFinal.getDouble("notaTrimestre2"));
            array.add(resultNotaFinal.getDouble("notaTrimestre3"));
            array.add(resultNotaFinal.getDouble("notaFinal"));
            
            this.notaFinal.put(resultNotaFinal.getInt("idAsignatura"), array);
        }
    }
    
    public void commitAlumno(Alumno alumno, int curso){
        String sqlAlumno = "INSERT INTO alumno (nombre, apellidos, dni, idCurso, fechaNacimiento) VALUES ('"
                +alumno.getNombre()+"', '"
                +alumno.getApellidos()+"', '"
                +alumno.getDni()+"', '"
                +curso+"', '"
                +alumno.getFechaNacimiento()+"'"
                + ")";
            try {
            Statement st = DBConnection.getConnection().createStatement();
            st.executeUpdate(sqlAlumno );
            } catch (Exception e){
                System.out.println("commitAlumno dice: "+e.toString());
            }
    }
    
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public HashMap<Integer, Double> getNotas() {
        return notas;
    }

    public void setNotas(HashMap<Integer, Double> notas) {
        this.notas = notas;
    }

    public HashMap<Integer, ArrayList<Double>> getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(HashMap<Integer, ArrayList<Double>> notaFinal) {
        this.notaFinal = notaFinal;
    }

}
