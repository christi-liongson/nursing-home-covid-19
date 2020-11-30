// prepare search form data
val facilities = spark.table("christiannenic_health_deficiencies").select(
       "providername", "federalprovidernumber").distinct()
facilities.write.mode("overwrite").saveAsTable("christiannenic_facilities_list_hive")

// get facilities by state


// prepare christiannenic_penalties table
val penalties_by_facility = spark.sql("select federalprovidernumber, " +
                                              "sum(fineamount) " +
                                       "from christiannenic_penalties " +
                                       "group by federalprovidernumber;")

penalties_by_facility.registerTempTable("christiannenic_penalties_by_facility")

// prepare christiannenic_health_deficiencies table
val deficiencies = spark.sql("christiannenic_health_deficiencies")
