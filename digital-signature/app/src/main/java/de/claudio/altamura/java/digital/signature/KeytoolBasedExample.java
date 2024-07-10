package de.claudio.altamura.java.digital.signature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HexFormat;

import static de.claudio.altamura.java.digital.signature.DigitalSignatureUtils.*;
import static de.claudio.altamura.java.digital.signature.SignatureHelper.getCurrentRelativePathAsString;

public class KeytoolBasedExample {

    private static final Logger logger = LoggerFactory.getLogger(KeytoolBasedExample.class);

    static PrivateKey loadPrivateKey() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(new FileInputStream(getCurrentRelativePathAsString() + "/digital-signature/example_keystore.p12"), "changeit".toCharArray());
        return (PrivateKey) keyStore.getKey("exampleKeyPair", "changeit".toCharArray());
    }

    static PublicKey loadPublicKey() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(new FileInputStream(getCurrentRelativePathAsString() + "/digital-signature/receiver_keystore.p12"), "changeit".toCharArray());
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate("exampleKeyPair");
        //compute thumbprint
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(certificate.getEncoded());
        logger.info(HexFormat.ofDelimiter(":").formatHex(md.digest()));
        return certificate.getPublicKey();
    }

    public static void main(String[] args) throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        try {
            // 1. Keypair
            PrivateKey privateKey = loadPrivateKey();
            PublicKey publicKey = loadPublicKey();

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
