package de.claudioaltamura.java.csv;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static de.claudioaltamura.java.csv.CsvDataProvider.getMetaDataList;

public class CsvWriter {

    public static void main(String[] args) throws IOException {
        String tmpDir = Files.createTempDirectory("CsvWriter").toFile().getAbsolutePath();
        final Path path = Paths.get(tmpDir, "output.csv");
        try (OutputStream outputStream = Files.newOutputStream(Path.of(path.toString()))) {
            final GitHubMetaDataRepository gitHubMetaDataRepository = new GitHubMetaDataCsvRepository();
            gitHubMetaDataRepository.saveMetaDataList(getMetaDataList(), outputStream);
        }
    }
}
