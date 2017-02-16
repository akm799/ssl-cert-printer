package uk.co.akm.cert.model;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by mavroidt on 11/02/2017.
 */
public interface X509CertificateView {

    String getName();

    String getIssuerName();

    BigInteger getSerial();

    String getSerialHex();

    String getExpiry();

    String getSHA1Hex();

    String getMD5Hex();

    X509CertificatePublicKeyView getPublicKeyView();
}
