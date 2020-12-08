# NOTE: data.medicare.gov links are outdated and ingestion will fail. See README.md for more information.

curl https://data.cms.gov/api/views/s2uc-8wxp/rows.csv | aws s3 cp - s3://christiannenic-mpcs53014/final_project/nursing-covid/covid-19-nursing.csv

curl https://data.medicare.gov/api/views/r5ix-sfxw/rows.csv | aws s3 cp - s3://christiannenic-mpcs53014/final_project/deficiencies/health-deficiencies.csv

curl https://data.medicare.gov/api/views/g6vv-u9sr/rows.csv | aws s3 cp - s3://christiannenic-mpcs53014/final_project/penalties/penalties.csv

# Copy data into hdfs
# SSHed into namenode

s3-dist-cp --src=s3://christiannenic-mpcs53014/final_project/nursing-covid/ --dest=hdfs:///tmp/christiannenic/final_project/raw_data/nursing-covid
s3-dist-cp --src=s3://christiannenic-mpcs53014/final_project/deficiencies/ --dest=hdfs:///tmp/christiannenic/final_project/raw_data/deficiencies
s3-dist-cp --src=s3://christiannenic-mpcs53014/final_project/penalties/ --dest=hdfs:///tmp/christiannenic/final_project/raw_data/penalties

