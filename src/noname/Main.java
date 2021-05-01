/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package noname;

import appinterface.MainWindow;
import appsmallinterfaces.LoginWindow;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main (String [] args){

//        MainWindow mw = new MainWindow(1, 0);
//        mw.pack();
//        mw.setVisible(true);
//        mw.setMinimumSize(mw.getSize());

          LoginWindow lw = new LoginWindow();
          lw.pack();
          lw.setVisible(true);
          lw.setMinimumSize(lw.getSize());

    }
}