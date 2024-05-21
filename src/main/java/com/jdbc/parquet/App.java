package com.jdbc.parquet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try {
            // Establish database connection
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // Retrieve data from the database
            ResultSet rs = DataRetrieval.retrieveData(connection);

            // Write JSON to file
            FileWriterClass fileWriter = new FileWriterClass();
            fileWriter.writeParquetToFile(rs);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
