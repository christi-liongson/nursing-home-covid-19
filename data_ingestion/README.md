# Data Ingestion

Due to the size of the files (> 1 GB), I decided to store the files in HDFS. As the filesize increases, it may make more sense to store the dataset in S3 and copy files into HDFS to create the batch views. Code for ingesting into S3 and copying into HDFS to create batch views is included in `data_ingest_s3.sh`.

## Note
Since the initial ingestion, [data.medicare.gov/](https://data.medicare.gov/) redesigned their website and updated their site architecture. As a result, the original links for ingesting the dataset are no longer available. There does not seem to be a reliable URL pattern for Medicare CSVs since their redesign, but I've included CSVs that may work for ingestion (note: this assumes that the CSV and data structure has not changed in their redesign).

## Ingesting COVID-19 Nursing Home Dataset

```bash
curl -x "socks5h://localhost:8157" -X PUT "http://ec2-52-15-169-10.us-east-2.compute.amazonaws.com:9870/webhdfs/v1/tmp/christiannenic/final_project/raw_data/nursing-covid/covid-19-nursing.csv?op=CREATE&user.name=hadoop&noredirect=true"
```

To get output:

```bash
{"Location":"http://ip-172-31-10-244.us-east-2.compute.internal:9864/webhdfs/v1/tmp/christiannenic/final_project/raw_data/nursing-covid/covid-19-nursing.csv?op=CREATE&user.name=hadoop&namenoderpcaddress=ip-172-31-11-144.us-east-2.compute.internal:8020&createflag=&createparent=true&overwrite=false"}
```

Create the file:

```bash
curl https://data.cms.gov/api/views/s2uc-8wxp/rows.csv | curl -x "socks5h://localhost:8157" -X PUT -T - "http://ip-172-31-10-244.us-east-2.compute.internal:9864/webhdfs/v1/tmp/christiannenic/final_project/raw_data/nursing-covid/covid-19-nursing.csv?op=CREATE&user.name=hadoop&namenoderpcaddress=ip-172-31-11-144.us-east-2.compute.internal:8020&createflag=&createparent=true&overwrite=false"
```

## Ingesting Deficiencies Dataset

```bash
curl -x "socks5h://localhost:8157" -X PUT "http://ec2-52-15-169-10.us-east-2.compute.amazonaws.com:9870/webhdfs/v1/tmp/christiannenic/final_project/raw_data/deficiencies/health-deficiencies.csv?op=CREATE&user.name=hadoop&noredirect=true"
```

To get output:

```bash
{"Location":"http://ip-172-31-10-71.us-east-2.compute.internal:9864/webhdfs/v1/tmp/christiannenic/final_project/raw_data/deficiencies/health-deficiencies.csv?op=CREATE&user.name=hadoop&namenoderpcaddress=ip-172-31-11-144.us-east-2.compute.internal:8020&createflag=&createparent=true&overwrite=false"}
```

Create the file:

```bash
curl https://data.medicare.gov/api/views/r5ix-sfxw/rows.csv | curl -x "socks5h://localhost:8157" -X PUT -T - "http://ip-172-31-10-71.us-east-2.compute.internal:9864/webhdfs/v1/tmp/christiannenic/final_project/raw_data/deficiencies/health-deficiencies.csv?op=CREATE&user.name=hadoop&namenoderpcaddress=ip-172-31-11-144.us-east-2.compute.internal:8020&createflag=&createparent=true&overwrite=false"
```


NOTE: CMS.gov has redesigned their website as of December 2, 2020. The URL originally used in data ingestion no longer works. To redownload files, use the following link: https://data.cms.gov/provider-data/sites/default/files/resources/abca93500cc2ac0e22d42ededfc2e7bd_1605453860/NH_HealthCitations_Nov2020.csv.


## Ingesting Fines Dataset

```bash
curl -x "socks5h://localhost:8157" -X PUT "http://ec2-52-15-169-10.us-east-2.compute.amazonaws.com:9870/webhdfs/v1/tmp/christiannenic/final_project/raw_data/penalties/penalties.csv?op=CREATE&user.name=hadoop&noredirect=true"
```

To get output:

```bash
{"Location":"http://ip-172-31-5-87.us-east-2.compute.internal:9864/webhdfs/v1/tmp/christiannenic/final_project/raw_data/penalties/penalties.csv?op=CREATE&user.name=hadoop&namenoderpcaddress=ip-172-31-11-144.us-east-2.compute.internal:8020&createflag=&createparent=true&overwrite=false"}
```

Create the file:

```bash
curl https://data.medicare.gov/api/views/g6vv-u9sr/rows.csv | curl -x "socks5h://localhost:8157" -X PUT -T - "http://ip-172-31-5-87.us-east-2.compute.internal:9864/webhdfs/v1/tmp/christiannenic/final_project/raw_data/penalties/penalties.csv?op=CREATE&user.name=hadoop&namenoderpcaddress=ip-172-31-11-144.us-east-2.compute.internal:8020&createflag=&createparent=true&overwrite=false"
```

NOTE: CMS.gov has redesigned their website as of December 2, 2020. The URL originally used in data ingestion no longer works. To redownload files, use the following link: https://data.cms.gov/provider-data/sites/default/files/resources/1c63d689a69b79609ef247be05e2bb51_1605453862/NH_Penalties_Nov2020.csv