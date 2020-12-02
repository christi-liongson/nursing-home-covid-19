// prepare search form data
val facilities = spark.table("christiannenic_health_deficiencies").select(
       "providername", "federalprovidernumber").distinct()


// get facilities by state
val facilities_by_state = spark.table("christiannenic_health_deficiencies").select(
       "providerstate", "providername", "federalprovidernumber", "provideraddress",
       "providercity", "providerzipcode").distinct()

// get all states
val state = spark.sql("""select distinct providerstate, providerstate as state_dup
       from christiannenic_health_deficiencies""")

// prepare christiannenic_penalties table
val penalties_by_facility = spark.sql("""select federalprovidernumber,
                                          sum(fineamount) as totalfines
                                       from christiannenic_penalties
                                       group by federalprovidernumber;""")

// penalties_by_facility.registerTempTable("christiannenic_penalties_by_facility")

// prepare christiannenic_health_deficiencies table
val deficiencies = spark.sql("""SELECT federalprovidernumber,
       count(*) as totaldeficiencies,
       count(if(lower(deficiencydescription) like "%infection"
              OR lower(deficiencydescription) like "infection%"
              OR lower(deficiencydescription) like "%infection%", 1, NULL)) as infectiondeficiencies,
       count(if(scopeseveritycode in ("H", "I", "J", "K", "L"), 1, NULL)) as severedeficiencies,
       count(if((lower(deficiencydescription) like "%infection"
              OR lower(deficiencydescription) like "infection%"
              OR lower(deficiencydescription) like "%infection%")
              AND scopeseveritycode in ("H", "I", "J", "K", "L"), 1, NULL)) as severeinfectiondeficiencies
FROM christiannenic_health_deficiencies
GROUP BY federalprovidernumber""")

// Clean nursing-covid dataset
var covid_numbers = spark.sql("""SELECT federalprovidernumber,
       COUNT(IF(submitteddata == "Y", 1, null)) as totalsubmissions,
       COUNT(submitteddata) as totaldataentries,
       COUNT(IF(passedqacheck == "N", 1, null)) as totaldidnotpassqa,
       SUM(residentsweeklyadmissionscovid) as totalresidentcovidadmissions,
       SUM(residentsweeklyconfirmedcovid) as totalresidentconfirmedcovid,
       SUM(residentsweeklycoviddeaths) as totalresidentcoviddeaths,
       SUM(staffweeklyconfirmedcovid) as totalstaffconfirmedcovid,
       SUM(staffweeklycoviddeaths) as totalstaffcoviddeaths
       FROM christiannenic_nursing_covid
       GROUP BY federalprovidernumber""")

var covid_response = spark.sql("""SELECT * FROM (SELECT federalprovidernumber,
       totalnumberoccupiedbeds, numberallbeds,
       ableTestAllResidentsWithin7Days,notTestingResidentsLackOfPPE,
       notTestingResidentsLackOfSupplies,notTestingResidentsLackAccessLab,
       notTestingResidentsLackAccessTrainedPersonnel,
       notTestingResidentsUncertaintyReimbursement, notTestingResidentsOther,
       avgTimeToReceiveResidentTestResults,
       facilityPerformedResidentTestsSinceLastReport,
       testedResidentsWithNewSignsOrSymptoms,
       testedAsymptomaticResidentsWithinUnitAfterNewCase,
       testedAsymptomaticResidentsFacilityWideAfterNewCase,
       testedAsymptomaticResidentsAsSurveillance, shortageNursingStaff,
       shortageClinicalStaff, shortageAides, shortageOtherStaff,
       oneWeekSupplyN95Masks, oneWeekSupplySurgicalMasks,
       oneWeekSupplyEyeProtection, oneWeekSupplyGowns, oneWeekSupplyGloves,
       oneWeekSupplyHandSanitizer, ventilatorDependentUnit,
       numberVentilatorsInFacility, numberVentilatorsInUseCovid,
       oneWeekSupplyVentilatorSupplies,totalResidentConfirmedPer1000Residents,
       threeOrMoreConfirmedCasesThisWeek,
       rank() over(partition by federalprovidernumber
       order by reportedyear desc, reportedmonth desc, reportedday desc) as rank
       FROM christiannenic_nursing_covid) a
       WHERE a.rank = 1""")

// COVID cases by date per facility
var covid_over_time_facility = spark.sql("""select
       concat(federalprovidernumber, "_", reportedyear, "-", reportedmonth, "-", reportedday) as key,
       reportedyear, reportedmonth, reportedday, residentsweeklyconfirmedcovid,
       residentsweeklycoviddeaths, staffweeklyconfirmedcovid, staffweeklycoviddeaths
       from christiannenic_nursing_covid""")

// Merge views for facility page
var facility_covid_info = facilities_by_state.join(covid_numbers, "federalprovidernumber").join(
       covid_response, "federalprovidernumber").join(deficiencies,
       "federalprovidernumber").join(penalties_by_facility, "federalprovidernumber")


// Facility overview
var facility_overview = facility_covid_info.select(
       "providername", "federalprovidernumber", "provideraddress", "providercity",
       "providerzipcode", "providerstate", "totalresidentconfirmedcovid",
       "totalresidentcoviddeaths","infectiondeficiencies").withColumn("state_facility",
       concat($"providerstate", lit("_"), regexp_replace(
              facility_covid_info.col("providername"), "[^A-Z0-9_]", "")))


// Write tables to Hive

facilities.write.mode("overwrite").saveAsTable("christiannenic_facilities_list_hive")
state.write.mode("overwrite").saveAsTable("christiannenic_state_list_hive")
facility_covid_info.write.mode("overwrite").saveAsTable("christiannenic_facility_covid_info_hive")
covid_over_time_facility.write.mode("overwrite").saveAsTable("christiannenic_covid_over_time_hive")
facility_overview.write.mode("overwrite").saveAsTable("christiannenic_state_facility_overview_hive")

