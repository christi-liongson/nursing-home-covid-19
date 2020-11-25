package org.uchicago.mpcs53014;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
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
//import org.apache.commons.csv.CSVParser;

import org.uchicago.mpcs53014.nursingHomeCovid.NursingHomeCovid;


public class NursingHomeCovidSerializer {
    static TProtocol protocol;
    public static void main(String[] args) {
        if(args.length == 0 || args.length > 2) {
            System.err.println("Usage:  yarn jar jarname tarFileLocations hdfsLocationToGenerateThriftFiles");
            System.err.println("");
            System.err.println("Examples:");
            System.err.println("yarn jar uber-IW-1.0-SNAPSHOT.jar org.example.SerializeWeatherSummary /home/hadoop/spertus/weatherData /inputs/thriftWeather1");
            System.err.println("yarn jar uber-IW-1.0-SNAPSHOT.jar org.example.SerializeWeatherSummary  hdfs:///inputs/raw_weather /inputs/thriftWeather2");
            System.err.println("yarn jar uber-IW-1.0-SNAPSHOT.jar org.example.SerializeWeatherSummary  s3://spertus-abc/weather /inputs/thriftWeather3");
            System.exit(-1);
        }
        final String targetDir = args.length > 1 ? args[1] : "/tmp/christiannenic/final_project/thrift_serialization";
        try {
            Configuration conf = new Configuration();
            final Configuration finalConf = new Configuration(conf);
            final FileSystem fs = FileSystem.get(conf);
            final TSerializer ser = new TSerializer(new TBinaryProtocol.Factory());
            NursingHomeProcessor processor = new NursingHomeProcessor() {
                Map<Integer, SequenceFile.Writer> monthMap = new HashMap<Integer, SequenceFile.Writer>();
//                Map<Integer, SequenceFile.Writer> yearMap = new HashMap<Integer, SequenceFile.Writer>();\

//                Pattern yearPattern = Pattern.compile("^.*\\d+-\\d+-(\\d+)\\.op\\.gz");
//                SequenceFile.Writer writer = SequenceFile.createWriter(finalConf,
//                        SequenceFile.Writer.file(
//                                new Path("/christiannenic/final_project/batch/covid")),
//                        SequenceFile.Writer.keyClass(IntWritable.class),
//                        SequenceFile.Writer.valueClass(BytesWritable.class),
//                        SequenceFile.Writer.compression(CompressionType.NONE));

                Writer getWriter(short reportedMonth) throws IOException {
                    if(!monthMap.containsKey(reportedMonth)) {
                        monthMap.put(0,
                               SequenceFile.createWriter(finalConf,
                                        SequenceFile.Writer.file(
                                                new Path(targetDir)),
                                        SequenceFile.Writer.keyClass(IntWritable.class),
                                        SequenceFile.Writer.valueClass(BytesWritable.class),
                                        SequenceFile.Writer.compression(CompressionType.NONE)));
                    }
                    return monthMap.get(0);
                }
                @Override
                void processWeatherSummary(NursingHomeCovid summary, String filename) throws IOException {
                    try {
                        getWriter(summary.reportedMonth).append(new IntWritable(1), new BytesWritable(ser.serialize(summary)));;
                    } catch (TException e) {
                        throw new IOException(e);
                    }
                }
            };

//            String fileName = args[0];
//            String s3Prefix = "s3://";
//            InputStream inputStream = null;
//
//            String withoutS3Prefix
//                    = fileName.startsWith(s3Prefix) ? fileName.substring(s3Prefix.length()) : fileName;
//            // Up to the first / is the bucket name
//            String bucketName = withoutS3Prefix.substring(0, withoutS3Prefix.indexOf('/'));
//            // After the bucket name is the object prefix
//            String objectPrefix = withoutS3Prefix.substring(bucketName.length()+1);
//            AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
//            try {
//                S3Object object = s3.getObject(bucketName, objectPrefix);
//                inputStream = object.getObjectContent();
//
//                for (InputStream is : file) {
//                    processor.processNoaaFile(is);
//                }
//            } catch (AmazonServiceException | IOException e) {
//                System.out.println(e.getMessage());
//            } finally {
//                try {
//                    if (inputStream != null) {
//                        inputStream.close();
//                    }
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//            }



//            processor.processNoaaFile(fs.openFile(args[0]));
            Iterable<InputStream> files;
            String fileName = args[0];
            if(fileName.startsWith("hdfs://"))
                files = new InputStreamsForHdfsDirectory(fs, fileName);
            else if (fileName.startsWith("s3://"))
                files = new InputStreamsForS3Folder(fileName);
            else
                files = new InputStreamsForLocalDirectory(fileName);

            for (InputStream is : files) {
                processor.processNoaaFile(is, fileName);
            }


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

// The classes below make it possible to iterate InputStreams for all the tar files in a folder.
// There is one class for each kind of folder that can hold the TarFiles (local filesystem, HDFS, and S3).

// All of these are iterable, so we can use a for loop as above to iterate through the tar files.
// This requires a lot of boilerplate implementing the Iterable and Iterator interfaces (e.g., hasNext()),
// but fortunately, that is basically passing through to the appropriate iterator for the folder type.
//
class InputStreamsForLocalDirectory implements Iterable<InputStream> {
    InputStreamsForLocalDirectory(String pathName) {
        this.pathName = pathName;
    }
    String pathName;

    @Override
    public Iterator<InputStream> iterator() {
        return new LocalInputStreamsIterator();
    }

    private class LocalInputStreamsIterator implements Iterator<InputStream> {
        Iterator<File> fileIterator
                = FileUtils.iterateFiles(new File(pathName), null, false);

        @Override
        public boolean hasNext() {
            return fileIterator.hasNext();
        }

        @Override
        public InputStream next() {
            try {
                return new FileInputStream(fileIterator.next());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
/** Provides InputStreams for all the tar files in an HDFS directory
 *
 */
class InputStreamsForHdfsDirectory implements Iterable<InputStream> {
    /**
     *
     * @param fs The HDFS Filesystem containing the directory
     * @param pathName The pathName of the directory
     */
    InputStreamsForHdfsDirectory(FileSystem fs, String pathName) {
        this.fs = fs;
        String hdfsPrefix = "hdfs://";
        String withoutHdfsPrefix
                = pathName.startsWith(hdfsPrefix) ? pathName.substring(hdfsPrefix.length()) : pathName;

        this.path = new Path(withoutHdfsPrefix);
    }
    FileSystem fs;
    Path path;

    @Override
    public Iterator<InputStream> iterator() {
        return new HdfsInputStreamsIterator();
    }

    private class HdfsInputStreamsIterator implements Iterator<InputStream> {
        HdfsInputStreamsIterator() {
            try {
                fileStatusIterator = Arrays.asList(fs.listStatus(path)).iterator();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Iterator<FileStatus> fileStatusIterator;

        @Override
        public boolean hasNext() {
            return fileStatusIterator.hasNext();
        }

        @Override
        public InputStream next() {
            try {
                return fs.open(fileStatusIterator.next().getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}

class InputStreamsForS3Folder implements Iterable<InputStream> {
    static Logger log = Logger.getLogger("InputStreamsForS3Folder");
    InputStreamsForS3Folder(String folderName) {
        String s3Prefix = "s3://";
        String withoutS3Prefix
                = folderName.startsWith(s3Prefix) ? folderName.substring(s3Prefix.length()) : folderName;
        // Up to the first / is the bucket name
        bucketName = withoutS3Prefix.substring(0, withoutS3Prefix.indexOf('/'));
        // After the bucket name is the object prefix
        objectPrefix = withoutS3Prefix.substring(bucketName.length()+1);
    }
    String bucketName;
    String objectPrefix;
    AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
    @Override
    public Iterator<InputStream> iterator() {
        return new S3InputStreamsIterator();
    }

    private class S3InputStreamsIterator implements Iterator<InputStream> {

        @Override
        public boolean hasNext() {
            return objectSummaryIterator.hasNext();
        }

        @Override
        public InputStream next() {
            return s3.getObject(bucketName, objectSummaryIterator.next().getKey()).getObjectContent();
        }

        Iterator<S3ObjectSummary> objectSummaryIterator
                = s3.listObjects(bucketName, objectPrefix).getObjectSummaries().iterator();
    }
}
