// prepare search form data
val facilities = spark.table("christiannenic_health_deficiencies").select(
       "providername", "federalprovidernumber").distinct()
facilities.write.mode("overwrite").saveAsTable("christiannenic_facilities_list_hive")

// get facilities by state
val facilities_by_state = spark.table("christiannenic_health_deficiencies").select(
       "state", "providername", "federalprovidernumber" + "provideraddress" +
       "providercity" + "providerstate" + "providerzipcode").distinct()
)

// prepare christiannenic_penalties table
val penalties_by_facility = spark.sql("select federalprovidernumber, " +
                                              "sum(fineamount) " +
                                       "from christiannenic_penalties " +
                                       "group by federalprovidernumber;")

penalties_by_facility.registerTempTable("christiannenic_penalties_by_facility")

// prepare christiannenic_health_deficiencies table
val infection_deficiencies = spark.sql("SELECT federalprovidernumber, " +
                                        "count(*), count(if(lower(deficiencydescription) like '%infection' " +
                         "OR lower(deficiencydescription) like 'infection%'' " +
                         "OR lower(deficiencydescription) like '%infection%', 1, NULL)), " +
                "count(if(scopeseveritycode in ('H', 'I', 'J', 'K', 'L'), 1, NULL)), " +
                "count(if((lower(deficiencydescription) like '%infection' " +
                         "OR lower(deficiencydescription) like 'infection%'' " +
                         "OR lower(deficiencydescription) like '%infection%') " +
                         "AND scopeseveritycode in ('H', 'I', 'J', 'K', 'L'), 1, NULL)) " +
"FROM christiannenic_health_deficiencies " +
"GROUP BY federalprovidernumber")

val

// select federalprovidernumber, count(*) count(if(lower(deficiencydescription) like "%infection" or lower(deficiencydescription) like "infection%"
// or lower(deficiencydescription) like "%infection%", 1, null)), count(if(scopeseveritycode in ("H", "I", "J", "K", "L"), 1, null))
// from christiannenic_health_deficiencies
// group by federalprovidernumber
// SELECT federalprovidernumber,
//        count(*), count(if(lower(deficiencydescription) like "%infection"
//                          OR lower(deficiencydescription) like "infection%"
//                          OR lower(deficiencydescription) like "%infection%", 1, NULL)),
//                 count(if(scopeseveritycode in ("H", "I", "J", "K", "L"), 1, NULL)),
//                 count(if((lower(deficiencydescription) like "%infection"
//                          OR lower(deficiencydescription) like "infection%"
//                          OR lower(deficiencydescription) like "%infection%")
//                          AND scopeseveritycode in ("H", "I", "J", "K", "L"), 1, NULL))
// FROM christiannenic_health_deficiencies
// GROUP BY federalprovidernumber

select federalprovidernumber, count(deficiencydescription) from christiannenic_health_deficiencies where
lower(deficiencydescription) like "%infection" or lower(deficiencydescription) like "infection%"
or lower(deficiencydescription) like "%infection%" group by federalprovidernumber;
