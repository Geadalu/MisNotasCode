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
    private HashMap<Integer, ArrayList<Double>> notasFinales; //asignatura && notas de los trimestres 1, 2, 3 y final
    private int posicion; //posicion en la array de contAlumnos

    

    public Alumno(int idAlumno, String nombre, String apellidos, String dni, int idCurso, String fechaNacimiento) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.idCurso = idCurso;
        this.fechaNacimiento = fechaNacimiento;
        this.notas = new HashMap<>(); //idPrueba, nota
        this.notasFinales = new HashMap<>(); //asignatura && notas de los trimestres 1, 2, 3 y final
    }

    public Alumno() {
        this.notas = new HashMap<>();
        this.notasFinales = new HashMap<>();
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
        this.idAlumno = idAlumno;
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
            
            this.notasFinales.put(resultNotaFinal.getInt("idAsignatura"), array);
        }
    }
    
    public void commitNuevoAlumno() throws SQLException {
        String sqlAlumno = "INSERT INTO alumno (nombre, apellidos, dni, idCurso, fechaNacimiento) VALUES ('"
                + this.nombre + "', '"
                + this.apellidos + "', '"
                + this.dni + "', '"
                + this.idCurso + "', '"
                + this.fechaNacimiento + "'"
                + ")";

        Statement st = DBConnection.getConnection().createStatement();
        st.executeUpdate(sqlAlumno);

    }
    
    public void updateNotas(Alumno alumno, int asignatura) throws SQLException {
        String sqlNotas = "UPDATE notafinal SET "
                + "idAsignatura = " + asignatura + ", "
                + "notaTrimestre1 = " + alumno.getNotasFinales().get(1) + ", "
                + "notaTrimestre2 = " + alumno.getNotasFinales().get(2) + ", "
                + "notaTrimestre3 = " + alumno.getNotasFinales().get(3) + ", "
                + "notaFinal = " + alumno.getNotasFinales().get(4)
                + "WHERE idAlumno = " + alumno.getIdAlumno()+"'";

        Statement st = DBConnection.getConnection().createStatement();
        st.executeUpdate(sqlNotas);
    }
    
    public int getIdAlumno() {
        return idAlumno;
    }
    
    public String getNombre() {
        return nombre;
    }
    
     public String getApellidos() {
        return apellidos;
    }
    
    public String getDni() {
        return dni;
    }
    
    public int getIdCurso() {
        return idCurso;
    }
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public HashMap<Integer, Double> getNotas() {
        return notas;
    }
    
      public HashMap<Integer, ArrayList<Double>> getNotasFinales() {
        return notasFinales;
    }
    
    public int getPosicion() {
        return posicion;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setNotas(HashMap<Integer, Double> notas) {
        this.notas = notas;
    }
    
     public void setNotasFinales(HashMap<Integer, ArrayList<Double>> notaFinal) {
        this.notasFinales = notaFinal;
    }
    
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
}
