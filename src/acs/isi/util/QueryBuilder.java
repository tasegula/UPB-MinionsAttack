package acs.isi.util;

import acs.isi.minions.layerType.LayerHumanTypes;
import acs.isi.minions.layerType.LayerMagnitudeTypes;
import acs.isi.minions.layerType.LayerMoneyTypes;

public class QueryBuilder {
	private static String command = null;
	private static final String format = "(MAG >= %s AND MAG <= %s) AND (HUMANS >= %s AND HUMANS <= %s) AND (MONEY >= %s AND MONEY <= %s)";
	
	private MinMax magnitudeData = new MinMax();
	private MinMax humanData = new MinMax();
	private MinMax moneyData = new MinMax();
	
	private static QueryBuilder instance = null;
	
	private QueryBuilder() {
		magnitudeData = new MinMax();
		humanData = new MinMax();
		moneyData = new MinMax();
	}
	
	public static QueryBuilder getInstance() {
		if (instance == null) {
			instance = new QueryBuilder();
		}
		return instance;
	}
	
	// ------------------------------------------------------------------------
	// GETTERS
	// ------------------------------------------------------------------------
	public MinMax getMagnitudeRange() {
		return magnitudeData;
	}
	public MinMax getHumanRange() {
		return humanData;
	}
	public MinMax getMoneyRange() {
		return moneyData;
	}
	
	public String query() {
		command = String.format(format, magnitudeData.min, magnitudeData.max, 
										humanData.min, humanData.max, 
										moneyData.min, moneyData.max);
		return command;
	}
	
	// ------------------------------------------------------------------------
	// MAGNITUDE
	// ------------------------------------------------------------------------
	
	public void setMagnitudeMin(LayerMagnitudeTypes value) {
		magnitudeData.min = value.value();
	}
	
	public void setMagnitudeMax(LayerMagnitudeTypes value) {
		magnitudeData.max = value.value();
	}

	// ------------------------------------------------------------------------
	// HUMAN
	// ------------------------------------------------------------------------
	public void setHumanMin(LayerHumanTypes value) {
		humanData.min = value.id();
	}
	
	public void setHumanMax(LayerHumanTypes value) {
		humanData.max = value.id();
	}

	// ------------------------------------------------------------------------
	// MONEY
	// ------------------------------------------------------------------------
	public void setMoneyMin(LayerMoneyTypes value) {
		moneyData.min = value.id();
	}
	
	public void setMoneyMax(LayerMoneyTypes value) {
		moneyData.max = value.id();
	}

	
	// ------------------------------------------------------------------------
	// MIN-MAX class
	// ------------------------------------------------------------------------
	public static class MinMax {
		public String min = "0";
		public String max = "10";
		
		public boolean validate(double test) {
			return (test >= Double.valueOf(min)) && (test <= Double.valueOf(max)); 
		}
	}
}
