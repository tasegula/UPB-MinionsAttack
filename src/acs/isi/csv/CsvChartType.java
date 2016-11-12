package acs.isi.csv;

public class CsvChartType implements Comparable<CsvChartType> {
	public int year;
	public int month;
	public String value;
	public int count;
	
	public String magValue;
	public int magCount;
	public String humanValue;
	public int humanCount;
	public String moneyValue;
	public int moneyCount;
	
	public CsvChartType(int year, int month, String value, int count) {
		this.year = year;
		this.month = month;
		this.value = value;
		this.count = count;
	}
	
	public CsvChartType(int year, int month, String value) {
		this.year = year;
		this.month = month;
		this.value = value;
		this.count = 0;
	}
	
	public CsvChartType(int year, int month) {
		this.year = year;
		this.month = month;
	}
	
	public boolean validateYear(int startYear, int endYear) {
		return (this.year >= startYear) && (this.year <= endYear); 
	}
	
	public boolean validateMonth(int startMonth, int endMonth) {
		return (this.month >= startMonth) && (this.month <= endMonth);
	}
	
	public boolean validate(int startYear, int endYear, int startMonth, int endMonth) {
		int startPeriod = startYear * 12 + startMonth;
		int endPeriod = endYear * 12 + endMonth;
		int thisPeriod = year * 12 + month;
		
		return (thisPeriod >= startPeriod) && (thisPeriod <= endPeriod);
	}

	@Override
	public boolean equals(Object obj){
	    if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof CsvChartType))return false;
	    CsvChartType other = (CsvChartType)obj;

	    return (this.year == other.year) &&
	    		(this.month == other.month) &&
	    		(this.value.equals(other.value)) &&
	    		(this.count == other.count);
	}
	
	@Override
	public int hashCode() {
		return year * 100 + month + value.hashCode();
	}
	
	@Override
	public int compareTo(CsvChartType o) {
		return (this.year < o.year) ? 
					-1 :
					(this.year > o.year) ?
							+1 :
							(this.month < o.month) ?
									-1 :
									(this.month > o.month) ?
											+1 : 
											(this.value.compareTo(o.value));
	}
	
	@Override
	public String toString() {
		return String.format("[%d-%d: %s-%d]", year, month, value, count);
	}
}
