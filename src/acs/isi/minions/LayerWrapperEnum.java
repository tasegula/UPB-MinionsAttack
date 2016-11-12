package acs.isi.minions;

public enum LayerWrapperEnum {
	MAGNITUDE("Magnitude Map", Helpers.MPK_MAG, Helpers.CSV_MAG),
	HUMAN("Casualties Map", Helpers.MPK_HUMAN, Helpers.CSV_HUMAN),
	MONEY("Damages Map", Helpers.MPK_MONEY, Helpers.CSV_MONEY);
	
	private String title;
	private String path;
	private String csvPath;

	private LayerWrapperEnum(String title, String path, String csvPath) {
		this.title = title;
		this.path = path;
		this.csvPath = csvPath;
	}
	
	public String title() {
		return title;
	}
	
	public String path() {
		return path;
	}
	
	public String csvPath() {
		return csvPath;
	}
	
	public void copy(LayerWrapperEnum other) {
		this.title = other.title;
		this.path = other.path;
		this.csvPath = other.csvPath;
	}
	
	public static LayerWrapperEnum getByTitle(String title) {
		switch(title) {
		case "Magnitude Map":
			return MAGNITUDE;
		case "Casualties Map":
			return HUMAN;
		case "Damages Map":
			return MONEY;
		default:
			return MAGNITUDE;	
		}
	}
	
	private class Helpers {
		public static final String CSV_MAG = "data/mag-count.csv";
		public static final String CSV_HUMAN = "data/human-count.csv";
		public static final String CSV_MONEY = "data/money-count.csv";
		private static final String MPK_MAG = "data/earthquake-mag.mpk";
		private static final String MPK_HUMAN = "data/earthquake-human.mpk";
		private static final String MPK_MONEY = "data/earthquake-money.mpk";
	}
	
	@Override
	public String toString() {
		return title;
	}
}

