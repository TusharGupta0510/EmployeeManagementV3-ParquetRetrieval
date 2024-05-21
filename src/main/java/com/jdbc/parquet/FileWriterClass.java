package com.jdbc.parquet;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FileWriterClass {
    private Scanner scanner = new Scanner(System.in);

    public void writeParquetToFile(ResultSet rs) {
        try {
            System.out.println("Enter the location and desired filename for Parquet file:");
            String filename = scanner.nextLine();

            // Define Avro schema
            Schema schema = new Schema.Parser().parse(
                    "{\"type\":\"record\",\"name\":\"Employee\",\"fields\":[{\"name\":\"firstName\",\"type\":\"string\"},{\"name\":\"lastName\",\"type\":\"string\"},{\"name\":\"website\",\"type\":\"string\"}]}");

            // Create Parquet writer
            Configuration conf = new Configuration();
            Path path = new Path(filename);
            @SuppressWarnings("deprecation")
			ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(path)
                    .withSchema(schema)
                    .withConf(conf)                    
                    .withCompressionCodec(CompressionCodecName.SNAPPY)  
                    .build();

            // Write data to Parquet file
            while (rs.next()) {
                GenericRecord record = new GenericData.Record(schema);
                record.put("firstName", rs.getString("firstName"));
                record.put("lastName", rs.getString("lastName"));
                record.put("website", rs.getString("website"));
                writer.write(record);
            }

            writer.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Parquet file created successfully.");
    }
}
