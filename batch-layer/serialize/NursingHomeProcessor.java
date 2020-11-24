package org.uchicago.mpcs53014;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

	void processLine(String line) throws IOException {
		try {
			String cvsSplitBy = ",";
			String[] nursingHome = line.split(cvsSplitBy);

			processNursingHomeCovid(nursingHomeFromLine(nursingHome));
		} catch(MissingDataException e) {
			// Just ignore lines with missing data
		}
	}

	abstract void processNursingHomeCovid(NursingHomeCovid summary) throws IOException;
	BufferedReader getFileReader(InputStream is) throws FileNotFoundException, IOException {
//
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

//	void processDeficienciesDirectory(String directoryName) throws IOException {
//		File directory = new File(directoryName);
//		File[] directoryListing = directory.listFiles();
//		for(File deficienciesFile : directoryListing)
//			processDeficienciesFile(deficienciesFile);
//	}

	NursingHomeCovid nursingHomeFromLine(String[] line) throws NumberFormatException, MissingDataException {
		NursingHomeCovid summary
				= new NursingHomeCovid(Short.parseShort(line[0].substring(6).trim()),
//						line[0], line[1], line[2], line[3], line[4], line[5], line[6],
//				line[7], line[8], line[9], line[10], line[11], Boolean.parseBoolean(line[12]), line[13]
		);
		return summary;
	}

}
