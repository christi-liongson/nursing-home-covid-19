create external table christiannenic_health_deficiencies_csv(
    federal_provider_number string,
    provider_name string,
    provider_address string,
    provider_city string,
    provider_state string,
    provider_zip_code string,
    survey_date timestamp,
    survey_type string,
    deficiency_prefix string,
    deficiency_category string,
    deficiency_description string,
    deficiency_tag_number string,
    scope_severity_code string,
    deficiency_corrected string,
    correction_date timestamp,
    inspection_cycle string,
    standard_deficiency string,
    complaint_deficiency boolean,
    location string,
    processing_date timestamp)
    row format serde 'org.apache.hadoop.hive.serde2.OpenCSVSerde'

WITH SERDEPROPERTIES (
   "separatorChar" = "\,",
   "quoteChar"     = "\""
)
STORED AS TEXTFILE
  location '/tmp/christiannenic/final_project/raw_data/deficiencies';

create external table christiannenic_health_deficiencies(
    federal_provider_number string,
    provider_name string,
    provider_address string,
    provider_city string,
    provider_state string,
    provider_zip_code string,
    survey_date timestamp,
    survey_type string,
    deficiency_prefix string,
    deficiency_category string,
    deficiency_description string,
    deficiency_tag_number string,
    scope_severity_code string,
    deficiency_corrected string,
    correction_date timestamp,
    inspection_cycle string,
    standard_deficiency string,
    complaint_deficiency boolean,
    location string,
    processing_date timestamp)
    stored as orc;

insert overwrite table christiannenic_health_deficiencies select * from christiannenic_health_deficiencies_csv;

drop table christiannenic_health_deficiencies_csv;