package de.claudio.altamura.java.digital.signature;

import java.nio.file.*;
import java.security.*;
import java.util.Base64;

public class DigitalSignatureExample {
    // Schlüsselpaar erzeugen
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    // Datei-Hash erstellen
    public static byte[] getFileHash(String filePath) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        return digest.digest(fileContent);
    }

    // Signatur erstellen
    public static byte[] signFile(byte[] hash, PrivateKey privateKey) throws NoSuchAlgorithmException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(hash);
        return signature.sign();
    }

    // Signatur verifizieren
    public static boolean verifySignature(byte[] hash, byte[] signatureBytes, PublicKey publicKey) throws NoSuchAlgorithmException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(hash);
        System.out.println(signature.toString());
        return signature.verify(signatureBytes);
    }

    public static void main(String[] args) {
        try {
            // 1. Keypair
            KeyPair keyPair = generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // 2. Hash
            Path currentRelativePath = Paths.get("");
            String currentRelativePathAsString = currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current absolute path is: " + currentRelativePathAsString);
            String filePath = currentRelativePathAsString + "/digital-signature/example.txt";
            byte[] fileHash = getFileHash(filePath);

            // 3. Signature
            byte[] digitalSignature = signFile(fileHash, privateKey);
            String encodedSignature = Base64.getEncoder().encodeToString(digitalSignature);
            System.out.println("Digital Signature: " + encodedSignature);

            // Store
            Files.write(Paths.get(currentRelativePathAsString + "/signature.sig"), digitalSignature);

            // 4. Verify
            boolean isVerified = verifySignature(fileHash, digitalSignature, publicKey);
            System.out.println("Signature Verified: " + isVerified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
