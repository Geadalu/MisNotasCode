/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author lucia
 */
public class Alumno {
    int idAlumno;
    String nombre;
    String apellidos;
    String dni;
    int idCurso;
    String fechaNacimiento;
    
    public Alumno(int idAlumno, String nombre, String apellidos, String dni, int idCurso, String fechaNacimiento){
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.idCurso = idCurso;
        this.fechaNacimiento = fechaNacimiento;
    }
}
