package acs.isi.csv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.csv.CSVRecord;

public final class CsvEarthquakeType {
	
	public Date time = null;
	public String datetime = null;
	
	public Double latitude = null;
	public Double longitude = null;
	public Double depth = null;
	public Double magnitude = null;
	public String place = null;
	
	public Integer humans;
	public Integer money;
	
	public String humanRange;
	public String moneyRange;
	
	private static List<String> places = new ArrayList<String>();
	
	public CsvEarthquakeType(CSVRecord record) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datetime = record.get(CsvUtils.CsvFields.TIME).replace('T', ' ').replaceAll(".[0-9][0-9][0-9]Z", "");
		time = df.parse(datetime);

		String aux = record.get(CsvUtils.CsvFields.PLACE);
		place = aux;		
		
		aux = record.get(CsvUtils.CsvFields.LAT);
		latitude = verifyContent(aux) ? Double.valueOf(aux) : null;
		
		aux = record.get(CsvUtils.CsvFields.LONG);
		longitude = verifyContent(aux) ? Double.valueOf(aux) : null;
		
		aux = record.get(CsvUtils.CsvFields.DEPTH);
		depth = verifyContent(aux) ? Double.valueOf(aux) : null;
		
		aux = record.get(CsvUtils.CsvFields.MAG);
		try {
		magnitude = verifyContent(aux) ? Double.valueOf(aux) : null;
		} catch (NumberFormatException e) {
			magnitude = null;
		}
		
		if (magnitude != null) {
			computeDamages();
		}
	}
	
	public CsvEarthquakeType(CSVRecord record, boolean unique) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datetime = record.get(CsvUtils.CsvFields.TIME).replace('T', ' ').replaceAll(".[0-9][0-9][0-9]Z", "");
		time = df.parse(datetime);

		String aux = record.get(CsvUtils.CsvFields.PLACE);
		if (!places.contains(aux)) {
			place = aux;
			places.add(place);
		}
		else {
			place = null;
		}
		
		aux = record.get(CsvUtils.CsvFields.LAT);
		latitude = verifyContent(aux) ? Double.valueOf(aux) : null;
		
		aux = record.get(CsvUtils.CsvFields.LONG);
		longitude = verifyContent(aux) ? Double.valueOf(aux) : null;
		
		aux = record.get(CsvUtils.CsvFields.DEPTH);
		depth = verifyContent(aux) ? Double.valueOf(aux) : null;
		
		aux = record.get(CsvUtils.CsvFields.MAG);
		try {
		magnitude = verifyContent(aux) ? Double.valueOf(aux) : null;
		} catch (NumberFormatException e) {
			magnitude = null;
		}
		
		if (magnitude != null) {
			computeDamages();
		}
	}
	
	// ************************************************************************
	// API
	// ************************************************************************
	
	public boolean isCorrect(double margin) {
		return (magnitude != null) && (magnitude > margin) && 
				(place != null) &&
				(latitude != null) && (longitude != null);
	}
	
	
	public String toString() {
		return String.format("%s (%s, %s)\t - (%s, %s)\t @ %s [H(%s) M(%s)]", 
								datetime, longitude, latitude, magnitude, depth, place, humans, money);
	}
	
	public String toCsv() {
		return String.format("%s,%s,%s,%s,%s,\"%s\",%s,\"%s\",%s,\"%s\"\n", datetime, latitude, longitude, depth, magnitude, place, humans, humanRange, money, moneyRange);
	}
	
	// ************************************************************************
	// HELPERS
	// ************************************************************************

	private boolean verifyContent(String content) {
		return content.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	private void computeDamages() {

		Random rand = new Random();
		
		if (magnitude < 3) {
			humans = 0;
			money = 0;
		}
		else if (magnitude < 3.5) {
			humans = (rand.nextInt(10) == 0) ? 1 : 0;
			money = (rand.nextInt(25) == 0) ? 1 : 0;
		}
		else if (magnitude < 4) {
			humans = (rand.nextInt(10) == 0) ? 1 : 0;
			money = (rand.nextInt(10) == 0) ? 2 : 
						((rand.nextInt(7) == 0) ? 1 : 0);
		}
		else if (magnitude < 5) {
			humans = (rand.nextInt(10) == 0) ? 2 : 
						((rand.nextInt(7) == 0) ? 1 : 0);
			
			money = (rand.nextInt(10) == 0) ? 2 : 
						((rand.nextInt(5) == 0) ? 1 : 0);
		}
		else if (magnitude < 6) {
			humans = (rand.nextInt(7) == 0) ? 3 : 
						((rand.nextInt(5) == 0) ? 2 : 
							(rand.nextInt(3) == 0) ? 1 : 0);
			
			money = (rand.nextInt(10) == 0) ? 3 : 
						((rand.nextInt(5) == 0) ? 2 : 
							((rand.nextInt(3) == 0) ? 1 : 0));
		}
		else if (magnitude < 7) {
			humans = (rand.nextInt(5) == 0) ? 4 : 
						((rand.nextInt(4) == 0) ? 3 : 
							(rand.nextInt(3) == 0) ? 2 : 
								((rand.nextInt(2) == 0) ? 1 : 0));
			
			money = (rand.nextInt(10) == 0) ? 4 : 
						((rand.nextInt(5) == 0) ? 3 : 2);
		}
		else if (magnitude < 7.5) {
			humans = (rand.nextInt(5) == 0) ? 5 :
						((rand.nextInt(4) == 0) ? 4 : 
							((rand.nextInt(3) == 0) ? 3 : 
									((rand.nextInt(2) == 0) ? 2 : 1)));

			money = (rand.nextInt(7) == 0) ? 5 : 
						((rand.nextInt(5) == 0) ? 4 : 
							((rand.nextInt(2) == 0) ? 3 : 2));
		}
		else if (magnitude < 8) {
			humans = (rand.nextInt(5) == 0) ? 5 :
						((rand.nextInt(4) == 0) ? 4 : 
							((rand.nextInt(3) == 0) ? 3 : 2));

			money = (rand.nextInt(5) == 0) ? 5 : 
						((rand.nextInt(3) == 0) ? 4 : 3);
		}
		else {
			humans = 5;
			money = 5;
		}
		
		humanRange = CsvUtils.HUMAN_RANGE[humans];
		moneyRange = CsvUtils.MONEY_RANGE[money];
	}
}
