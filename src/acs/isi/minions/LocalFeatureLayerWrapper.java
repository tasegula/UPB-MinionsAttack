package acs.isi.minions;

import java.util.LinkedHashMap;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import acs.isi.csv.CsvChart;
import acs.isi.util.JStuff;

import com.esri.client.local.ArcGISLocalFeatureLayer;
import com.esri.core.map.TimeExtent;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;
import com.esri.map.MapTip;
import com.esri.toolkit.sliders.JTimeSlider;

public class LocalFeatureLayerWrapper {
	
	private ArcGISLocalFeatureLayer layer;
	private JTimeSlider timeSlider;
	private LayerWrapperEnum type;
	private LinkedHashMap<String, String> displayFields;

	public LocalFeatureLayerWrapper(LayerWrapperEnum type) {
		layer = new ArcGISLocalFeatureLayer(type.path(), 0, true);
		this.type = type;
		
		initHoverFields();
		
		init();
		setPopup();
		setTimeslider();
	}
	
	public LocalFeatureLayerWrapper(LocalFeatureLayerWrapper other) {
		this.type = other.type;
		this.layer = other.layer;
		this.timeSlider = other.timeSlider;
	}
	
	private LocalFeatureLayerWrapper(LayerWrapperEnum type, TimeExtent timeExtent) {
		layer = new ArcGISLocalFeatureLayer(type.path(), 0, true);
		this.type = type;
		
		initHoverFields();
		
		init();
		setPopup();
		setTimeslider(timeExtent);
	}
	
	public static LocalFeatureLayerWrapper buildLayerWithInterval(LayerWrapperEnum type, TimeExtent timeExtent) {
		return new LocalFeatureLayerWrapper(type, timeExtent);
	}
	
	// ************************************************************************
	// API
	// ************************************************************************
	public void setup() {
		initHoverFields();
		init();
		setPopup();
		setTimeslider();
	}
	
	public ArcGISLocalFeatureLayer layer() {
		return layer;
	}

	public JTimeSlider slider() {
		return timeSlider;
	}

	// ************************************************************************
	// HELPERS
	// ************************************************************************
	
	protected ArcGISLocalFeatureLayer init() {
		layer.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
			@Override
			public void layerInitializeComplete(LayerInitializeCompleteEvent e) {
				if (e.getID() == LayerInitializeCompleteEvent.LOCALLAYERCREATE_ERROR) {
					String errMsg = "Failed to initialize due to " + layer.getInitializationError();
					JOptionPane.showMessageDialog(MapWrapper.map, wrap(errMsg), "", JOptionPane.ERROR_MESSAGE);
	        }
	      }
	    });
		return layer;
	}
	
	protected ArcGISLocalFeatureLayer setPopup() {
		layer.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
			@Override
			public void layerInitializeComplete(LayerInitializeCompleteEvent e) {		        
		        // create the MapTip and set it on the layer
		        layer.setMapTip(new MapTip(displayFields));
			}
		});
        return layer;
	}
	
	protected ArcGISLocalFeatureLayer setTimeslider() {
		timeSlider = JStuff.createTimeSlider(layer, type.title(), 500);
		
		layer.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
			@Override
			public void layerInitializeComplete(LayerInitializeCompleteEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						timeSlider = JStuff.setTimeSlider(timeSlider, layer);
					}
				});
			}
		});
		return layer;
	}
	
	protected ArcGISLocalFeatureLayer setTimeslider(TimeExtent timeExtent) {
		timeSlider = JStuff.createTimeSlider(layer, type.title(), 500);
		
		layer.addLayerInitializeCompleteListener(new LayerInitializeCompleteListener() {
			@Override
			public void layerInitializeComplete(LayerInitializeCompleteEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						timeSlider = JStuff.setTimeSlider(timeSlider, layer, timeExtent);
					}
				});
			}
		});
		return layer;
	}
	
	protected void initHoverFields() {
		// create the map of the fields we want the map tips to display
        displayFields = new LinkedHashMap<>();
        
        displayFields.put("place", "Place: ");
        displayFields.put("time", "Time: ");
        displayFields.put("latitude", "Latitude: ");
        displayFields.put("longitude", "Longitude: ");
        displayFields.put("mag", "Magnitude: ");
        displayFields.put("depth", "Depth: ");
        displayFields.put("humanRange", "Casulties: ");
        displayFields.put("moneyRange", "Damages: ");
	}

	// ************************************************************************
	// HELPERS
	// ************************************************************************
	private static String wrap(String str) {
		// create a HTML string that wraps text when longer
		return "<html><p style='width:200px;'>" + str + "</html>";
	}
}
