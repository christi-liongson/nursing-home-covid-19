package org.uchicago.mpcs53014;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.hadoop.fs.FSDataInputStream;
import org.uchicago.mpcs53014.nursingHomeFines.NursingHomeFines;



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

	void processLine(String line) throws IOException {
		try {


			processNursingHomeFines(nursingHomeFromLine(line));
		} catch(MissingDataException e) {
			// Just ignore lines with missing data
		}
	}

	abstract void processNursingHomeFines(NursingHomeFines summary) throws IOException;
	BufferedReader getFileReader(InputStream is) throws FileNotFoundException, IOException {

//		if(file.getName().endsWith(".csv"))
//			return new BufferedReader
//					(new InputStreamReader
//							(new FileInputStream(file)));
		return new BufferedReader(new InputStreamReader(is));
	}

	void processDeficienciesFile(InputStream is) throws IOException {
		BufferedReader br = getFileReader(is);
		br.readLine(); // Discard header
		String line;
		while((line = br.readLine()) != null) {
			processLine(line);
		}
	}

	void processNursingHomeFinesCsvFile(InputStream is) throws IOException {
//		InputStream csvInputStream =

//		File directory = new File(directoryName);

//		File[] directoryListing = directory.listFiles();
//		for(File deficienciesFile : directoryListing)
		processDeficienciesFile(is);

	}

	NursingHomeFines nursingHomeFromLine(String line) throws NumberFormatException, MissingDataException {
		String cvsSplitBy = ",";
		String[] nursingHome = line.split(cvsSplitBy);
		NursingHomeFines summary
				= new NursingHomeFines(nursingHome[0], Short.parseShort(nursingHome[6].substring(6).trim()),
				Byte.parseByte(nursingHome[6].substring(0, 2).trim()), Byte.parseByte(nursingHome[6].substring(3, 5).trim()),
				nursingHome[7], Integer.parseInt(nursingHome[8])
				);
		return summary;
	}

}
