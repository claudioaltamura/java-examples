package de.claudioaltamura.java.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class CsvReader {

    private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

    public static void main(String[] args) {
        final InputStream inputStream = CsvReader.class.getResourceAsStream("/github-metadata.csv");
        final GitHubMetaDataRepository gitHubMetaDataRepository = new GitHubMetaDataCsvRepository();
        var metaDataList = gitHubMetaDataRepository.getMetaDataList(inputStream);
        for(GitHubMetaData data : metaDataList) {
            logger.info(data.toString());
        }
    }
}
