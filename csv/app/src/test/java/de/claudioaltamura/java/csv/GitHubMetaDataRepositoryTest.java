package de.claudioaltamura.java.csv;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GitHubMetaDataRepositoryTest {

    @Test
    @DisplayName("null check inputstream")
    void shouldThrowExceptionIfInputStreamIsNull() {
        final InputStream inputStream = GitHubMetaDataRepositoryTest.class.getResourceAsStream("non-existing.csv");
        var gitHubMetaDataCsvRepository = new GitHubMetaDataCsvRepository();
        assertThrows(NullPointerException.class, () -> gitHubMetaDataCsvRepository.getMetaDataList(inputStream));
    }

    @Test
    @DisplayName("return metadata list")
    void shouldReturnMetadataList() {
        final InputStream inputStream = GitHubMetaDataRepositoryTest.class.getResourceAsStream("/github-metadata.csv");
        var gitHubMetaDataCsvRepository = new GitHubMetaDataCsvRepository();
        assertThat(gitHubMetaDataCsvRepository.getMetaDataList(inputStream)).isNotNull();
    }
}