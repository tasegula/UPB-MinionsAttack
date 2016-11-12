package acs.isi.util;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import acs.isi.minions.LayerWrapperEnum;
import acs.isi.minions.MapWrapper;
import acs.isi.minions.layerType.LayerHumanTypes;
import acs.isi.minions.layerType.LayerMagnitudeTypes;
import acs.isi.minions.layerType.LayerMoneyTypes;

public class ControlPanel {

	// J-PANEL control panel
	public static JPanel create() {
		final QueryBuilder sql = QueryBuilder.getInstance();
		final Name layerType = new Name("Magnitude Map");
		
		final JLabel lblTitle = JStuff.getLabel("CONTROL PANEL");
		final JLabel lblChooseLayer = JStuff.getLabel("Choose Layer:");
		final JLabel lblChooseMag = JStuff.getLabel("Choose Magnitude:");
		final JLabel lblLegend = JStuff.getLabel("LEGEND: ");
	
		// drop-down list for the MAP layer
		final JComboBox<LayerWrapperEnum> chooseLayer = new JComboBox<>();
		chooseLayer.setModel(new DefaultComboBoxModel<>(LayerWrapperEnum
				.values()));
		chooseLayer.setSelectedItem(LayerWrapperEnum.MAGNITUDE);
	
		// drop-down list for the MAGNITUDE
		final JComboBox<LayerMagnitudeTypes> chooseMagMin = JStuff.getComboBox(
				LayerMagnitudeTypes.ZERO, LayerMagnitudeTypes.all());
		final JComboBox<LayerMagnitudeTypes> chooseMagMax = JStuff.getComboBox(
				LayerMagnitudeTypes.FIVE, LayerMagnitudeTypes.all());
	
		// drop-down list for the HUMAN casualties
		final JComboBox<LayerHumanTypes> chooseHumanMin = JStuff.getComboBox(
				LayerHumanTypes.ZERO, LayerHumanTypes.all());
		final JComboBox<LayerHumanTypes> chooseHumanMax = JStuff.getComboBox(
				LayerHumanTypes.FIVE, LayerHumanTypes.all());
	
		// drop-down list for the MONEY damage
		final JComboBox<LayerMoneyTypes> chooseMoneyMin = JStuff.getComboBox(
				LayerMoneyTypes.ZERO, LayerMoneyTypes.all());
		final JComboBox<LayerMoneyTypes> chooseMoneyMax = JStuff.getComboBox(
				LayerMoneyTypes.FIVE, LayerMoneyTypes.all());
	
		// button - when clicked, render
		final JButton btnRender = new JButton("Render");
		btnRender.addActionListener(e -> {
			LayerWrapperEnum layer = (LayerWrapperEnum) chooseLayer.getSelectedItem();
			layerType.set(layer.title());
	
			sql.setMagnitudeMin((LayerMagnitudeTypes) chooseMagMin.getSelectedItem());
			sql.setHumanMin((LayerHumanTypes) chooseHumanMin.getSelectedItem());
			sql.setMoneyMin((LayerMoneyTypes) chooseMoneyMin.getSelectedItem());
			sql.setMagnitudeMax((LayerMagnitudeTypes) chooseMagMax.getSelectedItem());
			sql.setHumanMax((LayerHumanTypes) chooseHumanMax.getSelectedItem());
			sql.setMoneyMax((LayerMoneyTypes) chooseMoneyMax.getSelectedItem());
	
			MapWrapper.setLayer(layer, sql.query());
			
		});
		
		// button - when click, create chart
		final JButton btnChart = new JButton("Chart");
		btnChart.addActionListener(e -> {
			MapWrapper.createChart(LayerWrapperEnum.getByTitle(layerType.name), sql);
		});
	
		// group the above UI items into a control panel
		final JPanel controlPanel = JStuff.getBoxPanel(JStuff.controlDim , BoxLayout.Y_AXIS);
		final JPanel magPanel = JStuff.getBoxPanel(JStuff.elemDim, BoxLayout.X_AXIS);
		magPanel.add(chooseMagMin);
		magPanel.add(chooseMagMax);
	
		
		final JPanel humanPanel = JStuff.getGridPanel(JStuff.elemDim);
		JStuff.setGridPanel(humanPanel, chooseHumanMin, chooseHumanMax);
	
		final JPanel moneyPanel = JStuff.getGridPanel(JStuff.elemDim);
		JStuff.setGridPanel(moneyPanel, chooseMoneyMin, chooseMoneyMax);

		final JPanel buttonPanel = JStuff.getBoxPanel(JStuff.elemDim, BoxLayout.X_AXIS);
		buttonPanel.add(btnRender);
		buttonPanel.add(btnChart);
	
		controlPanel.add(lblTitle);
		controlPanel.add(JStuff.getLabel(" "));
		controlPanel.add(lblChooseLayer);
		controlPanel.add(chooseLayer);
	
		controlPanel.add(JStuff.getLabel(" "));
		controlPanel.add(lblChooseMag);
		controlPanel.add(magPanel);
	
		controlPanel.add(JStuff.getLabel(" "));
		controlPanel.add(JStuff.getLabel("Choose Casualties:"));
		controlPanel.add(humanPanel);
	
		controlPanel.add(JStuff.getLabel(" "));
		controlPanel.add(JStuff.getLabel("Choose Damages:"));
		controlPanel.add(moneyPanel);
	
		controlPanel.add(JStuff.getLabel(" "));
		controlPanel.add(buttonPanel);
	
		controlPanel.add(JStuff.getLabel(" "));
		controlPanel.add(lblLegend);
	
		return controlPanel;
	}
	
	public static class Name {
		public String name;
		
		public Name(String name) {
			this.name = name;
		}
		
		public void set(String name) {
			this.name = name;
		}
	}

}
