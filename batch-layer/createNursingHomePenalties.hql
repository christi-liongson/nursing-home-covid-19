create temporary table christiannenic_penalties_csv(
    federal_provider_number string,
    provider_name string,
    provider_address string,
    provider_city string,
    provider_state string,
    provider_zip_code string,
    penalty_date string,
    penalty_type string,
    fine_amount string)
    row format serde 'org.apache.hadoop.hive.serde2.OpenCSVSerde'

WITH SERDEPROPERTIES (
   "separatorChar" = "\,",
   "quoteChar"     = "\""
)
STORED AS TEXTFILE
  location '/tmp/christiannenic/final_project/raw_data/penalties'

tblproperties("skip.header.line.count"="1");

create external table christiannenic_penalties(
    federalProviderNumber string,
    penaltyDay smallInt,
    penaltyMonth smallInt,
    penaltyYear smallInt,
    penaltyType string,
    fineAmount bigInt)
    stored as orc;

insert overwrite table christiannenic_penalties
select
    federal_provider_number string,
    substring(penalty_date, 4, 2),
    substring(penalty_date, 1, 2),
    substring(penalty_date, 7, 4),
    penalty_type string,
    fine_amount string
from christiannenic_penalties_csv;
