/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author lucia
 */
public class AuxiliarMethods {
    
    public static List<String> stringToList (Object obj){
        String cadena = obj.toString();
        cadena = cadena.substring(1, cadena.length()-1);
        System.out.println("cadena = "+cadena);
        
	String str[] = cadena.split(",");
	List<String> list = new ArrayList<String>();
	list = Arrays.asList(str);
	for(String s: list){
	   System.out.println(s);
	}
        return list;
   }
    
    public static double sumarElementosLista(List<?> list){
        int i;
        double result = 0.0;
        
        for (i=0; i<list.size(); i++){
            result += Double.valueOf(list.get(i).toString());
        }
        System.out.println("result = "+result);
        return result;
    }
    
    public static void printLista(List<?> list){
        int i;
        System.out.println("Printeando lista...");
        for (i=0; i<list.size(); i++){
            System.out.print(i+" ");
        }
    }
    
}
