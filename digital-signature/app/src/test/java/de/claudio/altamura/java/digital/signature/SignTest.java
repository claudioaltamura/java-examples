package de.claudio.altamura.java.digital.signature;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import static de.claudio.altamura.java.digital.signature.DigitalSignatureUtils.*;
import static de.claudio.altamura.java.digital.signature.SignatureHelper.getCurrentRelativePathAsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignTest {

    @Test
    void shouldVerifySignatureSuccessfully() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        var currentRelativePathAsString = getCurrentRelativePathAsString();
        String filePath = currentRelativePathAsString + "/../example.txt";

        byte[] fileHash = getFileHash(filePath);
        byte[] digitalSignature = signFile(fileHash, keyPair.getPrivate());

        assertTrue(verifySignature(fileHash, digitalSignature, keyPair.getPublic()));
    }
}