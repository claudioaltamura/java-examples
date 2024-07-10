package de.claudio.altamura.java.digital.signature;

import java.nio.file.*;
import java.security.*;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.claudio.altamura.java.digital.signature.SignatureHelper.getCurrentRelativePathAsString;
import static de.claudio.altamura.java.digital.signature.DigitalSignatureUtils.*;

public class GeneratedKeyExample {

    private static final Logger logger = LoggerFactory.getLogger(GeneratedKeyExample.class);

    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    public static void main(String[] args) {
        try {
            // 1. Keypair
            KeyPair keyPair = generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // 2. Hash
            var currentRelativePathAsString = getCurrentRelativePathAsString();
            String filePath = currentRelativePathAsString + "/digital-signature/example.txt";
            byte[] fileHash = getFileHash(filePath);

            // 3. Signature
            byte[] digitalSignature = signFile(fileHash, privateKey);
            String encodedSignature = Base64.getEncoder().encodeToString(digitalSignature);
            logger.info("Digital Signature: {}", encodedSignature);

            // Store
            Files.write(Paths.get(currentRelativePathAsString + "/signature.sig"), digitalSignature);

            // 4. Verify
            boolean isVerified = verifySignature(fileHash, digitalSignature, publicKey);
            logger.info("Signature Verified: {}", isVerified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
