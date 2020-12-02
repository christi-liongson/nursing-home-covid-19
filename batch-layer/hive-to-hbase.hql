set hive.support.quoted.identifiers=none;

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
  totalsubmissions bigint, totaldataentries bigint, totaldidnotpassqa bigint,
  totalresidentcovidadmissions bigint, totalresidentconfirmedcovid bigint,
  totalresidentcoviddeaths bigint, totalstaffconfirmedcovid bigint,
  totalstaffcoviddeaths bigint, totalnumberoccupiedbeds smallint,
  numberallbeds smallint, abletestallresidentswithin7days string,
  nottestingresidentslackofppe string, nottestingresidentslackofsupplies string,
  nottestingresidentslackaccesslab string,
  nottestingresidentslackaccesstrainedpersonnel string,
  nottestingresidentsuncertaintyreimbursement string,
  nottestingresidentsother string, avgtimetoreceiveresidenttestresults string,
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
  totalresidentconfirmedper1000residents double,
  threeormoreconfirmedcasesthisweek string,
  totaldeficiencies bigint, infectiondeficiencies bigint,
  severedeficiencies bigint, severeinfectiondeficiencies bigint,
  totalfines bigint
  )
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,
info:providerstate, info:providername, info:provideraddress, info:providercity,
info:providerzipcode, info:totalsubmissions, info:totaldataentries, info:totaldidnotpassqa,
info:totalresidentcovidadmissions, info:totalresidentconfirmedcovid,
info:totalresidentcoviddeaths, info:totalstaffconfirmedcovid, info:totalstaffcoviddeaths,
info:totalnumberoccupiedbeds, info:numberallbeds, info:abletestallresidentswithin7days,
info:nottestingresidentslackofppe, info:nottestingresidentslackofsupplies,
info:nottestingresidentslackaccesslab, info:nottestingresidentslackaccesstrainedpersonnel,
info:nottestingresidentsuncertaintyreimbursement, info:nottestingresidentsother,
info:avgtimetoreceiveresidenttestresults, info:facilityperformedresidenttestssincelastreport,
info:testedresidentswithnewsignsorsymptoms, info:testedasymptomaticresidentswithinunitafternewcase,
info:testedasymptomaticresidentsfacilitywideafternewcase,
info:testedasymptomaticresidentsassurveillance,
info:shortagenursingstaff, info:shortageclinicalstaff, info:shortageaides,
info:shortageotherstaff, info:oneweeksupplyn95masks, info:oneweeksupplysurgicalmasks,
info:oneweeksupplyeyeprotection, info:oneweeksupplygowns, info:oneweeksupplygloves,
info:oneweeksupplyhandsanitizer, info:ventilatordependentunit,
info:numberventilatorsinfacility, info:numberventilatorsinusecovid,
info:oneweeksupplyventilatorsupplies, info:totalresidentconfirmedper1000residents,
info:threeormoreconfirmedcasesthisweek,
info:totaldeficiencies, info:infectiondeficiencies, info:severedeficiencies,
info:severeinfectiondeficiencies, info:totalfines')
TBLPROPERTIES ('hbase.table.name' = 'christiannenic_facility_covid_info');

-- Skipping rank from import into Hbase table, select all other columns.
-- Source: https://stackoverflow.com/questions/51227890/hive-how-to-select-all-but-one-column

insert overwrite table christiannenic_facility_covid_info
  select `(rank)?+.+` from christiannenic_facility_covid_info_hive;

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
summary:totalresidentconfirmedcovid,
summary:totalresidentcoviddeaths, summary:infectiondeficiencies')
TBLPROPERTIES ('hbase.table.name' = 'christiannenic_state_facility_overview');

insert overwrite table christiannenic_state_facility_overview
  select state_facility, providername, federalprovidernumber, provideraddress,
  providercity, providerzipcode, providerstate, totalresidentconfirmedcovid,
  totalresidentcoviddeaths, infectiondeficiencies from christiannenic_state_facility_overview_hive;

