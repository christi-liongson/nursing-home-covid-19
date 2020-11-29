// prepare christiannenic_penalties table
val penalties_by_facility = spark.sql("select federalprovidernumber, " +
                                              "sum(fineamount) " +
                                       "from christiannenic_penalties " +
                                       "group by federalprovidernumber;")

// prepare christiannenic_health_deficiencies table
val deficiencies = spark.sql("christiannenic_health_deficiencies")
