package de.claudio.altamura.java.digital.signature;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static de.claudio.altamura.java.digital.signature.SignatureHelper.getCurrentRelativePathAsString;

public class KeytoolBasedExample {

    public static PrivateKey loadPrivateKey() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(new FileInputStream(getCurrentRelativePathAsString() + "/digital-signature/example_keystore.p12"), "changeit".toCharArray());
        return (PrivateKey) keyStore.getKey("exampleKeyPair", "changeit".toCharArray());
    }

    public static PublicKey loadPublicKey() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(new FileInputStream(getCurrentRelativePathAsString() + "/digital-signature/receiver_keystore.p12"), "changeit".toCharArray());
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate("exampleKeyPair");
        return certificate.getPublicKey();
    }

    public static void main(String[] args) throws IOException, UnrecoverableKeyException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
        // 1. Load private key for signing
        PrivateKey privateKey = loadPrivateKey();
        PublicKey publicKey = loadPublicKey();
    }
}
