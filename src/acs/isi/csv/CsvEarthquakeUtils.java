package acs.isi.csv;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CsvEarthquakeUtils {
	public List<CsvEarthquakeType> records;
	public int size;

	public CsvEarthquakeUtils(String filepath) {
		try {
			FileReader in = new FileReader(filepath);
			Iterable<CSVRecord> csvRecords = CSVFormat.EXCEL.withHeader().parse(in);
			
			records = new ArrayList<CsvEarthquakeType>();
			
			for (CSVRecord record : csvRecords) {
				CsvEarthquakeType data = new CsvEarthquakeType(record);
				if (data.isCorrect(0)) {
					records.add(data);
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	
	public static CsvEarthquakeUtils combineCsv(String filepath, List<CsvEarthquakeUtils> list) throws IOException, ParseException {
		Writer out = new FileWriter(filepath);
		out.write(CsvUtils.CsvFields.ALL_FINAL);
		
		for (CsvEarthquakeUtils file: list) {
			for (CsvEarthquakeType item : file.records) {
				out.write(item.toCsv());
			}
		}
		
		out.close();		
		return new CsvEarthquakeUtils(filepath);
	}
	
	@Override
	public String toString() {
		return records.stream()
					  .map(CsvEarthquakeType::toString)
					  .collect(Collectors.joining("\n"));
	}
	
	// ************************************************************************
	// HELPERS
	// ************************************************************************
	@SuppressWarnings("unused")
	private void computeNumberOfLines(FileReader in) throws IOException {
		LineNumberReader  lnr = new LineNumberReader(in);
		lnr.skip(Long.MAX_VALUE);
		
		size = lnr.getLineNumber(); // counting starts at 0, but it contains the header
	}
}
