[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# java-examples
Java Examples

## Java keytool

generate

    keytool -genkeypair -alias exampleKeyPair -keyalg RSA -keysize 2048 \
    -dname "CN=Altamura" -validity 365 -storetype pkcs12 \
    -keystore example_keystore.p12 -storepass changeit

list

    keytool -list -v -keystore example_keystore.p12

delete

    keytool -delete -alias exampleKeyPair -keystore example_keystore.p12

export

    keytool -exportcert -alias exampleKeyPair -storetype pkcs12 \
    -keystore example_keystore.p12 -file \
    example_certificate.cer -rfc -storepass changeit

create a certificate signing request

    keytool -certreq -alias exampleKeyPair -storetype pkcs12 \
    -keystore example_keystore.p12 -file -rfc \
    -storepass changeit > example_certificate.csr