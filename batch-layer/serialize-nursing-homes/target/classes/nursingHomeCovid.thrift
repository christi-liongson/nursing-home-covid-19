namespace java org.uchicago.mpcs53014.nursingHomeCovid

enum avgTimeTestResults {
    LESS_THAN_ONE_DAY = 1,
    ONE_TO_TWO_DAYS = 2,
    THREE_TO_SEVEN_DAYS = 3,
    OVER_SEVEN_DAYS = 4
}

struct NursingHomeCovid {
    1: required i16 reportedYear;
    2: required byte reportedMonth;
    3: required byte reportedDay;
//    4: required string federalProviderNumber;
//    5: required string submittedData;
//    6: optional string passedQACheck;
//    7: optional byte residentsWeeklyAdmissionsCovid;
//    8: optional i16 residentsWeeklyConfirmedCovid;
//    9: optional i16 residentsWeeklySuspectedCovid;
//    10: optional i16 residentsWeeklyAllDeaths;
//    11: optional i16 residentsWeeklyCovidDeaths;
//    12: optional i16 numberAllBeds;
//    13: optional i16 totalNumberOccupiedBeds;
//    14: optional string residentAccessToTesting;
//    15: optional string labTypeStateHealthDept;
//    16: optional string labTypePrivateLab;
//    17: optional string labTypeOther;
//    18: optional string ableTestAllResidentsWithin7Days;
//    19: optional string notTestingResidentsLackOfPPE;
//    20: optional string notTestingResidentsLackOfSupplies;
//    21: optional string notTestingResidentsLackAccessLab;
//    22: optional string notTestingResidentsLackAccessTrainedPersonnel;
//    23: optional string notTestingResidentsUncertaintyReimbursement;
//    24: optional string notTestingResidentsOther;
//    25: optional avgTimeTestResults avgTimeToReceiveResidentTestResults;
//    26: optional string facilityPerformedResidentTestsSinceLastReport;
//    27: optional string testedResidentsWithNewSignsOrSymptoms;
//    28: optional string testedAsymptomaticResidentsWithinUnitAfterNewCase;
//    29: optional string testedAsymptomaticResidentsFacilityWideAfterNewCase;
//    30: optional string testedAsymptomaticResidentsAsSurveillance;
//    31: optional string testedSubGroupResidents;
//    32: optional string ableToTestAllStaffWithin7Days;
//    33: optional string notTestingStaffLackOfPPE;
//    34: optional string notTestingStaffLackOfSupplies;
//    35: optional string notTestingStaffLackAccessLab;
//    36: optional string notTestingStaffLackAccessTrainedPersonnel;
//    37: optional string notTestingStaffUncertaintyReimbursement;
//    38: optional string notTestingStaffOther;
//    39: optional avgTimeTestResults avgTimeToReceiveStaffTestResults;
//    40: optional string facilityPerformedStaffTestsSinceLastReport;
//    41: optional string testedStaffWithNewSignsOrSymptoms;
//    42: optional string testedAsymptomaticStaffWithinUnitAfterNewCase;
//    43: optional string testedAsymptomaticStaffFacilityWideAfterNewCase;
//    44: optional string testedAsymptomaticStaffAsSurveillance;
//    45: optional string testedSubGroupStaff;
//    46: optional string inHousePOCTestMachine;
//    47: optional i16 staffWeeklyConfirmedCovid;
//    48: optional i16 staffWeeklySuspectedCovid;
//    49: optional i16 staffWeeklyCovidDeaths;
//    50: optional string shortageNursingStaff;
//    51: optional string shortageClinicalStaff;
//    52: optional string shortageAides;
//    53: optional string shortageOtherStaff;
//    54: optional string oneWeekSupplyN95Masks;
//    55: optional string oneWeekSupplySurgicalMasks;
//    56: optional string oneWeekSupplyEyeProtection;
//    57: optional string oneWeekSupplyGowns;
//    58: optional string oneWeekSupplyGloves;
//    59: optional string oneWeekSupplyHandSanitizer;
//    60: optional string ventilatorDependentUnit;
//    61: optional byte numberVentilatorsInFacility;
//    62: optional byte numberVentilatorsInUseCovid;
//    63: optional string oneWeekSupplyVentilatorSupplies;
//    64: optional i16 totalResidentConfirmedPer1000Residents;
//    65: optional byte totalResidentCovidDeathsAsPercentageofConfirmedCases;
//    66: optional string threeOrMoreConfirmedCasesThisWeek;
}

