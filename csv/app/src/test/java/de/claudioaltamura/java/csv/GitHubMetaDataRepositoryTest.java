package de.claudioaltamura.java.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import static de.claudioaltamura.java.csv.GitHubMetaDataProvider.getMetaDataList;

class GitHubMetaDataRepositoryTest {

    @Test
    @DisplayName("null check inputstream get")
    void shouldThrowExceptionForGetIfInputStreamIsNull() {
        final InputStream inputStream = GitHubMetaDataRepositoryTest.class.getResourceAsStream("non-existing.csv");
        final GitHubMetaDataRepository gitHubMetaDataRepository = new GitHubMetaDataCsvRepository();
        assertThrows(NullPointerException.class, () -> gitHubMetaDataRepository.getMetaDataList(inputStream));
    }

    @Test
    @DisplayName("return metadata list")
    void shouldReturnMetadataList() {
        final InputStream inputStream = GitHubMetaDataRepositoryTest.class.getResourceAsStream("/github-metadata.csv");
        final GitHubMetaDataRepository gitHubMetaDataRepository = new GitHubMetaDataCsvRepository();
        assertThat(gitHubMetaDataRepository.getMetaDataList(inputStream)).isNotNull();
    }

    @Test
    @DisplayName("checks if CsvValidationException is caught")
    void shouldCatchCsvValidationException() {
        final InputStream inputStream = GitHubMetaDataRepositoryTest.class.getResourceAsStream("/github-metadata-corrupt.csv");
        final GitHubMetaDataRepository gitHubMetaDataRepository = new GitHubMetaDataCsvRepository();
        assertThrows(GitHubMetaDataRepositoryException.class, () -> gitHubMetaDataRepository.getMetaDataList(inputStream));
    }


    @Test
    @DisplayName("null check outputstream save")
    void shouldThrowExceptionForSaveIfOutputStreamIsNull() {
        final GitHubMetaDataRepository gitHubMetaDataRepository = new GitHubMetaDataCsvRepository();
        assertThatThrownBy(() -> gitHubMetaDataRepository.saveMetaDataList(getMetaDataList(), null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("save metadata list")
    void shouldSaveMetadataList() throws IOException {
        final String fileName = "test.csv";
        final Path path = Paths.get(createTempDirectory("shouldSaveMetadataList"), fileName);

        final OutputStream outputStream = Files.newOutputStream(path);
        final GitHubMetaDataRepository gitHubMetaDataRepository = new GitHubMetaDataCsvRepository();
        gitHubMetaDataRepository.saveMetaDataList(getMetaDataList(), outputStream);

        final InputStream inputStream = new FileInputStream(path.toString());
        assertThat(gitHubMetaDataRepository.getMetaDataList(inputStream)).isNotNull();
    }

    @Test
    @DisplayName("check if IOException is caught")
    void shouldCaughtIOException() throws IOException {
        final String fileName = "test.csv";
        final Path path = Paths.get(createTempDirectory("shouldCaughtIOException"), fileName);

        final OutputStream outputStream = Files.newOutputStream(path);
        outputStream.close();
        final GitHubMetaDataRepository gitHubMetaDataRepository = new GitHubMetaDataCsvRepository();

        assertThrows(GitHubMetaDataRepositoryException.class, () -> gitHubMetaDataRepository.saveMetaDataList(getMetaDataList(), outputStream));
    }

    private String createTempDirectory(String testName) throws IOException {
        return Files.createTempDirectory(testName).toFile().getAbsolutePath();
    }

}