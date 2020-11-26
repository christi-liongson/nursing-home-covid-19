namespace java org.uchicago.mpcs53014.nursingHomeCovid

//enum avgTimeTestResults {
//    LESS_THAN_ONE_DAY = 1,
//    ONE_TO_TWO_DAYS = 2,
//    THREE_TO_SEVEN_DAYS = 3,
//    OVER_SEVEN_DAYS = 4
//}

//union OptionalCountEnvelope {
//    1: optional OptionalCount result;
//}
////    1: string input "NULL";
////    1: string input = "NULL";
////}
//struct OptionalCount {
//    1: optional i16 op;
//}

struct NursingHomeCovid {
    1: required i16 reportedYear;
    2: required byte reportedMonth;
    3: required byte reportedDay;
    4: required string federalProviderNumber;
    5: required bool submittedData;
    6: optional string passedQACheck;
    7: optional i16 residentsWeeklyAdmissionsCovid = -1;
    8: optional i16 residentsWeeklyConfirmedCovid = -1;
    9: optional i16 residentsWeeklySuspectedCovid = -1;
    10: optional i16 residentsWeeklyAllDeaths = -1;
    11: optional i16 residentsWeeklyCovidDeaths = -1;
    12: optional i16 numberAllBeds = -1;
    13: optional i16 totalNumberOccupiedBeds = -1;
    14: optional string residentAccessToTesting;
    15: optional string labTypeStateHealthDept;
    16: optional string labTypePrivateLab;
    17: optional string labTypeOther;
    18: optional string ableTestAllResidentsWithin7Days;
    19: optional string notTestingResidentsLackOfPPE;
    20: optional string notTestingResidentsLackOfSupplies;
    21: optional string notTestingResidentsLackAccessLab;
    22: optional string notTestingResidentsLackAccessTrainedPersonnel;
    23: optional string notTestingResidentsUncertaintyReimbursement;
    24: optional string notTestingResidentsOther;
//    25: optional string avgTimeToReceiveResidentTestResults;
//    26: optional bool facilityPerformedResidentTestsSinceLastReport;
//    27: optional bool testedResidentsWithNewSignsOrSymptoms;
//    28: optional bool testedAsymptomaticResidentsWithinUnitAfterNewCase;
//    29: optional bool testedAsymptomaticResidentsFacilityWideAfterNewCase;
//    30: optional bool testedAsymptomaticResidentsAsSurveillance;
//    31: optional bool testedSubGroupResidents;
//    32: optional bool ableToTestAllStaffWithin7Days;
//    33: optional bool notTestingStaffLackOfPPE;
//    34: optional bool notTestingStaffLackOfSupplies;
//    35: optional bool notTestingStaffLackAccessLab;
//    36: optional bool notTestingStaffLackAccessTrainedPersonnel;
//    37: optional bool notTestingStaffUncertaintyReimbursement;
//    38: optional bool notTestingStaffOther;
//    39: optional string avgTimeToReceiveStaffTestResults;
//    40: optional bool facilityPerformedStaffTestsSinceLastReport;
//    41: optional bool testedStaffWithNewSignsOrSymptoms;
//    42: optional bool testedAsymptomaticStaffWithinUnitAfterNewCase;
//    43: optional bool testedAsymptomaticStaffFacilityWideAfterNewCase;
//    44: optional bool testedAsymptomaticStaffAsSurveillance;
//    45: optional bool testedSubGroupStaff;
//    46: optional bool inHousePOCTestMachine;
//    47: optional i16 staffWeeklyConfirmedCovid;
//    48: optional i16 staffWeeklySuspectedCovid;
//    49: optional i16 staffWeeklyCovidDeaths;
//    50: optional bool shortageNursingStaff;
//    51: optional bool shortageClinicalStaff;
//    52: optional bool shortageAides;
//    53: optional bool shortageOtherStaff;
//    54: optional bool oneWeekSupplyN95Masks;
//    55: optional bool oneWeekSupplySurgicalMasks;
//    56: optional bool oneWeekSupplyEyeProtection;
//    57: optional bool oneWeekSupplyGowns;
//    58: optional bool oneWeekSupplyGloves;
//    59: optional bool oneWeekSupplyHandSanitizer;
//    60: optional bool ventilatorDependentUnit;
//    61: optional byte numberVentilatorsInFacility;
//    62: optional byte numberVentilatorsInUseCovid;
//    63: optional bool oneWeekSupplyVentilatorSupplies;
//    64: optional i16 totalResidentConfirmedPer1000Residents;
//    65: optional byte totalResidentCovidDeathsAsPercentageofConfirmedCases;
//    66: optional bool threeOrMoreConfirmedCasesThisWeek;
}

