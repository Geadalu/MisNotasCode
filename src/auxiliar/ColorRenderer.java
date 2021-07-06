/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package auxiliar;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import objects.Opciones;

/**
 * Clase para colorear la tabla de InformeTrimestreWindow. Sacada de https://stackoverflow.com/ y modificada acorde
 * @author lucia
 */
public class ColorRenderer extends DefaultTableCellRenderer {
    
    Opciones opciones;
    
    /**
     * Constructor. Necesita las opciones para el color
     * @param opciones 
     */
    public ColorRenderer(Opciones opciones){
        this.opciones = opciones;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable tabla, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(tabla, value, isSelected, hasFocus, row, column);
        
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();

        if (!isSelected && !hasFocus){
            try {
                if (!model.getValueAt(row, column).toString().equals("-") && Double.parseDouble(model.getValueAt(row, column).toString()) < 5.0) {
                    setForeground(opciones.getColorSuspensos());
                } else {
                    setForeground(opciones.getColorAprobados());
                }
            } catch (NullPointerException npe){
                
            }
                
        }
        return this;
    }
}
