-- Writing christiannenic_facilities_list_hive hive table to hbase

create external table  christiannenic_facilities_list (
  providername string, federalprovidernumber string)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,facility:federalprovidernumber')
TBLPROPERTIES ('hbase.table.name' = 'christiannenic_facilities_list');

insert overwrite table christiannenic_facilities_list
  select * from christiannenic_facilities_list_hive;

-- Writing christiannenic_state_list_hive hive table to hbase

create external table  christiannenic_state_list (
  state_key string, state_dup string)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,state:state_dup')
TBLPROPERTIES ('hbase.table.name' = 'christiannenic_state_list');

insert overwrite table christiannenic_state_list
  select * from christiannenic_state_list_hive;

-- Writing christiannenic_facility_covid_info_hive hive table to hbase

create external table  christiannenic_facility_covid_info (
  federalprovidernumber string, providerstate string, providername string,
  provideraddress string, providercity string, providerzipcode string,
  totalsubmissions bigint, totaldataentries bigint, totalpassqa bigint,
  totalresidentcovidadmissions bigint, totalresidentconfirmedcovid bigint,
  totalresidentcoviddeaths bigint, totalstaffconfirmedcovid bigint,
  totalstaffcoviddeaths bigint, totalnumberoccupiedbeds bigint,
  numberallbeds bigint, abletestallresidentswithin7days string,
  avgtimetoreceiveresidenttestresults string,
  facilityperformedresidenttestssincelastreport string,
  testedresidentswithnewsignsorsymptoms string,
  testedasymptomaticresidentswithinunitafternewcase string,
  testedasymptomaticresidentsfacilitywideafternewcase string,
  testedasymptomaticresidentsassurveillance string,
  shortagenursingstaff string, shortageclinicalstaff string,
  shortageaides string, shortageotherstaff string,
  oneweeksupplyn95masks string, oneweeksupplysurgicalmasks string,
  oneweeksupplyeyeprotection string, oneweeksupplygowns string,
  oneweeksupplygloves string, oneweeksupplyhandsanitizer string,
  ventilatordependentunit string, numberventilatorsinfacility smallint,
  numberventilatorsinusecovid smallint, oneweeksupplyventilatorsupplies string,
  threeormoreconfirmedcasesthisweek string,
  totaldeficiencies bigint, infectiondeficiencies bigint,
  severedeficiencies bigint, severeinfectiondeficiencies bigint,
  totalfines bigint
  )
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,
info:providerstate, info:providername, info:provideraddress, info:providercity,
info:providerzipcode, info:totalsubmissions#b, info:totaldataentries#b, info:totalpassqa#b,
info:totalresidentcovidadmissions#b, info:totalresidentconfirmedcovid#b,
info:totalresidentcoviddeaths#b, info:totalstaffconfirmedcovid#b, info:totalstaffcoviddeaths#b,
info:totalnumberoccupiedbeds#b, info:numberallbeds#b, info:abletestallresidentswithin7days,
info:avgtimetoreceiveresidenttestresults, info:facilityperformedresidenttestssincelastreport,
info:testedresidentswithnewsignsorsymptoms, info:testedasymptomaticresidentswithinunitafternewcase,
info:testedasymptomaticresidentsfacilitywideafternewcase,
info:testedasymptomaticresidentsassurveillance,
info:shortagenursingstaff, info:shortageclinicalstaff, info:shortageaides,
info:shortageotherstaff, info:oneweeksupplyn95masks, info:oneweeksupplysurgicalmasks,
info:oneweeksupplyeyeprotection, info:oneweeksupplygowns, info:oneweeksupplygloves,
info:oneweeksupplyhandsanitizer, info:ventilatordependentunit,
info:numberventilatorsinfacility, info:numberventilatorsinusecovid,
info:oneweeksupplyventilatorsupplies,
info:threeormoreconfirmedcasesthisweek,
info:totaldeficiencies#b, info:infectiondeficiencies#b, info:severedeficiencies#b,
info:severeinfectiondeficiencies#b, info:totalfines#b')
TBLPROPERTIES ('hbase.table.name' = 'christiannenic_facility_covid_info');

