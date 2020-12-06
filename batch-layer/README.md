# Batch Layer ReadMe

Data sources are ingested into HDFS for serialization. See `data_ingestion.md` for the script to ingest raw data files. Since data is loaded in as csvs and the size of the files are < 1 GB, storing raw data files in HDFS allows us to append new data to files. If storage later becomes an issue, raw data files should be stored in S3 to preserve space and grouped when importing data back into S3. Sample code for data ingestion into S3 and copying files into HDFS is in `data_ingest_s3.sh`.

## Batch Layer

Scripts to create the Batch Layer are in the folder `batch-layer`.

Nursing Home Deficiencies and Penalties data sources are added to Hive using HQL using the scripts in `createNursingHomeDeficiencies.hql` and `createNursingHomePenalties.hql`, respectively.

In anticipation of future changes to the survey, the COVID-19 Nursing Home survey response dataset has been Thrift serialized and loaded into the Hive dataset. Script to navigate to the directory, serialize the data, and move the jar to hdfs is in `runjar.sh`. Creating the table in Hive is in `createNursingHomeCovid.hql`

## Building Batch View

To create the batch views, open the SSH tunnel and use the following command to open Spark:

```bash
spark-shell --master local[*] --jars hdfs:///tmp/christiannenic/final_project/jars/serialize-nursing-deficiencies-0.0.1-SNAPSHOT.jar
```

Use the script in `build_batch_views.scala` to create the tables and add to Hive.

Start Hbase. Create the necessary HBase tables using the following commands:

```bash
create 'christiannenic_facilities_list', 'facility'
create 'christiannenic_state_list', 'state'
create 'christiannenic_facility_covid_info', 'info'
create 'christiannenic_covid_over_time', 'report'
create 'christiannenic_state_facility_overview', 'summary'
```
