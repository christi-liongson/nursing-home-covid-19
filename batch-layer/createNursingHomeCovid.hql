add jar hdfs:///tmp/christiannenic/final_project/jars/serialize-nursing-deficiencies-0.0.1-SNAPSHOT.jar;

drop table christiannenic_nursing_covid;

CREATE EXTERNAL TABLE IF NOT EXISTS christiannenic_nursing_covid
  ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.thrift.ThriftDeserializer'
    WITH SERDEPROPERTIES (
      'serialization.class' = 'org.uchicago.mpcs53014.nursingHomeCovid.NursingHomeCovid',
      'serialization.format' =  'org.apache.thrift.protocol.TBinaryProtocol')
  STORED AS SEQUENCEFILE
  LOCATION '/tmp/christiannenic/final_project/thrift_serialization';

  SELECT * FROM christiannenic_nursing_covid LIMIT 2;

  DESCRIBE christiannenic_nursing_covid;