package src;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;

/**
 * BarChartFrame - a frame intentionally created to show subject's outcomes over times.
 * @author sonng
 *
 */
class BarChartFrame extends JFrame {
    
    public BarChartFrame(JFreeChart chart) {
        super("Subject Outcomes Statistics");
        ChartPanel chartPane = new ChartPanel(chart);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(255, 255, 250));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.black);
        plot.setOutlineVisible(false);
        ((BarRenderer)plot.getRenderer()).setBarPainter(new StandardBarPainter());
        plot.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        plot.getRenderer().setBaseItemLabelsVisible(true);            
        chartPane.setPreferredSize(new Dimension(750, 450));
        setContentPane(chartPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}