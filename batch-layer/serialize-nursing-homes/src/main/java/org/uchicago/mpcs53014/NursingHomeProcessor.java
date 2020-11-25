package org.uchicago.mpcs53014;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.uchicago.mpcs53014.nursingHomeCovid.NursingHomeCovid;


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


	NursingHomeCovid nursingFromLine(CSVRecord record) throws NumberFormatException, MissingDataException {
		return new NursingHomeCovid(Short.parseShort(record.get("Week Ending").substring(6).trim()),
				Byte.parseByte(record.get("Week Ending").substring(0, 2).trim()),
				Byte.parseByte(record.get("Week Ending").substring(3, 5).trim()),
				record.get("Federal Provider Number"));
	}

}
c