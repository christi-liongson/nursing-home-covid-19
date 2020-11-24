package org.uchicago.mpcs53014;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List

//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.ListObjectsRequest;
//import com.amazonaws.services.s3.model.ObjectListing;
//import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import com.opencsv.bean.CsvToBeanBuilder;

import org.uchicago.mpcs53014.nursingHomeCovid.NursingHomeCovid;


public class NursingHomeCovidSerializer {
    static TProtocol protocol;
    public static void main(String[] args) {
//        if(args.length == 0 || args.length > 2) {
//            System.err.println("Usage:  yarn jar jarname tarFileLocations hdfsLocationToGenerateThriftFiles");
//            System.err.println("");
//            System.err.println("Examples:");
//            System.err.println("yarn jar uber-IW-1.0-SNAPSHOT.jar org.example.SerializeWeatherSummary /home/hadoop/spertus/weatherData /inputs/thriftWeather1");
//            System.err.println("yarn jar uber-IW-1.0-SNAPSHOT.jar org.example.SerializeWeatherSummary  hdfs:///inputs/raw_weather /inputs/thriftWeather2");
//            System.err.println("yarn jar uber-IW-1.0-SNAPSHOT.jar org.example.SerializeWeatherSummary  s3://spertus-abc/weather /inputs/thriftWeather3");
//            System.exit(-1);
//        }
//        final String targetDir = args.length > 1 ? args[1] : "/inputs/thriftWeather";
        String splitBy = ",";
        String line = "";
        try {
            Configuration conf = new Configuration();
            final Configuration finalConf = new Configuration(conf);
            final FileSystem fs = FileSystem.get(conf);
            final TSerializer ser = new TSerializer(new TBinaryProtocol.Factory());

//            NursingHomeProcessor processor = new NursingHomeProcessor() {
//                Writer getWriter(String fileName) throws IOException {
            SequenceFile.Writer writer = SequenceFile.createWriter(conf,
                        SequenceFile.Writer.file(new Path("/tmp/christiannenic/final_project/serialized_data/nursing_home_covid.out")),
                        SequenceFile.Writer.keyClass(IntWritable.class), SequenceFile.Writer.valueClass(BytesWritable.class),
                        SequenceFile.Writer.compression(CompressionType.BLOCK));
//            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            List<NursingHomeCovid> beans = new CsvToBeanBuilder(new FileReader(args[0]))
                    .withType(NursingHomeCovid.class).withSkipLines(1).build().parse();



//            Since the NursingHomeCovid schema involves many fields, using OOP tools such as OpenCSV to parse the input csv.


//            @Override
//            void processNursingHomeCovid(NursingHomeCovid summary, String fileName) throws IOException {
//                try {
//                    IntWritable key = new IntWritable(1);
////                    User val = user.prepareForSerialization();
//                    BytesWritable val = new BytesWritable(ser.serialize(summary));
//                    writer.append(key, val);
//                     } catch (TException e) {
//                        throw new IOException(e);
//    }
//}

//            };

//            }
//            NursingHomeProcessor processor = new NursingHomeProcessor() {
//                Map<Integer, SequenceFile.Writer> yearMap = new HashMap<Integer, SequenceFile.Writer>();
//                Pattern yearPattern = Pattern.compile("^.*\\d+-\\d+-(\\d+)\\.op\\.gz");
//
//                Writer getWriter(String fileName) throws IOException {
//                    Matcher yearMatcher = yearPattern.matcher(fileName);
//                    if(!yearMatcher.find())
//                        throw new IllegalArgumentException("Bad file name. Can't find year: " + fileName);
//                    int year = Integer.parseInt(yearMatcher.group(1));
//                    if(!yearMap.containsKey(year)) {
//                        yearMap.put(year,
//                                SequenceFile.createWriter(finalConf,
//                                        SequenceFile.Writer.file(
//                                                new Path(targetDir + "/weather-" + Integer.toString(year))),
//                                        SequenceFile.Writer.keyClass(IntWritable.class),
//                                        SequenceFile.Writer.valueClass(BytesWritable.class),
//                                        SequenceFile.Writer.compression(CompressionType.NONE)));
//                    }
//                    return yearMap.get(year);
//                }
//


//            };
//
//            Iterable<InputStream> tarFiles;
//            String tarFilesDirectory = args[0];
//            if(tarFilesDirectory.startsWith("hdfs://"))
//                tarFiles = new InputStreamsForHdfsDirectory(fs, tarFilesDirectory);
//            else if (tarFilesDirectory.startsWith("s3://"))
//                tarFiles = new InputStreamsForS3Folder(tarFilesDirectory);
//            else
//                tarFiles = new InputStreamsForLocalDirectory(tarFilesDirectory);
//
//            for (InputStream is : tarFiles) {
//                processor.processNoaaTarFile(is);
//            }

//            Iterable<InputStream> csvFiles = new InputStreamsForS3Folder(args[0]);
//            for (InputStream is : csvFiles) {
//                processor.processDeficienciesFile(is);
//            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
//    void writeNursingHomeCovid(TSerializer ser, SequenceFile.Writer writer, NursingHomeCovid summary) throws IOException {
//                    try {
////                        getWriter(fileName).append(new IntWritable(1), new BytesWritable(ser.serialize(summary)));;
//                        IntWritable key = new IntWritable(1);
//                        BytesWritable val = new BytesWritable(ser.serialize(summary));
//                        writer.append(key, val);
//                    } catch (TException e) {
//                        throw new IOException(e);
}

// The classes below make it possible to iterate InputStreams for all the tar files in a folder.
// There is one class for each kind of folder that can hold the TarFiles (local filesystem, HDFS, and S3).

// All of these are iterable, so we can use a for loop as above to iterate through the tar files.
// This requires a lot of boilerplate implementing the Iterable and Iterator interfaces (e.g., hasNext()),
// but fortunately, that is basically passing through to the appropriate iterator for the folder type.
//////
//class InputStreamsForS3Folder implements Iterable<InputStream> {
//    static Logger log = Logger.getLogger("InputStreamsForS3Folder");
//    InputStreamsForS3Folder(String folderName) {
//        String s3Prefix = "s3://";
//        String withoutS3Prefix
//                = folderName.startsWith(s3Prefix) ? folderName.substring(s3Prefix.length()) : folderName;
//        // Up to the first / is the bucket name
//        bucketName = withoutS3Prefix.substring(0, withoutS3Prefix.indexOf('/'));
//        // After the bucket name is the object prefix
//        objectPrefix = withoutS3Prefix.substring(bucketName.length()+1);
//    }
//    String bucketName;
//    String objectPrefix;
//    AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
//    @Override
//    public Iterator<InputStream> iterator() {
//        return new S3InputStreamsIterator();
//    }
//
//    private class S3InputStreamsIterator implements Iterator<InputStream> {
//
//        @Override
//        public boolean hasNext() {
//            return objectSummaryIterator.hasNext();
//        }
//
//        @Override
//        public InputStream next() {
//            return s3.getObject(bucketName, objectSummaryIterator.next().getKey()).getObjectContent();
//        }
//
//        Iterator<S3ObjectSummary> objectSummaryIterator
//                = s3.listObjects(bucketName, objectPrefix).getObjectSummaries().iterator();
//    }
//}
