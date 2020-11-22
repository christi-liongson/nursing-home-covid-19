package org.uchicago.mpcs53014;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import org.uchicago.mpcs53014.nursingHomeFines.NursingHomeFines;

public class NursingHomeFinesSerializer {
    static TProtocol protocol;
    public static void main(String[] args) {
        try {
            Configuration conf = new Configuration();
            final Configuration finalConf = new Configuration(conf);
            final FileSystem fs = FileSystem.get(conf);
            final TSerializer ser = new TSerializer(new TBinaryProtocol.Factory());
            NursingHomeProcessor processor = new NursingHomeProcessor() {
//                Map<Integer, SequenceFile.Writer> hashMap = new HashMap<Integer, SequenceFile.Writer>();
//                Pattern monthPattern = Pattern.compile("^\\d+/(\\d+)/\\d+");
                int intKey = 0;

                Writer getWriter(File file) throws IOException {
//                    Matcher yearMatcher = monthPattern.matcher(file.getName());
//                    if(!yearMatcher.find())
//                        throw new IllegalArgumentException("Bad file name. Can't find year: " + file.getName());
//                    int year = Integer.parseInt(yearMatcher.group(1));
//                    monthMap.putmonth, ()
//                    hashMap.put(intKey,
                            SequenceFile.Writer writer = SequenceFile.createWriter(finalConf,
                                    SequenceFile.Writer.file(
                                            new Path("/christiannenic/final_project/raw_data/penalties")),
                                    SequenceFile.Writer.keyClass(IntWritable.class),
                                    SequenceFile.Writer.valueClass(BytesWritable.class),
                                    SequenceFile.Writer.compression(CompressionType.NONE));
//                    if(!monthMap.containsKey(year)) {
//                        monthMap.put(year,
//                                SequenceFile.createWriter(finalConf,
//                                        SequenceFile.Writer.file(
//                                                new Path("/inputs/thriftWeather/weather-" + Integer.toString(year))),
//                                        SequenceFile.Writer.keyClass(IntWritable.class),
//                                        SequenceFile.Writer.valueClass(BytesWritable.class),
//                                        SequenceFile.Writer.compression(CompressionType.NONE)));
//                    return hashMap.get(intKey);
                    return writer;
                    }



                @Override
                void processNursingHomeFines(NursingHomeFines summary, File file) throws IOException {
                    try {
                        getWriter(file).append(new IntWritable(1), new BytesWritable(ser.serialize(summary)));;
                    } catch (TException e) {
                        throw new IOException(e);
                    }
                }
            };
            processor.processDeficienciesFile(args[0]);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
