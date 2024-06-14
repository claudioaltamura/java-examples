package de.claudioaltamura.java.csv;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        final CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
        try (Reader inputStreamReader = new InputStreamReader(inputStream);
             CSVReader csvReader = new CSVReaderBuilder(inputStreamReader)
                     .withCSVParser(csvParser)
                     .withSkipLines(1) // skip the first line, header info
                             .build()) {
            String[] lineInArray;
            while ((lineInArray = csvReader.readNext()) != null) {
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
    public void saveMetaDataList(List<GitHubMetaData> metadataList, OutputStream outputStream) {
        Objects.requireNonNull(metadataList);
        Objects.requireNonNull(outputStream);
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
             CSVWriter writer = new CSVWriter(outputStreamWriter)) {
            writer.writeNext(new String[] {"Library","Github Stars","Forks","Watchers"});
            for (GitHubMetaData metaData : metadataList) {
                writer.writeNext(
                        new String[]{
                                metaData.library(),
                                String.valueOf(metaData.stars()),
                                String.valueOf(metaData.forks()),
                                String.valueOf(metaData.watchers())
                        }
                );
            }
        } catch (IOException e) {
            throw new GitHubMetaDataRepositoryException(e);
        }
    }

}
