package de.claudio.altamura.java.digital.signature;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SignatureHelper {

    private SignatureHelper() {}

    static String getCurrentRelativePathAsString() {
        Path currentRelativePath = Paths.get("");
        String currentRelativePathAsString = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + currentRelativePathAsString);
        return currentRelativePathAsString;
    }

}
