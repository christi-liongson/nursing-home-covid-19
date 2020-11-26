# Nursing Home Deficiencies and COVID-19 Cases

## Data Sources

* [Health Deficiencies](https://data.medicare.gov/Nursing-Home-Compare/Health-Deficiencies/r5ix-sfxw)
* [COVID-19 Nursing Home Dataset](https://data.cms.gov/Special-Programs-Initiatives-COVID-19-Nursing-Home/COVID-19-Nursing-Home-Dataset/s2uc-8wxp)
* [Fines](https://data.medicare.gov/Nursing-Home-Compare/Penalties/g6vv-u9sr)

Data sources are ingested into S3 and copied into HDFS for serialization. See `data_ingest_s3.sh` for the script to ingest raw data files.

## Batch Layer

Scripts to create the Batch Layer are in the folder `batch-layer`.

Nursing Home Deficiencies and Penalties data sources are added to Hive using HQL using the scripts in `createNursingHomeDeficiencies.hql` and `createNursingHomePenalties.hql`, respectively.

In anticipation of future changes to the survey, the COVID-19 Nursing Home survey response dataset has been Thrift serialized and loaded into the Hive dataset. Script to navigate to the directory, serialize the data, and move the jar to hdfs is in `runjar.sh`. Creating the table in Hive is in `createNursingHomeCovid.hql`
