cd christiannenic/final_project/src/target

hdfs dfs -rm -r /tmp/christiannenic/final_project/thrift_serialization

yarn jar uber-serialize-nursing-homes-0.0.1-SNAPSHOT.jar org.uchicago.mpcs53014.NursingHomeCovidSerializer s3://christiannenic-mpcs53014/final_project/nursing-covid/

hdfs dfs -rm /tmp/christiannenic/final_project/jars/serialize-nursing-deficiencies-0.0.1-SNAPSHOT.jar

hdfs dfs -put serialize-nursing-deficiencies-0.0.1-SNAPSHOT.jar /tmp/christiannenic/final_project/jars/serialize-nursing-deficiencies-0.0.1-SNAPSHOT.jar
