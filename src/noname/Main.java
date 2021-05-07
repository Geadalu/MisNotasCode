/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package noname;

import appinterface.MainWindow;
import appsmallinterfaces.LoginWindow;
import objects.Maestro;

public class Main {

    public static void main (String [] args){

        MainWindow mw = new MainWindow(new Maestro(1, "1234"), 16);
        mw.pack();
        mw.setVisible(true);
        mw.setMinimumSize(mw.getSize());

//          LoginWindow lw = new LoginWindow();
//          lw.pack();
//          lw.setVisible(true);
//          lw.setMinimumSize(lw.getSize());

    }
}