insert overwrite table christiannenic_facility_covid_info select
  federalprovidernumber, providerstate, providername,
  provideraddress, providercity, providerzipcode,
  totalsubmissions, totaldataentries, totalpassqa,
  totalresidentcovidadmissions, totalresidentconfirmedcovid,
  totalresidentcoviddeaths, totalstaffconfirmedcovid,
  totalstaffcoviddeaths, totalnumberoccupiedbeds,
  numberallbeds, abletestallresidentswithin7days,
  avgtimetoreceiveresidenttestresults,
  facilityperformedresidenttestssincelastreport,
  testedresidentswithnewsignsorsymptoms,
  testedasymptomaticresidentswithinunitafternewcase,
  testedasymptomaticresidentsfacilitywideafternewcase,
  testedasymptomaticresidentsassurveillance,
  shortagenursingstaff, shortageclinicalstaff,
  shortageaides, shortageotherstaff,
  oneweeksupplyn95masks, oneweeksupplysurgicalmasks,
  oneweeksupplyeyeprotection, oneweeksupplygowns,
  oneweeksupplygloves, oneweeksupplyhandsanitizer,
  ventilatordependentunit, numberventilatorsinfacility,
  numberventilatorsinusecovid, oneweeksupplyventilatorsupplies,
  threeormoreconfirmedcasesthisweek,
  totaldeficiencies, infectiondeficiencies,
  severedeficiencies, severeinfectiondeficiencies,
  totalfines from christiannenic_facility_covid_info_hive;

-- Writing christiannenic_covid_over_time_hive hive table to hbase

create external table  christiannenic_covid_over_time (
  key string, reportedyear smallint, reportedmonth tinyint, reportedday tinyint,
  residentsweeklyconfirmedcovid smallint, residentsweeklycoviddeaths smallint,
  staffweeklyconfirmedcovid smallint, staffweeklycoviddeaths smallint)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,
report:reportedyear, report:reportedmonth, report:reportedday,
report:residentsweeklyconfirmedcovid, report:residentsweeklycoviddeaths,
report:staffweeklyconfirmedcovid, report:staffweeklycoviddeaths')
TBLPROPERTIES ('hbase.table.name' = 'christiannenic_covid_over_time');

insert overwrite table christiannenic_covid_over_time
  select * from christiannenic_covid_over_time_hive;

-- Writing christiannenic_state_facility_overview_hive hive table to hbase

create external table  christiannenic_state_facility_overview (
  state_facility string, providername string,
  federalprovidernumber string, provideraddress string, providercity string,
  providerzipcode string, providerstate string, totalresidentconfirmedcovid bigint,
  totalresidentcoviddeaths bigint,infectiondeficiencies bigint)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,
summary:providername, summary:federalprovidernumber, summary:provideraddress,
summary:providercity, summary:providerzipcode, summary:providerstate,
summary:totalresidentconfirmedcovid#b,
summary:totalresidentcoviddeaths#b, summary:infectiondeficiencies#b')
TBLPROPERTIES ('hbase.table.name' = 'christiannenic_state_facility_overview');

insert overwrite table christiannenic_state_facility_overview
  select state_facility, providername, federalprovidernumber, provideraddress,
  providercity, providerzipcode, providerstate, totalresidentconfirmedcovid,
  totalresidentcoviddeaths, infectiondeficiencies from christiannenic_state_facility_overview_hive;

  -- Writing christiannenic_state_facility_overview_hive hive table to hbase

create external table  christiannenic_state_facility_overview2 (
  state_facility string, federalprovidernumber string)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,
summary:federalprovidernumber')
TBLPROPERTIES ('hbase.table.name' = 'christiannenic_state_facility_overview2');

insert overwrite table christiannenic_state_facility_overview2
  select state_facility, federalprovidernumber
  from christiannenic_state_facility_overview_hive2;

