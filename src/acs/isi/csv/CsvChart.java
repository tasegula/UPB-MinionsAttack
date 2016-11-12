package acs.isi.csv;

import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import acs.isi.minions.LayerWrapperEnum;
import acs.isi.util.QueryBuilder;
import acs.isi.util.QueryBuilder.MinMax;

import com.esri.core.map.TimeExtent;

public class CsvChart extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private static List<CsvChartType> magData;
	private static List<CsvChartType> humanData;
	private static List<CsvChartType> moneyData;

	String title;
	String xLabel;
	String yLabel;
	
	CategoryDataset dataset;
	JFreeChart chart;
	ChartPanel chartPanel;
	
	CategoryPlot magPlot = null;
	CategoryPlot humanPlot = null;
	CategoryPlot moneyPlot = null;
	
    private CsvChart(LayerWrapperEnum layerType, QueryBuilder query, TimeExtent interval) {
		this.title = layerType.title();
		this.yLabel = "Value";
		this.xLabel = layerType.name();
		
		switch (layerType) {
		case MAGNITUDE:
			initMag(query, interval); break;
		case HUMAN:
			initHuman(query, interval); break;
		case MONEY:
			initMoney(query, interval); break;
		default:
			initMag(query, interval);
			initHuman(query, interval);
			initMoney(query, interval); 
			break;
		}

        chart = getChart(magPlot, humanPlot, moneyPlot);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
	}
    
    // ------------------------------------------------------------------------
    // INIT CODE
    // ------------------------------------------------------------------------
    private void initMag(QueryBuilder query, TimeExtent interval) {		
    	String label = "Number of Earthquakes in each category";
    	
		if (magData == null) {
			magData = CsvChartUtils.getDataForOneLayer(LayerWrapperEnum.MAGNITUDE.csvPath());
		}
		dataset = getDatasetByQuery(magData, label, query.getMagnitudeRange(), interval);
		magPlot = getPlot(dataset);
    }
    
    private void initHuman(QueryBuilder query, TimeExtent interval) {
    	String label = "Number of Casualties in each category";
    	
		if (humanData == null) {
			humanData = CsvChartUtils.getDataForOneLayer(LayerWrapperEnum.HUMAN.csvPath());
		}
		dataset = getDatasetByQuery(humanData, label, query.getHumanRange(), interval); 
		humanPlot = getPlot(dataset);   	
    }
    
    private void initMoney(QueryBuilder query, TimeExtent interval) {
    	String label = "Number of Material Damages in each category";
    	
		if (moneyData == null) {
			moneyData = CsvChartUtils.getDataForOneLayer(LayerWrapperEnum.MONEY.csvPath());
		}
		dataset = getDatasetByQuery(moneyData, label, query.getMoneyRange(), interval);   
		moneyPlot = getPlot(dataset); 	
    }


    // ------------------------------------------------------------------------
    // FILTER DATA
    // ------------------------------------------------------------------------
	private CategoryDataset getDatasetByQuery(List<CsvChartType> data, String name, MinMax query, TimeExtent interval) {
    	final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	
    	Map<String, Integer> map;
        try {
			map = getMapFromData(data, query, interval);
			SortedSet<String> keys = new TreeSet<String>(map.keySet());
	        for (String key: keys) {
	            dataset.addValue(map.get(key), name, key);
	        }
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return dataset;
    }

	private Map<String, Integer> getMapFromData(List<CsvChartType> data, MinMax query, TimeExtent interval) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        
        int startYear = interval.getStartDate().get(Calendar.YEAR);
        int startMonth = interval.getStartDate().get(Calendar.MONTH);
        int endYear = interval.getEndDate().get(Calendar.YEAR);
        int endMonth = interval.getEndDate().get(Calendar.MONTH) - 1;
        
        // ADD TO MAP
		for (CsvChartType item : data) {
			double value = Double.valueOf(item.value);
			System.out.print(item);
			if (item.validate(startYear, endYear, startMonth, endMonth) && query.validate(value)) {
				
				System.out.print(" DONE");
				
				String key = item.value;
				Integer count = map.get(key);
				
				count = (count != null) ? count + 1 : item.count; 
				map.put(key, count + 1);
			}
			System.out.println();
		}
        
        return map;
    }


    // ------------------------------------------------------------------------
    // GENERATE CHART
    // ------------------------------------------------------------------------
	private CategoryPlot getPlot(CategoryDataset dataset) {
        final NumberAxis rangeAxis = new NumberAxis("Count");
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        final LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        final CategoryPlot subplot = new CategoryPlot(dataset, null, rangeAxis, renderer);
        subplot.setDomainGridlinesVisible(true);
        
        return subplot;
	}
	
    private JFreeChart getChart(CategoryPlot... plots) {
        final CategoryAxis domainAxis = new CategoryAxis("Category");
        final CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
        
        int index = 1;
        for (CategoryPlot subplot: plots) {
        	if (subplot != null) {
        		plot.add(subplot, index++);
        	}
        }
        
        chart = new JFreeChart(
                title,
                new Font("SansSerif", Font.BOLD, 12),
                plot,
                true
            );
        
        return chart;
    }
    
    // ------------------------------------------------------------------------
    // API
    // ------------------------------------------------------------------------
    
	public static void createChart(LayerWrapperEnum layerType, QueryBuilder query, TimeExtent interval) {		
		final CsvChart demo = new CsvChart(layerType, query, interval);
        		
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
	}
	
	public static void setMagnitudeData(List<CsvChartType> data) {
		CsvChart.magData = data;
	}
	
	public static void setHumanData(List<CsvChartType> data) {
		CsvChart.humanData = data;
	}
	
	public static void setMoneyData(List<CsvChartType> data) {
		CsvChart.moneyData = data;
	}
}