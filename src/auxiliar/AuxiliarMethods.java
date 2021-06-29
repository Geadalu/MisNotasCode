/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package auxiliar;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author lucia
 */
public class AuxiliarMethods {

    //****************************************************
    //      MÉTODOS PARA PRINTEAR ESTRUCTURAS DE DATOS
    //****************************************************
    
    public static List<String> stringToList(Object obj) {
        String cadena = obj.toString();
        cadena = cadena.substring(1, cadena.length() - 1);
        System.out.println("cadena = " + cadena);

        String str[] = cadena.split(",");
        List<String> list = new ArrayList<String>();
        list = Arrays.asList(str);
        for (String s : list) {
            System.out.println(s);
        }
        return list;
    }

    public static void printArray(Object[] array) {
        int i;

        System.out.println("Voy a imprimir una array...");
        for (i = 0; i < array.length; i++) {
            System.out.print(array[i].toString() + " ");
        }
    }

    public static void printLista(List<?> list) {
        int i;
        System.out.println("Imprimiendo lista...");
        for (i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void printArrayList(ArrayList array) {
        int i;
        System.out.println("Imprimiendo arraylist...");
        for (i = 0; i < array.size(); i++) {
            System.out.print(array.get(i) + " ");
        }
    }

    public static void printHashMap(HashMap hashmap) {
        System.out.println(Arrays.asList(hashmap));
    }
    
    //****************************************************
    //      OTROS MÉTODOS USADOS PARA DEBUGGEAR
    //****************************************************

    public static double sumarElementosLista(List<?> list) {
        int i;
        double result = 0.0;

        for (i = 0; i < list.size(); i++) {
            result += Double.valueOf(list.get(i).toString());
        }
        System.out.println("result = " + result);
        return result;
    }
    
    //****************************************************
    //      MÉTODOS COMUNES DE FUNCIONES VARIAS
    //****************************************************

    /**
     * Ajusta el ancho de las columnas de una tabla al contenido de la misma
     *
     * @param tabla
     */
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
    
    /**
     * Método que comprueba si una array está llena de ceros.
     * @param array
     * @return 
     */
    public static boolean arrayTodoCeros(ArrayList<Double> array){
        for (int i=0; i<array.size(); i++){
            if (array.get(i) != 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Muestra un warning por pantalla
     *
     * @param warning
     */
    public static void showWarning(String warning) {
        JOptionPane.showMessageDialog(null, warning);
    }

    /**
     * Mete en una lista todos los componentes de un container
     * @param c
     * @return la lista con los componentes
     */
    public static List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }
        return compList;
    }

    /**
     * Método que ajusta un frame al centro de la pantalla
     *
     * @param frame
     */
    public static void centrarVentana(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

}
