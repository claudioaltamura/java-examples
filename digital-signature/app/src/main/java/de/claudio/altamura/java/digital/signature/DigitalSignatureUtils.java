package de.claudio.altamura.java.digital.signature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

class DigitalSignatureUtils {

    private DigitalSignatureUtils() {}

    static byte[] getFileHash(String filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        return digest.digest(fileContent);
    }

    static byte[] signFile(byte[] hash, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(hash);
        return signature.sign();
    }

    static boolean verifySignature(byte[] hash, byte[] signatureBytes, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(hash);
        return signature.verify(signatureBytes);
    }

}
