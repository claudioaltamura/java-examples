package de.claudioaltamura.java.csv;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface GitHubMetaDataRepository {

    List<GitHubMetaData> getMetaDataList(InputStream inputStream);

    void saveMetaDataList(List<GitHubMetaData> metadataList, OutputStream outputStream);

}
