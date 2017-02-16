package uk.co.akm.cert.model;

import java.math.BigInteger;

/**
 * Created by mavroidt on 11/02/2017.
 */
public interface X509CertificatePublicKeyView {

    String getAlgorithm();

    String getFormat();

    int getNumberOfBytes();

    String getHex();

    String getBase64();

    String getSHA1Hex();

    String getSHA1Base64();

    String getModulusBytesHex();

    String getModulusSHA1Hex();

    String getModulusSHA1Base64();

    BigInteger getPublicExponent();

    BigInteger getModulus();
}
