s3-dist-cp --src=s3://christiannenic-mpcs53014/final_project/nursing-covid/ --dest=hdfs:///tmp/christiannenic/final_project/raw_data/nursing-covid

cd christiannenic/final_project/src/target

hdfs dfs -rm -r /tmp/christiannenic/final_project/thrift_serialization

yarn jar uber-serialize-nursing-homes-0.0.1-SNAPSHOT.jar org.uchicago.mpcs53014.NursingHomeCovidSerializer hdfs:///tmp/christiannenic/final_project/raw_data/nursing-covid

hdfs dfs -rm /tmp/christiannenic/final_project/jars/serialize-nursing-deficiencies-0.0.1-SNAPSHOT.jar

hdfs dfs -put serialize-nursing-deficiencies-0.0.1-SNAPSHOT.jar /tmp/christiannenic/final_project/jars/serialize-nursing-deficiencies-0.0.1-SNAPSHOT.jar
