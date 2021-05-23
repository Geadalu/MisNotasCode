/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package objects;

import java.awt.Color;

/**
 * Esta clase representa las opciones de apariencia de la aplicación, elegidas en el Login
 * @author lucia
 */
public class Opciones {
    int tamañoLetra;
    boolean oscuro;
    Color colorBackground;
    Color colorAprobados;
    Color colorSuspensos;    

    public Opciones(int tamañoLetra, boolean oscuro, Color colorBackground, Color colorAprobados, Color colorSuspensos) {
        this.tamañoLetra = tamañoLetra;
        this.oscuro = oscuro;
        this.colorBackground = colorBackground;
        this.colorAprobados = colorAprobados;
        this.colorSuspensos = colorSuspensos;
    }

    public int getTamañoLetra() {
        return tamañoLetra;
    }
    
    public boolean getOscuro() {
        return oscuro;
    }

    public Color getColorBackground() {
        return colorBackground;
    }

    public Color getColorAprobados() {
        return colorAprobados;
    }

    public Color getColorSuspensos() {
        return colorSuspensos;
    }
    
    
}
