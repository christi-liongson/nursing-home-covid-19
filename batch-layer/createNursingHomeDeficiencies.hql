create temporary table christiannenic_health_deficiencies_csv(
    federal_provider_number string,
    provider_name string,
    provider_address string,
    provider_city string,
    provider_state string,
    provider_zip_code string,
    survey_date string,
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
    processing_date string)
    row format serde 'org.apache.hadoop.hive.serde2.OpenCSVSerde'

WITH SERDEPROPERTIES (
   "separatorChar" = "\,",
   "quoteChar"     = "\""
)
STORED AS TEXTFILE
  location '/tmp/christiannenic/final_project/raw_data/deficiencies'

tblproperties("skip.header.line.count"="1");

create external table christiannenic_health_deficiencies(
    federalProviderNumber string,
    surveyDay smallInt,
    surveyMonth smallInt,
    surveyYear smallInt,
    surveyType string,
    deficiencyPrefix string,
    deficiencyCategory string,
    deficiencyDescription string,
    deficiencyTagNumber string,
    scopeSeverityCode string,
    deficiencyCorrected string,
    correctionDay smallInt,
    correctionMonth smallInt,
    correctionYear smallInt,
    inspectionCycle string,
    standardDeficiency string,
    complaintDeficiency boolean,
    processingDay smallInt,
    processingMonth smallInt,
    processingYear smallInt)
    stored as orc;

insert overwrite table christiannenic_health_deficiencies
select
    federal_provider_number,
    substring(survey_date, 4, 2),
    substring(survey_date, 1, 2),
    substring(survey_date, 7, 4),
    survey_type,
    deficiency_prefix,
    deficiency_category,
    deficiency_description,
    deficiency_tag_number,
    scope_severity_code,
    deficiency_corrected,
    substring(correction_date, 4, 2),
    substring(correction_date, 1, 2),
    substring(correction_date, 7, 4),
    inspection_cycle,
    standard_deficiency,
    complaint_deficiency,
    substring(processing_date, 4, 2),
    substring(processing_date, 1, 2),
    substring(processing_date, 7, 4)
from christiannenic_health_deficiencies_csv;
