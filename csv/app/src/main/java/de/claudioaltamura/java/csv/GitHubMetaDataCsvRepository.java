package de.claudioaltamura.java.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GitHubMetaDataCsvRepository implements GitHubMetaDataRepository{

    private static final Logger logger = LoggerFactory.getLogger(GitHubMetaDataCsvRepository.class);

    @Override
    public List<GitHubMetaData> getMetaDataList(InputStream inputStream) {
        Objects.requireNonNull(inputStream);

        logger.debug("get csv");

        final var metaDataList = new ArrayList<GitHubMetaData>();
        try (Reader inputStreamReader = new InputStreamReader(inputStream);
             CSVReader reader = new CSVReader(inputStreamReader)) {
            //skip header
            reader.readNext();
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {
                var gitHubMetaData = new GitHubMetaData(lineInArray[0],
                        Integer.parseInt(lineInArray[1]),
                        Integer.parseInt(lineInArray[2]),
                        Integer.parseInt(lineInArray[3]));
                metaDataList.add(gitHubMetaData);
            }
            return metaDataList;
        } catch (IOException | CsvValidationException | RuntimeException e) {
            throw new GitHubMetaDataRepositoryException(e);
        }
    }

    @Override
    public void saveMetaDataList(List<GitHubMetaData> metadata) {
        throw new UnsupportedOperationException();
    }
}
