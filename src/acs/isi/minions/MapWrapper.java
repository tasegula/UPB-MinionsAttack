package acs.isi.minions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import acs.isi.csv.CsvChart;
import acs.isi.csv.CsvChartType;
import acs.isi.csv.CsvChartUtils;
import acs.isi.util.ControlPanel;
import acs.isi.util.JStuff;
import acs.isi.util.QueryBuilder;
import acs.isi.util.Utils;

import com.esri.map.JMap;
import com.esri.map.LayerList;
import com.esri.map.MapOptions;
import com.esri.map.MapOptions.MapType;
import com.esri.toolkit.legend.JLegend;
import com.esri.toolkit.sliders.JTimeSlider;

public class MapWrapper {
	public static JComponent contentPane;
	public static JMap map;
	public static JLegend legend;
	public static JTimeSlider slider;
	public static JPanel controlPanel;
	
	private static MapOptions mapOptions;
	private static LayerList layerList;
	
	private static List<CsvChartType> magData;
	private static List<CsvChartType> humanData;
	private static List<CsvChartType> moneyData;

	private MapWrapper() {
	}
	
	public static void setup() {
		slider = new JTimeSlider();
		contentPane = JStuff.createContentPane();
		  
		mapOptions = new MapOptions(MapType.TOPO);
		map = new JMap(mapOptions);
		
		legend = new JLegend(map);
		legend.setSize(new Dimension(Utils.App.jWidth, Utils.App.height));
		legend.list();		
		layerList = map.getLayers();

		magData = CsvChartUtils.getDataForOneLayer(LayerWrapperEnum.MAGNITUDE.csvPath());
		humanData = CsvChartUtils.getDataForOneLayer(LayerWrapperEnum.HUMAN.csvPath());
		moneyData = CsvChartUtils.getDataForOneLayer(LayerWrapperEnum.MONEY.csvPath());
		
		CsvChart.setMagnitudeData(magData);
		CsvChart.setHumanData(humanData);
		CsvChart.setMoneyData(moneyData);
	}
	
	public static void bind() {		
		controlPanel = ControlPanel.create();
		controlPanel.add(legend);
		
		contentPane.add(map, BorderLayout.CENTER);
		contentPane.add(controlPanel, BorderLayout.WEST);
		contentPane.add(slider, BorderLayout.SOUTH);
	}
	
	public static void setDefaultLayer(LayerWrapperEnum type) {
		LocalFeatureLayerWrapper layer = new LocalFeatureLayerWrapper(type);
		layer.layer().setName(type.title());
		layerList.add(layer.layer());
		slider = layer.slider();
	}

	public static void setLayer(LayerWrapperEnum layerType, String query) {
		//LocalFeatureLayerWrapper layer = new LocalFeatureLayerWrapper(layerType);
		LocalFeatureLayerWrapper layer = LocalFeatureLayerWrapper.buildLayerWithInterval(layerType, slider.getTimeInterval());
		layer.layer().setName(layerType.title());
		layer.layer().setDefinitionExpression(query);
		
		layerList.remove(1).dispose();
		layerList.add(layer.layer());
		map.validate();
		
		contentPane.remove(slider);
		slider = layer.slider();
		contentPane.add(slider, BorderLayout.SOUTH);
	}
	
	public static void createChart(LayerWrapperEnum layerType, QueryBuilder query) {
		CsvChart.createChart(layerType, query, slider.getTimeInterval());
	}
}

