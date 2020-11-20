namespace java edu.uchicago.mpcs53014

enum ScopeSeverityType {
    A = 1,
    B = 2,
    C = 3,
    D = 4,
    E = 5,
    F = 6,
    G = 7,
    H = 8,
    I = 9,
    J = 10,
    K = 11,
    L = 12,
}

struct HealthDeficiencies {
    1: required string federalProviderNumber;
    2: required string providerName;
    3: required string address;
    4: required string state;
    5: required string zipCode;
	6: required i16 year;
	7: required byte month;
	8: required byte day;
	9: required string surveyType;
	10: required string deficiencyPrefix;
	11: required string deficiencyCategory;
	12: required string deficiencyDescription;
	13: required string deficiencyTagNumber;
	14: required ScopeSeverityType scopeSeverity;
	15: required bool deficiencyCorrected;
	16: optional i16 correctedYear;
	17: optional byte correctedMonth;
	18: optional byte correctedDay;
}