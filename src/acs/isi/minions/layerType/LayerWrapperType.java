package acs.isi.minions.layerType;

public abstract class LayerWrapperType {	
	private String id;
	private double key;
	private String value;
	
	public LayerWrapperType(int id, double key, String value) {
		this.id = id + "";
		this.key = key;
		this.value = value;
	}
	
	public String id() {
		return id;
	}
	
	public String value() {
		return value;
	}
	
	public double key() {
		return key;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
