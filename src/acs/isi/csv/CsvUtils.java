package acs.isi.csv;


public final class CsvUtils {

	public static final String[] HUMAN_RANGE = {
		"None",
		"1-10 people",
		"10-100 people",
		"100-1000 people",
		"1000-10000 people",
		"more than 10.000 people"
	};
	
	public static final String[] MONEY_RANGE = {
		"less than $100K",
		"more than $100.000",
		"more than $1.000.000",
		"more than $100.000.000",
		"more than $1.000.000.000",
		"more than $10.000.000.000"
	};
	
	public static final class CsvFields {
		public static final String TIME = "time";
		public static final String LAT = "latitude";
		public static final String LONG = "longitude";
		public static final String DEPTH = "depth";
		public static final String MAG = "mag";
		public static final String MAG_TYPE = "magType";
		public static final String NST = "nst";
		public static final String GAP = "gap";
		public static final String DMIN = "dmin";
		public static final String RMS = "rms";
		public static final String NET = "net";
		public static final String ID = "id";
		public static final String UPDATED = "updated";
		public static final String PLACE = "place";
		public static final String TYPE = "type";

		public static final String HUMANS = "humans";
		public static final String MONEY = "money";

		public static final String HUMAN_RANGE = "humanRange";
		public static final String MONEY_RANGE = "moneyRange";

		public static final String ALL_FINAL = String.format(
				"%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", TIME, LAT, LONG, DEPTH, MAG,
				PLACE, HUMANS, HUMAN_RANGE, MONEY, MONEY_RANGE);
	}

	public static final class CsvFiles {
		private static final String base = "data/";
		private static final String YEAR_10 = "2010";
		private static final String YEAR_11 = "2011";
		private static final String YEAR_12 = "2012";
		private static final String YEAR_13 = "2013";
		private static final String YEAR_14 = "2014";
		private static final String path10 = base + YEAR_10 + "/";
		private static final String path11 = base + YEAR_11 + "/";
		private static final String path12 = base + YEAR_12 + "/";
		private static final String path13 = base + YEAR_13 + "/";
		private static final String path14 = base + YEAR_14 + "/";
		
		public static final String[] csv10 = generate(path10, YEAR_10);
		public static final String[] csv11 = generate(path11, YEAR_11);
		public static final String[] csv12 = generate(path12, YEAR_12);
		public static final String[] csv13 = generate(path13, YEAR_13);
		public static final String[] csv14 = generate(path14, YEAR_14);
		
		public static final String[][] files = generate();
		
		private static String[] generate(String path, String base) {
			String[] files = {
					path + base + "-01.csv",
					path + base + "-02.csv",
					path + base + "-03.csv",
					path + base + "-04.csv",
					path + base + "-05.csv",
					path + base + "-06.csv",
					path + base + "-07.csv",
					path + base + "-08.csv",
					path + base + "-09.csv",
					path + base + "-10.csv",
					path + base + "-11.csv",
					path + base + "-12.csv"
				};
			return files;
		}
		
		private static String[][] generate() {
			String[][] files = {
					generate(path10, YEAR_10),
					generate(path11, YEAR_11),
					generate(path12, YEAR_12),
					generate(path13, YEAR_13),
					generate(path14, YEAR_14)
			};
			return files;
		}
	}

}
