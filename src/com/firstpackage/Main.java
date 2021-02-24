package com.firstpackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main (String [] args){


        try {
            //Traductor de SQL a ArrayList
            ArrayList<HashMap<String,Object>> array = DBQuery.SQLtoArray("SELECT * FROM alumno");

            if (!DBConnection.closeConnection()){
                System.out.println("Uy! No he podido cerrar la conexión con la DB.\nError en: Main/main");
            }

            for(int i = 0; i < array.size(); i++) {
                System.out.print(array.get(i));
            }

        } catch (Exception ex){
            System.out.println("Uy... Algo ha pasado aquí, en mitad de Main/main: \n"+ex);
        }
    }
}