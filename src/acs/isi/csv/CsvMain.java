package acs.isi.csv;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import acs.isi.csv.CsvUtils.CsvFiles;

public class CsvMain {

	// ************************************************************************
	// MAIN
	// ************************************************************************
	public static void main(String[] args) throws IOException, ParseException {
		List<CsvEarthquakeUtils> csvParse = new ArrayList<CsvEarthquakeUtils>();
		
		
		//csvParse.add(new CsvEarthquakeUtils("diana.csv"));
		
		//*
		String[][] files = CsvFiles.files;
		for (int year = 0; year < 5; year++) {
			for (int month = 0; month < 12; month++) {
				String filename = files[year][month];
				System.out.println("START: " + filename);
				csvParse.add(new CsvEarthquakeUtils(filename));
				System.out.println("DONE:  " + filename);
			}
		}
		//*/
		
		CsvEarthquakeUtils csv = CsvEarthquakeUtils.combineCsv("data-all.csv", csvParse);
		//*
		CsvChartUtils.createCsvForMagnitude(csv, "magnitude-count.csv");
		CsvChartUtils.createCsvForHuman(csv, "human-count.csv");
		CsvChartUtils.createCsvForMoney(csv, "money-count.csv");
		//*/
	}
}
