package acs.isi.minions.layerType;


public class LayerMagnitudeTypes extends LayerWrapperType {
	public static final LayerMagnitudeTypes ZERO = new LayerMagnitudeTypes(0, Data.keys[0], Data.values[0]);
	public static final LayerMagnitudeTypes ONE = new LayerMagnitudeTypes(1, Data.keys[1], Data.values[1]);
	public static final LayerMagnitudeTypes TWO = new LayerMagnitudeTypes(2, Data.keys[2], Data.values[2]);
	public static final LayerMagnitudeTypes THREE = new LayerMagnitudeTypes(3, Data.keys[3], Data.values[3]);
	public static final LayerMagnitudeTypes FOUR = new LayerMagnitudeTypes(4, Data.keys[4], Data.values[4]);
	public static final LayerMagnitudeTypes FIVE = new LayerMagnitudeTypes(5, Data.keys[5], Data.values[5]);
	
	private static final LayerMagnitudeTypes[] all = {
		ZERO, ONE, TWO, THREE, FOUR, FIVE
	};
	
	private LayerMagnitudeTypes(int id, double key, String value) {
		super(id, key, value);
	}

	public static LayerMagnitudeTypes[] all() {
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
			5.00, 5.50, 6.00, 6.50, 7.00, 7.50
		};
		
		public static final String values[] = {
			"5.00", "5.50", "6.00", "6.50", "7.00", "7.50"
		};
		
		private Data() {
			
		}
	}
}
