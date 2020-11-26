package org.uchicago.mpcs53014;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.io.InputStreamReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.uchicago.mpcs53014.nursingHomeCovid.NursingHomeCovid;
//import org.uchicago.mpcs53014.nursingHomeCovid.OptionalCount;


public abstract class NursingHomeProcessor {
	static class MissingDataException extends Exception {

		public MissingDataException(String message) {
			super(message);
		}

		public MissingDataException(String message, Throwable throwable) {
			super(message, throwable);
		}

	}

	static double tryToReadMeasurement(String name, String s, String missing) throws MissingDataException {
		if(s.equals(missing))
			throw new MissingDataException(name + ": " + s);
		return Double.parseDouble(s.trim());
	}

	void processLine(CSVRecord record, String filename) throws IOException {
		try {
			processNursingSummary(nursingFromLine(record), filename);
		} catch(MissingDataException e) {
			// Just ignore lines with missing data
		}
	}

	abstract void processNursingSummary(NursingHomeCovid summary, String filename) throws IOException;
	BufferedReader getFileReader(InputStream is) throws FileNotFoundException, IOException {
		return new BufferedReader(new InputStreamReader(is));
	}

	void processNursingFile(InputStream is, String filename) throws IOException {
		BufferedReader br = getFileReader(is);
		CSVParser csvFileParser = new CSVParser(br, CSVFormat.EXCEL.withFirstRecordAsHeader());

		for (CSVRecord csvRecord : csvFileParser) {
			processLine(csvRecord, filename);
		}
	}

	private Boolean parseBoolean (String response) {
		return (response == "Y");
	}

	NursingHomeCovid nursingFromLine(CSVRecord record) throws NumberFormatException, MissingDataException {
//		if (record.get("Residents Weekly Admissions COVID-19") == null) {
//			System.out.println(record.get("Residents Weekly Admissions COVID-19"));
//		}
//		System.out.println("Weekly admissions for " + record.get("Federal Provider Number") + ": " + record.get("Residents Weekly Admissions COVID-19"));
//		System.out.println(record.get("Submitted Data"));


		NursingHomeCovid summary = new NursingHomeCovid(Short.parseShort(record.get("Week Ending").substring(6).trim()),
				Byte.parseByte(record.get("Week Ending").substring(0, 2).trim()),
				Byte.parseByte(record.get("Week Ending").substring(3, 5).trim()),
				record.get("Federal Provider Number"),
				parseBoolean(record.get("Submitted Data")));
//		Optional<String> passedQACheck = Optional.ofNullable(record.get("Passed Quality Assurance Check"));
//		if (Optional.ofNullable(record.get("Passed Quality Assurance Check")))



//		if (record.get("Passed Quality Assurance Check").length() > 0) {
//			summary.setPassedQACheck(null);
//		} else {
//			summary.setPassedQACheck(null);
//			Optional<Boolean> val = Optional.empty();
//			Optional<Boolean>.empty();
//			summary.setPassedQACheck(val);
//		}
		if (record.get("Residents Weekly Admissions COVID-19").length() > 0) {
////			System.out.println(record.get("Residents Weekly Admissions COVID-19"));
			summary.setResidentsWeeklyAdmissionsCovid((Short.parseShort(record.get("Residents Weekly Admissions COVID-19"))));
		}
//		if (record.get("Residents Weekly Confirmed COVID-19").length() > 0) {
//			summary.setResidentsWeeklyAdmissionsCovid(Short.parseShort(record.get("Residents Weekly Confirmed COVID-19")));
//		}
//		if (record.get("Residents Weekly Suspected COVID-19").length() > 0) {
//			summary.setResidentsWeeklyAdmissionsCovid(Short.parseShort(record.get("Residents Weekly Suspected COVID-19")));
//		}
//		if (record.get("Residents Weekly COVID-19 Deaths").length() > 0) {
//			summary.setResidentsWeeklyAllDeaths(Short.parseShort(record.get("Residents Weekly COVID-19 Deaths")));
//		}
//		if (record.get("Residents Weekly All Deaths").length() > 0) {
//			summary.setResidentsWeeklyAllDeaths(Short.parseShort(record.get("Residents Weekly All Deaths")));
//		}
//		if (record.get("Number of All Beds").length() > 0) {
//			summary.setNumberAllBeds(Short.parseShort(record.get("Number of All Beds")));
//		}
//		if (record.get("Total Number of Occupied Beds").length() > 0) {
//			summary.setTotalNumberOccupiedBeds(Short.parseShort(record.get("Total Number of Occupied Beds")));
//		}
//		if (record.get("Resident Access to Testing in Facility").length() > 0) {
//			summary.setResidentAccessToTesting(parseBoolean(record.get("Resident Access to Testing in Facility")));
//		}


		return summary;

	}

}
