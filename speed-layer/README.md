# Speed layer

Topic christiannenic-nursing-covid is created using the following command:

```bash
cd kafka_2.12-2.2.1/bin

./kafka-topics.sh --create --zookeeper z-2.mpcs53014-kafka.fwx2ly.c4.kafka.us-east-2.amazonaws.com:2181,z-3.mpcs53014-kafka.fwx2ly.c4.kafka.us-east-2.amazonaws.com:2181,z-1.mpcs53014-kafka.fwx2ly.c4.kafka.us-east-2.amazonaws.com:2181 --replication-factor 1 --partitions 1 --topic christiannenic-nursing-covid
```

To simulate a speed layer, we will call the CMS's API to gather all data that has been collected after 11/15 when the data for the batch layer was created. An endpoint /submit_data/ will call the CMS API and populate the Kafka topic christiannenic-nursing-covid with new surveys.

```bash
./kafka-console-consumer.sh --bootstrap-server b-1.mpcs53014-kafka.fwx2ly.c4.kafka.us-east-2.amazonaws.com:9092,b-2.mpcs53014-kafka.fwx2ly.c4.kafka.us-east-2.amazonaws.com:9092 --topic christiannenic-nursing-covid
```

You can also submit new data into the same topic on Kafka using the form on the main page of the web app.

We will use this data to increment the christiannenic_facilities_list hbase table.

<!-- To create the hbase table, run the following in the hbase shell -->

<!-- ```bash
create 'christiannenic_latest_covid_reports', 'report'
``` -->

```cd
cd christiannenic/final_project/speedlayer/target
spark-submit --master local[2] --driver-java-options "-Dlog4j.configuration=file:///home/hadoop/ss.log4j.properties" --class StreamNursing uber-untitled-1.0-SNAPSHOT.jar b-1.mpcs53014-kafka.fwx2ly.c4.kafka.us-east-2.amazonaws.com:9092,b-2.mpcs53014-kafka.fwx2ly.c4.kafka.us-east-2.amazonaws.com:9092

```