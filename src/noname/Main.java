package noname;

import appinterface.MainWindow;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main (String [] args){

        MainWindow mw = new MainWindow();
        mw.pack();
        mw.setVisible(true);
        mw.setMinimumSize(mw.getSize());

    }
}