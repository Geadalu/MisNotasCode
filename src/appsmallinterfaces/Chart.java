/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package appsmallinterfaces;

import java.util.HashMap;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;



public class Chart {
    
    JFreeChart chart;
    
    public Chart(String tituloChart, HashMap<String, Float> datos, JPanel panel) {
        PieDataset dataset = createDataset(datos);
        chart = createChart(dataset, tituloChart);
    }
    
    private PieDataset createDataset(HashMap<String, Float> datos){
        DefaultPieDataset result = new DefaultPieDataset();
        for (String key : datos.keySet()){
            result.setValue(key, datos.get(key));
        }
        return result;
    }
    
    private JFreeChart createChart(PieDataset dataset, String title){
        JFreeChart chart2 = ChartFactory.createPieChart(title, dataset, true, false, false);
        
        Plot plot = chart2.getPlot();
        plot.setForegroundAlpha(0.5f);
        return chart2;
        
    }
    
    public void paint(JPanel panel){
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(200, 120));
        panel.add(chartPanel);
    }
    
}
