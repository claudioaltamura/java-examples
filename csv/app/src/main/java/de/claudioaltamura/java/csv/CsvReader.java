package de.claudioaltamura.java.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

public class CsvReader {

    private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

    public static void main(String[] args) {
        try (InputStream initialStream = CsvReader.class.getResourceAsStream("/github-metadata.csv");
            Reader inputStreamReader = new InputStreamReader(Objects.requireNonNull(initialStream));
            CSVReader reader = new CSVReader(inputStreamReader)) {
                String[] lineInArray;
                while ((lineInArray = reader.readNext()) != null) {
                    logger.info("{} {} {} {}", lineInArray[0], lineInArray[1], lineInArray[2], lineInArray[3]);
                }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
