package acs.isi.minions.layerType;


public class LayerMoneyTypes extends LayerWrapperType {
	public static final LayerMoneyTypes ZERO = new LayerMoneyTypes(0, Data.keys[0], Data.values[0]);
	public static final LayerMoneyTypes ONE = new LayerMoneyTypes(1, Data.keys[1], Data.values[1]);
	public static final LayerMoneyTypes TWO = new LayerMoneyTypes(2, Data.keys[2], Data.values[2]);
	public static final LayerMoneyTypes THREE = new LayerMoneyTypes(3, Data.keys[3], Data.values[3]);
	public static final LayerMoneyTypes FOUR = new LayerMoneyTypes(4, Data.keys[4], Data.values[4]);
	public static final LayerMoneyTypes FIVE = new LayerMoneyTypes(5, Data.keys[5], Data.values[5]);
	
	private static final LayerMoneyTypes[] all = {
		ZERO, ONE, TWO, THREE, FOUR, FIVE
	};
	
	private LayerMoneyTypes(int id, double key, String value) {
		super(id, key, value);
	}

	public static LayerMoneyTypes[] all() {
		return all;
	}
	
	public static String getValue(double key) {
		for (int i = Data.keys.length - 1; i >= 0; i--) {
			if (key >= Data.keys[i]) {
				return Data.values[i];
			}
		}
		return Data.values[5];
	}
	
	public static final class Data {
		public static final double keys[] = {
			0, 1, 2, 3, 4, 5
		};
		
		public static final String values[] = {
			"less than $100K", 
			"more than $100.000", 
			"more than $1.000.000", 
			"more than $100.000.000", 
			"more than $1.000.000.000", 
			"more than $10.000.000.000"
		};
		
		private Data() {
			
		}
	}
}
