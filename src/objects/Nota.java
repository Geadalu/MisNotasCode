/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package objects;

/**
 * Clase nota
 * @author lucia
 */
public class Nota {
    int idPrueba;
    double nota;
    String comentario;

    public Nota(int idPrueba, double nota, String comentario) {
        this.idPrueba = idPrueba;
        this.nota = nota;
        this.comentario = comentario;
    }

    public int getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(int idPrueba) {
        this.idPrueba = idPrueba;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
}
