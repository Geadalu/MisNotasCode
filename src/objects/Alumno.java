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
    private String fechaNacimiento;
    private String comentario; //este comentario está en la tabls notafinal, porque depende de la asignatura
    private ArrayList<Nota> notas;
    private HashMap<Integer, ArrayList<Double>> notasFinales; //asignatura && notas de los trimestres 1, 2, 3 y final
    private HashMap<Integer, String> comentariosAsignaturas; //idAsignatura, comentario
    private int posicion; //posicion en la array de contAlumnos
      

    public Alumno(int idAlumno, String nombre, String apellidos, String dni, String fechaNacimiento) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.comentario = "";
        this.notas = new ArrayList<Nota>(); //idPrueba, nota
        this.notasFinales = new HashMap<>(); //asignatura && notas de los trimestres 1, 2, 3 y final
        this.comentariosAsignaturas = new HashMap<>(); //idAsignatura, comentario
    }

    public Alumno() {
        this.notas = new ArrayList<>();
        this.notasFinales = new HashMap<>();
        this.comentariosAsignaturas = new HashMap<>();
    }

    public void cargarAlumno(int idAlumno) throws SQLException {
        String sqlAlumnos = "SELECT * FROM alumno WHERE idAlumno = "+idAlumno;
         
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultAlumnos = st.executeQuery(sqlAlumnos);

        while (resultAlumnos.next()){
            this.nombre = resultAlumnos.getString("nombre");
            this.apellidos = resultAlumnos.getString("apellidos");
            this.dni = resultAlumnos.getString("dni");
            this.fechaNacimiento = resultAlumnos.getString("fechaNacimiento");
        }
        this.idAlumno = idAlumno;
        this.cargarNotas();
        this.cargarNotasFinales();
        this.cargarComentariosAsignaturas();
    }
    
    public void cargarNotas() throws SQLException{
        String sqlNotas = "SELECT * FROM nota WHERE idAlumno = "+idAlumno;
         
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultNotas = st.executeQuery(sqlNotas);
        
        while(resultNotas.next()){
            this.notas.add(new Nota(resultNotas.getInt("idPrueba"), resultNotas.getDouble("nota"), resultNotas.getString("comentario")));
        }
    }
    
    public void cargarNotasFinales() throws SQLException{
        String sqlNotaFinal = "SELECT * FROM notafinal WHERE idAlumno = "+idAlumno;
        
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultNotaFinal = st.executeQuery(sqlNotaFinal);

        while(resultNotaFinal.next()){
            ArrayList<Double> array = new ArrayList<>();
            array.add(resultNotaFinal.getDouble("notaTrimestre1"));
            array.add(resultNotaFinal.getDouble("notaTrimestre2"));
            array.add(resultNotaFinal.getDouble("notaTrimestre3"));
            array.add(resultNotaFinal.getDouble("notaFinal"));
            this.comentario = resultNotaFinal.getString("comentario");
            
            this.notasFinales.put(resultNotaFinal.getInt("idAsignatura"), array);
        }
    }
    
    
    public void cargarComentariosAsignaturas() throws SQLException{
        String sqlComentarios = "SELECT * FROM notafinal WHERE idAlumno = "+idAlumno+";";
        
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet resultComentarios = st.executeQuery(sqlComentarios);

        while(resultComentarios.next()){
            this.comentariosAsignaturas.put(resultComentarios.getInt("idAsignatura"), resultComentarios.getString("comentario"));
        }
    }
    
    /**
     * Commit a la base de datos de un alumno nuevo a la tabla alumno
     * @return el idAlumno del nuevo alumno
     * @throws SQLException 
     */
    public int commitNuevoAlumno() throws SQLException {
        String sqlAlumno = "INSERT INTO alumno (nombre, apellidos, dni, fechaNacimiento) VALUES ('"
                + this.nombre + "', '"
                + this.apellidos + "', '"
                + this.dni + "', '"
                + this.fechaNacimiento + "'"
                + ")";

        Statement st = DBConnection.getConnection().createStatement();
        st.executeUpdate(sqlAlumno);
        
        sqlAlumno = "SELECT idAlumno FROM alumno WHERE dni = '"+this.dni+"'";
        st = DBConnection.getConnection().createStatement();
        ResultSet resultidAlumno = st.executeQuery(sqlAlumno);
        
        if (resultidAlumno.next()){
            return resultidAlumno.getInt("idAlumno");
        } else {
            return 0;
        }
       
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public ArrayList<Nota> getNotas() {
        return notas;
    }
    
    public HashMap<Integer, ArrayList<Double>> getNotasFinales() {
        return notasFinales;
    }
    
    public int getPosicion() {
        return posicion;
    }
    
    public HashMap<Integer, String> getComentariosAsignaturas() {
        return comentariosAsignaturas;
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

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setNotas(ArrayList<Nota> notas) {
        this.notas = notas;
    }
    
     public void setNotasFinales(HashMap<Integer, ArrayList<Double>> notaFinal) {
        this.notasFinales = notaFinal;
    }
    
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    
    
}
