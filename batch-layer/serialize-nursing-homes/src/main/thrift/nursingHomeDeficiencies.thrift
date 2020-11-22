namespace java org.uchicago.mpcs53014.nursingHomeDeficiencies

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
    L = 12
}

struct NursingHomeDeficiencies {
    1: required string federalProviderNumber;
    2: required string providerName;
    3: required string address;
    4: required string state;
    5: required string zipCode;
    6: required string reportedDate;
	7: required string surveyType;
	8: required string deficiencyPrefix;
	9: required string deficiencyCategory;
	10: required string deficiencyDescription;
	11: required string deficiencyTagNumber;
	12: required ScopeSeverityType scopeSeverity;
	13: required bool deficiencyCorrected;
	14: optional string correctedDate;

}

