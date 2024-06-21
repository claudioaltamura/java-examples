package de.claudioaltamura.java.csv;

import java.util.ArrayList;
import java.util.List;

public class GitHubMetaDataProvider {

    private GitHubMetaDataProvider() {}

    static List<GitHubMetaData> getMetaDataList() {
        final List<GitHubMetaData> metaDataList = new ArrayList<>();
        metaDataList.add(new GitHubMetaData("freeCodeCamp", 391000, 35900, 8500));
        metaDataList.add(new GitHubMetaData("free-programming-books", 324000, 60200, 9700));
        return metaDataList;
    }

}
