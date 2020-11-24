package org.uchicago.mpcs53014;

import com.opencsv.bean.CsvBindByName;

import org.uchicago.mpcs53014.nursingHomeCovid.NursingHomeCovid;

// Documentation code: https://www.baeldung.com/opencsv

public class NursingHomeBean {
//    @CSVBindByPosition(position = 0)
//    private String weekEnding;
    @CsvBindByName(column = "Week Ending")
    private String weekEnding;

    @CsvBindByName(column = "Federal Provider Number")
    private String federalProviderNumber;

    @CsvBindByName(column = "Submitted Data")
    private String submittedData;

    @CsvBindByName(column = "Passed Quality Assurance Check")
    private String passedQACheck;

    @CsvBindByName (column = "Residents Weekly Admissions COVID-19")
    private int residentsWeeklyAdmissionsCovid;

    @CsvBindByName (column = "Residents Weekly Suspected COVID-19")
    private int residentsWeeklySuspectedCovid;

    @CsvBindByName (column = "Residents Weekly All Deaths")
    private int residentsWeeklyAllDeaths;

    @CsvBindByName (column = "Residents Weekly COVID-19 Deaths")
    private int residentsWeeklyCovidDeaths;

    @CsvBindByName (column = "Number of All Beds")
    private int numberAllBeds;

    @CsvBindByName (column = "Total Number of Occupied Beds")
    private int totalNumberOccupiedBeds;

    @CsvBindByName (column = "Resident Access to Testing in Facility")
    private String residentAccessTestingFacility;

    @CsvBindByName (column = "Laboratory Type is State Health Dept")
    private String labTypeStateHealthDept;

    @CsvBindByName (column = "Laboratory Type is Private Lab")
    private String labTypePrivateLab;

    @CsvBindByName (column = "Laboratory Type is Other")
    private String labTypeOther;

//    @CsvBindByName (column = "Able to Test or Obtain Resources to Test All Current Residents Within Next 7 Days")
//    private String ableTestAllResidentsWithin7Days;
//
//    @CsvBindByName (column = "Reason for Not Testing Residents - Lack of PPE for Personnel")
//    private String reasonNotTestingResidentsLackPPE;
//
//    @CsvBindByName (column = "Reason for Not Testing Residents - Lack of Supplies")
//    private String reasonNotTestingResidentsLackSupplies;
//
//    @CsvBindByName (column = "Reason for Not Testing Residents - Lack of Access to Laboratory")
//    private String reasonNotTestingResidentsLackAccessLab;
//
//    @CsvBindByName (column = "Reason for Not Testing Residents - Uncertainty About Reimbursement")
//    private String reasonNotTestingResidentsUncertaintyReimbursement;

    private boolean makeBoolean(String text) {
        boolean booleanValue = text == "Y";
        return booleanValue;
    }

    NursingHomeCovid surveyDataFromLine() {
        String[] reportedDate = weekEnding.split("/");
        NursingHomeCovid summary = new NursingHomeCovid(Short.parseShort(reportedDate[2]),
                Byte.parseByte(reportedDate[0]),
                Byte.parseByte(reportedDate[1]));
        return summary;
    }



}
