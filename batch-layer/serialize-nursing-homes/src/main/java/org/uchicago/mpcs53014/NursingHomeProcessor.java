package org.uchicago.mpcs53014;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
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
		return new NursingHomeCovid(Short.parseShort(record.get(0).substring(6).trim()),
				Byte.parseByte(record.get(0).substring(0, 2).trim()), Byte.parseByte(record.get(0).substring(3, 5).trim()));
	}

}
