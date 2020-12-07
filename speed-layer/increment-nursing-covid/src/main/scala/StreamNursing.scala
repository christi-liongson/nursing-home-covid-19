import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._
import com.fasterxml.jackson.databind.{ DeserializationFeature, ObjectMapper }
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.ConnectionFactory
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.client.Increment
import org.apache.hadoop.hbase.util.Bytes

object StreamNursing {
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  val hbaseConf: Configuration = HBaseConfiguration.create()
  hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")
  hbaseConf.set("hbase.zookeeper.quorum", "localhost")

  val hbaseConnection = ConnectionFactory.createConnection(hbaseConf)
  val facilityTable = hbaseConnection.getTable(TableName.valueOf("christiannenic_facility_covid_info"))
  val stateTable = hbaseConnection.getTable(TableName.valueOf("christiannenic_state_facility_overview"))

//  def getLatestWeather(station: String) = {
//    val result = latestWeather.get(new Get(Bytes.toBytes(station)))
//    System.out.println(result.isEmpty())
//    if(result.isEmpty())
//      None
//    else
//      Some(WeatherReport(
//        station,
//        Bytes.toBoolean(result.getValue(Bytes.toBytes("weather"), Bytes.toBytes("fog"))),
//        Bytes.toBoolean(result.getValue(Bytes.toBytes("weather"), Bytes.toBytes("rain"))),
//        Bytes.toBoolean(result.getValue(Bytes.toBytes("weather"), Bytes.toBytes("snow"))),
//        Bytes.toBoolean(result.getValue(Bytes.toBytes("weather"), Bytes.toBytes("hail"))),
//        Bytes.toBoolean(result.getValue(Bytes.toBytes("weather"), Bytes.toBytes("thunder"))),
//        Bytes.toBoolean(result.getValue(Bytes.toBytes("weather"), Bytes.toBytes("tornado")))))
//  }

  def incrementReportsByFacility(knr : NursingReport) : String = {
//    val maybeLatestWeather = getLatestWeather(kfr.originName)
//    if(maybeLatestWeather.isEmpty)
//      return "No weather for " + kfr.originName;
//    val latestWeather = maybeLatestWeather.get
    val facilityInc = new Increment(Bytes.toBytes(knr.federalProviderNumber))
    facilityInc.addColumn(Bytes.toBytes("info"), Bytes.toBytes("totalresidentcovidadmissions"), knr.resAdmissions)
    facilityInc.addColumn(Bytes.toBytes("info"), Bytes.toBytes("totalresidentconfirmedcovid"), knr.resConfirmed)
    facilityInc.addColumn(Bytes.toBytes("info"), Bytes.toBytes("totalresidentcoviddeaths"), knr.resDeaths)
    facilityInc.addColumn(Bytes.toBytes("info"), Bytes.toBytes("totalstaffconfirmedcovid"), knr.staffConfirmed)
    facilityInc.addColumn(Bytes.toBytes("info"), Bytes.toBytes("totalstaffcoviddeaths"), knr.staffDeaths)

    facilityTable.increment(facilityInc)
    return "Updated speed layer for facility " + knr.federalProviderNumber
  }

  def main(args: Array[String]) {
    if (args.length < 1) {
      System.err.println(s"""
                            |Usage: StreamFlights <brokers>
                            |  <brokers> is a list of one or more Kafka brokers
                            |
        """.stripMargin)
      System.exit(1)
    }

    val Array(brokers) = args

    // Create context with 2 second batch interval
    val sparkConf = new SparkConf().setAppName("StreamNursing")
    val ssc = new StreamingContext(sparkConf, Seconds(2))

    // Create direct kafka stream with brokers and topics
    val topicsSet = Set("christiannenic-nursing-covid")
    // Create direct kafka stream with brokers and topics
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> brokers,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "use_a_separate_group_id_for_each_stream",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc, PreferConsistent,
      Subscribe[String, String](topicsSet, kafkaParams)
    )

    // Get the lines, split them into words, count the words and print
    val serializedRecords = stream.map(_.value);

    val kfrs = serializedRecords.map(rec => mapper.readValue(rec, classOf[NursingReport]))

    // Update speed table
    val processedFlights = kfrs.map(incrementReportsByFacility)
    processedFlights.print()
    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }
}
