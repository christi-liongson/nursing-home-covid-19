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

		NursingHomeCovid summary = new NursingHomeCovid(Short.parseShort(record.get("Week Ending").substring(6).trim()),
				Byte.parseByte(record.get("Week Ending").substring(0, 2).trim()),
				Byte.parseByte(record.get("Week Ending").substring(3, 5).trim()),
				record.get("Federal Provider Number"),
				parseBoolean(record.get("Submitted Data")));
		if (record.get("Passed Quality Assurance Check").length() > 0) {
			summary.setPassedQACheck(record.get("Passed Quality Assurance Check"));
		}
		if (record.get("Residents Weekly Admissions COVID-19").length() > 0) {
			summary.setResidentsWeeklyAdmissionsCovid((Short.parseShort(record.get("Residents Weekly Admissions COVID-19"))));
		}
		if (record.get("Residents Weekly Confirmed COVID-19").length() > 0) {
			summary.setResidentsWeeklyAdmissionsCovid(Short.parseShort(record.get("Residents Weekly Confirmed COVID-19")));
		}
		if (record.get("Residents Weekly Suspected COVID-19").length() > 0) {
			summary.setResidentsWeeklyAdmissionsCovid(Short.parseShort(record.get("Residents Weekly Suspected COVID-19")));
		}
		if (record.get("Residents Weekly COVID-19 Deaths").length() > 0) {
			summary.setResidentsWeeklyAllDeaths(Short.parseShort(record.get("Residents Weekly COVID-19 Deaths")));
		}
		if (record.get("Residents Weekly All Deaths").length() > 0) {
			summary.setResidentsWeeklyAllDeaths(Short.parseShort(record.get("Residents Weekly All Deaths")));
		}
		if (record.get("Number of All Beds").length() > 0) {
			summary.setNumberAllBeds(Short.parseShort(record.get("Number of All Beds")));
		}
		if (record.get("Total Number of Occupied Beds").length() > 0) {
			summary.setTotalNumberOccupiedBeds(Short.parseShort(record.get("Total Number of Occupied Beds")));
		}
		if (record.get("Resident Access to Testing in Facility").length() > 0) {
			summary.setResidentAccessToTesting(record.get("Resident Access to Testing in Facility"));
		}
		if (record.get("Laboratory Type Is State Health Dept").length() > 0) {
			summary.setLabTypeStateHealthDept(record.get("Laboratory Type Is State Health Dept"));
		}
		if (record.get("Laboratory Type Is Private Lab").length() > 0) {
			summary.setLabTypePrivateLab(record.get("Laboratory Type Is Private Lab"));
		}
		if (record.get("Laboratory Type Is Other").length() > 0) {
			summary.setLabTypeOther(record.get("Laboratory Type Is Other"));
		}
		if (record.get("Able to Test or Obtain Resources to Test All Current Residents Within Next 7 Days").length() > 0) {
			summary.setAbleTestAllResidentsWithin7Days(record.get("Able to Test or Obtain Resources to Test All Current Residents Within Next 7 Days"));
		}
		if (record.get("Reason for Not Testing Residents - Lack of PPE for Personnel ").length() > 0) {
			summary.setNotTestingResidentsLackOfPPE(record.get("Reason for Not Testing Residents - Lack of PPE for Personnel "));
		}
		if (record.get("Reason for Not Testing Residents - Lack of Supplies").length() > 0) {
			summary.setNotTestingResidentsLackOfSupplies(record.get("Reason for Not Testing Residents - Lack of Supplies"));
		}
		if (record.get("Reason for Not Testing Residents  - Lack of Access to Laboratory").length() > 0) {
			summary.setNotTestingResidentsLackAccessLab(record.get("Reason for Not Testing Residents  - Lack of Access to Laboratory"));
		}
		if (record.get("Reason for Not Testing Residents - Lack of Access to Trained Personnel ").length() > 0) {
			summary.setNotTestingResidentsLackAccessTrainedPersonnel(record.get("Reason for Not Testing Residents - Lack of Access to Trained Personnel "));
		}
		if (record.get("Reason for Not Testing Residents  - Uncertainty About Reimbursement").length() > 0) {
			summary.setNotTestingResidentsUncertaintyReimbursement(record.get("Reason for Not Testing Residents  - Uncertainty About Reimbursement"));
		}
		if (record.get("Reason for Not Testing Residents  - Other").length() > 0) {
			summary.setNotTestingResidentsOther(record.get("Reason for Not Testing Residents  - Other"));
		}
		if (record.get("During Past Two Weeks Average Time to Receive Resident Test Results").length() > 0) {
			summary.setAvgTimeToReceiveResidentTestResults(record.get("During Past Two Weeks Average Time to Receive Resident Test Results"));
		}
		if (record.get("Has Facility Performed Resident Tests Since Last Report").length() > 0) {
			summary.setFacilityPerformedResidentTestsSinceLastReport(record.get("Has Facility Performed Resident Tests Since Last Report"));
		}
		if (record.get("Tested Residents with New Signs or Symptoms").length() > 0) {
			summary.setTestedResidentsWithNewSignsOrSymptoms(record.get("Tested Residents with New Signs or Symptoms"));
		}
		if (record.get("Tested Asymptomatic Residents in a Unit or Section After a New Case").length() > 0) {
			summary.setTestedAsymptomaticResidentsWithinUnitAfterNewCase(record.get("Tested Asymptomatic Residents in a Unit or Section After a New Case"));
		}
		if (record.get("Tested Asymptomatic Residents Facility-Wide After a New Case").length() > 0) {
			summary.setTestedAsymptomaticResidentsFacilityWideAfterNewCase(record.get("Tested Asymptomatic Residents Facility-Wide After a New Case"));
		}
		if (record.get("Tested Asymptomatic Residents Without Known Exposure as Surveillance").length() > 0) {
			summary.setTestedAsymptomaticResidentsAsSurveillance(record.get("Tested Asymptomatic Residents Without Known Exposure as Surveillance"));
		}
		if (record.get("Tested Asymptomatic Residents Facility-Wide After a New Case").length() > 0) {
			summary.setTestedAsymptomaticResidentsAsSurveillance(record.get("Tested Asymptomatic Residents Facility-Wide After a New Case"));
		}
		if (record.get("Staff Weekly Confirmed COVID-19").length() > 0) {
			summary.setStaffWeeklyConfirmedCovid(Short.parseShort(record.get("Staff Weekly Confirmed COVID-19")));
		}
		if (record.get("Staff Weekly Suspected COVID-19").length() > 0) {
			summary.setStaffWeeklySuspectedCovid(Short.parseShort(record.get("Staff Weekly Suspected COVID-19")));
		}
		if (record.get("Staff Weekly COVID-19 Deaths").length() > 0) {
			summary.setStaffWeeklyCovidDeaths(Short.parseShort(record.get("Staff Weekly COVID-19 Deaths")));
		}
		if (record.get("Shortage of Nursing Staff").length() > 0) {
			summary.setShortageNursingStaff(record.get("Shortage of Nursing Staff"));
		}
		if (record.get("Shortage of Clinical Staff").length() > 0) {
			summary.setShortageClinicalStaff(record.get("Shortage of Clinical Staff"));
		}
		if (record.get("Shortage of Aides").length() > 0) {
			summary.setShortageAides(record.get("Shortage of Aides"));
		}
		if (record.get("Shortage of Other Staff").length() > 0) {
			summary.setShortageOtherStaff(record.get("Shortage of Other Staff"));
		}
		if (record.get("One-Week Supply of N95 Masks").length() > 0) {
			summary.setOneWeekSupplyN95Masks(record.get("One-Week Supply of N95 Masks"));
		}
		if (record.get("One-Week Supply of Surgical Masks").length() > 0) {
			summary.setOneWeekSupplySurgicalMasks(record.get("One-Week Supply of Surgical Masks"));
		}
		if (record.get("One-Week Supply of Eye Protection").length() > 0) {
			summary.setOneWeekSupplyEyeProtection(record.get("One-Week Supply of Eye Protection"));
		}
		if (record.get("One-Week Supply of Gowns").length() > 0) {
			summary.setOneWeekSupplyGowns(record.get("One-Week Supply of Gowns"));
		}
		if (record.get("One-Week Supply of Gloves").length() > 0) {
			summary.setOneWeekSupplyGloves(record.get("One-Week Supply of Gloves"));
		}
		if (record.get("One-Week Supply of Hand Sanitizer").length() > 0) {
			summary.setOneWeekSupplyHandSanitizer(record.get("One-Week Supply of Hand Sanitizer"));
		}
		if (record.get("Ventilator Dependent Unit").length() > 0) {
			summary.setVentilatorDependentUnit(record.get("Ventilator Dependent Unit"));
		}
		if (record.get("Number of Ventilators in Facility").length() > 0) {
			summary.setNumberVentilatorsInFacility(Short.parseShort(record.get("Number of Ventilators in Facility")));
		}
		if (record.get("Number of Ventilators in Use for COVID-19").length() > 0) {
			summary.setNumberVentilatorsInUseCovid(Short.parseShort(record.get("Number of Ventilators in Use for COVID-19")));
		}
		if (record.get("One-Week Supply of Ventilator Supplies").length() > 0) {
			summary.setOneWeekSupplyVentilatorSupplies(record.get("One-Week Supply of Ventilator Supplies"));
		}
		if (record.get("Total Resident Confirmed COVID-19 Cases Per 1,000 Residents").length() > 0) {
			summary.setTotalResidentConfirmedPer1000Residents(Float.parseFloat(record.get("Total Resident Confirmed COVID-19 Cases Per 1,000 Residents")));
		}
		if (record.get("Three or More Confirmed COVID-19 Cases This Week").length() > 0) {
			summary.setThreeOrMoreConfirmedCasesThisWeek(record.get("Three or More Confirmed COVID-19 Cases This Week"));
		}




		return summary;

	}

}
