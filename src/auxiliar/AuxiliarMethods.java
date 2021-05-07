/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package auxiliar;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

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
        System.out.println("Imprimiendo lista...");
        for (i=0; i<list.size(); i++){
            System.out.print(i+" ");
        }
    }
    
    public static void printArrayList(ArrayList array){
        int i;
        System.out.println("Imprimiendo arraylist...");
        for (i=0; i<array.size(); i++){
            System.out.print(i+" ");
        }
    }
    
    public static void ajustarColumnasTabla(JTable tabla) {
        final TableColumnModel columnModel = tabla.getColumnModel();
        for (int column = 0; column < tabla.getColumnCount(); column++) {
            int width = 25; // Min width
            for (int row = 0; row < tabla.getRowCount(); row++) {
                TableCellRenderer renderer = tabla.getCellRenderer(row, column);
                Component comp = tabla.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) {
                width = 300;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    
    public static void showWarning(String warning){
        JOptionPane.showMessageDialog(null, warning);
    }
    
}
