package acs.isi.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import acs.isi.minions.layerType.LayerHumanTypes;
import acs.isi.minions.layerType.LayerMagnitudeTypes;
import acs.isi.minions.layerType.LayerMoneyTypes;

@SuppressWarnings("deprecation")
public class CsvChartUtils {

	public static List<CsvChartType> createCsvForMagnitude(CsvEarthquakeUtils csv, String newFilename) {
		Map<CsvChartType, Integer> map = new HashMap<CsvChartType, Integer>();
		
		// INITIALIZE THE MAP
		for (String value : LayerMagnitudeTypes.Data.values) {
			for (int year = 2010; year < 2015; year++) {
				for (int month = 0; month < 12; month++) {
					CsvChartType data = new CsvChartType(year, month, value);
					map.put(data, 0);
				}
			}
		}
		
		// ADD DATA
		for (CsvEarthquakeType item : csv.records) {
			Date date = item.time;
			String value = LayerMagnitudeTypes.getValue(item.magnitude);
			CsvChartType key = new CsvChartType(1900 + date.getYear(), date.getMonth(), value);
			int count = map.get(key) + 1;
			map.put(key, count);
		}
		
		// CREATE CSV
		try {
			return createCsvFromMap(map, newFilename);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<CsvChartType> createCsvForHuman(CsvEarthquakeUtils csv, String newFilename) {
		Map<CsvChartType, Integer> map = new HashMap<CsvChartType, Integer>();
		
		// INITIALIZE THE MAP
		for (String value : LayerHumanTypes.Data.values) {
			for (int year = 2010; year < 2015; year++) {
				for (int month = 0; month < 12; month++) {
					CsvChartType data = new CsvChartType(year, month, value);
					map.put(data, 0);
				}
			}
		}
		
		// ADD DATA
		for (CsvEarthquakeType item : csv.records) {
			Date date = item.time;
			String value = LayerHumanTypes.getValue(item.humans);
			CsvChartType key = new CsvChartType(1900 + date.getYear(), date.getMonth(), value);
			int count = map.get(key) + 1;
			map.put(key, count);
		}
		
		// CREATE CSV
		try {
			return createCsvFromMap(map, newFilename);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<CsvChartType> createCsvForMoney(CsvEarthquakeUtils csv, String newFilename) {
		Map<CsvChartType, Integer> map = new HashMap<CsvChartType, Integer>();
		
		// INITIALIZE THE MAP
		for (String value : LayerMoneyTypes.Data.values) {
			for (int year = 2010; year < 2015; year++) {
				for (int month = 0; month < 12; month++) {
					CsvChartType data = new CsvChartType(year, month, value);
					map.put(data, 0);
				}
			}
		}
		
		// ADD DATA
		for (CsvEarthquakeType item : csv.records) {
			Date date = item.time;
			String value = LayerMoneyTypes.getValue(item.money);
			CsvChartType key = new CsvChartType(1900 + date.getYear(), date.getMonth(), value);
			int count = map.get(key) + 1;
			map.put(key, count);
		}
		
		// CREATE CSV
		try {
			return createCsvFromMap(map, newFilename);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<CsvChartType> createCsvFromMap(Map<CsvChartType, Integer> map,
													String filepath) throws IOException {
		List<CsvChartType> list = new ArrayList<CsvChartType>();
		Writer out = new FileWriter(filepath);
		out.write("year,month,key,count\n");
		
		for (CsvChartType key: map.keySet()) {
			int count = map.get(key);
			out.write(String.format("%d,%d,%s,%d\n", key.year, key.month, key.value, count));
			list.add(key);
		}
		
		out.close();
		java.util.Collections.sort(list);
		return list;
	}
	
	public static List<CsvChartType> getDataForOneLayer(String filename) {
		List<CsvChartType> data = new ArrayList<>();
        BufferedReader br;
        
		try {
			br = new BufferedReader(new FileReader(filename));String line = br.readLine(); // ignore first line
	        
	        while ((line = br.readLine()) != null) {
	            StringTokenizer st = new StringTokenizer(line, ",");
	            if (st.countTokens() == 4) {
		            // The first token is year value.
		            int year = Integer.valueOf(st.nextToken());
		            // The last token is month value.
		            int month = Integer.valueOf(st.nextToken());
		            // The last token is month value.
		            String value = st.nextToken();
		            // The last token is month value.
		            int count = Integer.valueOf(st.nextToken());
	
		            data.add(new CsvChartType(year, month, value, count));
	            }
	        }
	        br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.sort(data);
		return data;
	}

}
