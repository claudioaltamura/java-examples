package de.claudio.altamura.java.digital.signature;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SignatureHelper {

    private SignatureHelper() {}

    static String getCurrentRelativePathAsString() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();
    }

}
