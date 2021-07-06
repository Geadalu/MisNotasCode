/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package objects;

import java.util.HashMap;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


/**
 * Crea un diagrama de quesitos con un hashmap de datos y lo pinta en un panel
 * @author lucia
 */
public class Chart {
    
    JFreeChart chart;
    
    /**
     * Crea un nuevo chart
     * @param tituloChart
     * @param datos
     * @param panel 
     */
    public Chart(String tituloChart, HashMap<String, Float> datos, JPanel panel) {
        PieDataset dataset = createDataset(datos);
        chart = createChart(dataset, tituloChart);
    }
    
    /**
     * Inicializa el dataset y lo mete en el diagrama de quesitos
     * @param datos
     * @return 
     */
    private PieDataset createDataset(HashMap<String, Float> datos){
        DefaultPieDataset result = new DefaultPieDataset();
        for (String key : datos.keySet()){
            result.setValue(key, datos.get(key));
        }
        return result;
    }
    
    /**
     * Crea el chart 
     * @param dataset
     * @param title
     * @return 
     */
    private JFreeChart createChart(PieDataset dataset, String title){
        JFreeChart chart2 = ChartFactory.createPieChart(title, dataset, true, false, false);
        
        Plot plot = chart2.getPlot();
        plot.setForegroundAlpha(0.5f);
        return chart2;
        
    }
    
    /**
     * Pinta el diagrama en el panel que se le pasa
     * @param panel 
     */
    public void paint(JPanel panel){
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(200, 120));
        panel.add(chartPanel);
    }
    
}
