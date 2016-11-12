package acs.isi.minions.layerType;


public class LayerHumanTypes extends LayerWrapperType {
	public static final LayerHumanTypes ZERO = new LayerHumanTypes(0, Data.keys[0], Data.values[0]);
	public static final LayerHumanTypes ONE = new LayerHumanTypes(1, Data.keys[1], Data.values[1]);
	public static final LayerHumanTypes TWO = new LayerHumanTypes(2, Data.keys[2], Data.values[2]);
	public static final LayerHumanTypes THREE = new LayerHumanTypes(3, Data.keys[3], Data.values[3]);
	public static final LayerHumanTypes FOUR = new LayerHumanTypes(4, Data.keys[4], Data.values[4]);
	public static final LayerHumanTypes FIVE = new LayerHumanTypes(5, Data.keys[5], Data.values[5]);
	
	private static final LayerHumanTypes[] all = {
		ZERO, ONE, TWO, THREE, FOUR, FIVE
	};
	
	private LayerHumanTypes(int id, double key, String value) {
		super(id, key, value);
	}

	
	public static LayerHumanTypes[] all() {
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
			"None", 
			"1 - 10 people", 
			"10 - 100 people", 
			"100 - 1000 people", 
			"1000-10000 people", 
			"more than 10k people"
		};
		
		private Data() {
			
		}
	}
}
