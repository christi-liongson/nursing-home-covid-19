namespace java org.uchicago.mpcs53014.nursingHomeCovid



struct NursingHomeCovid {
    1: required i16 reportedYear;
    2: required byte reportedMonth;
    3: required byte reportedDay;
    4: required string federalProviderNumber;
    5: required string providerName;
    6: required string providerAddress;
    7: required string providerCity;
    8: required string providerState;
    9: required string providerZipCode;
    10: required string submittedData;

//    Optional fields

    11: optional string passedQACheck;
    12: optional i16 residentsWeeklyAdmissionsCovid;
    13: optional i16 residentsWeeklyConfirmedCovid ;
    14: optional i16 residentsWeeklySuspectedCovid;
    15: optional i16 residentsWeeklyAllDeaths;
    16: optional i16 residentsWeeklyCovidDeaths;
    17: optional i16 numberAllBeds = -1;
    18: optional i16 totalNumberOccupiedBeds = -1;
    19: optional string residentAccessToTesting;
    20: optional string ableTestAllResidentsWithin7Days;
    21: optional string notTestingResidentsLackOfPPE;
    22: optional string notTestingResidentsLackOfSupplies;
    23: optional string notTestingResidentsLackAccessLab;
    24: optional string notTestingResidentsLackAccessTrainedPersonnel;
    25: optional string notTestingResidentsUncertaintyReimbursement;
    26: optional string notTestingResidentsOther;
    27: optional string avgTimeToReceiveResidentTestResults;
    28: optional string facilityPerformedResidentTestsSinceLastReport;
    29: optional string testedResidentsWithNewSignsOrSymptoms;
    30: optional string testedAsymptomaticResidentsWithinUnitAfterNewCase;
    31: optional string testedAsymptomaticResidentsFacilityWideAfterNewCase;
    32: optional string testedAsymptomaticResidentsAsSurveillance;
    33: optional i16 staffWeeklyConfirmedCovid;
    34: optional i16 staffWeeklySuspectedCovid;
    35: optional i16 staffWeeklyCovidDeaths;
    36: optional string shortageNursingStaff;
    37: optional string shortageClinicalStaff;
    38: optional string shortageAides;
    39: optional string shortageOtherStaff;
    40: optional string oneWeekSupplyN95Masks;
    41: optional string oneWeekSupplySurgicalMasks;
    42: optional string oneWeekSupplyEyeProtection;
    43: optional string oneWeekSupplyGowns;
    44: optional string oneWeekSupplyGloves;
    45: optional string oneWeekSupplyHandSanitizer;
    46: optional string ventilatorDependentUnit;
    47: optional i16 numberVentilatorsInFacility = -1;
    48: optional i16 numberVentilatorsInUseCovid = -1;
    49: optional string oneWeekSupplyVentilatorSupplies;
    50: optional string threeOrMoreConfirmedCasesThisWeek;

}

