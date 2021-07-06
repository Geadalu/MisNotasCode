/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package mainpackage;

import appsmallinterfaces.LoginWindow;

/**
 * Clase desde la que se corre el login
 * @author lucia
 */
public class Main {

    public static void main (String [] args){

          LoginWindow lw = new LoginWindow();
          lw.pack();
          lw.setVisible(true);
          lw.setMinimumSize(lw.getSize());

    }
}