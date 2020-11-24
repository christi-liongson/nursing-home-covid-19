namespace java org.uchicago.mpcs53014.nursingHomeFines

struct NursingHomeFines {
    1: required string federalProviderNumber;
    2: required i16 penaltyYear;
    3: required byte penaltyMonth;
    4: required byte penaltyDay;
    5: required string penaltyType;
    6: required i32 fineAmount;
}

