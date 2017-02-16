package uk.co.akm.cert.print.impl;

import uk.co.akm.cert.model.X509CertificatePublicKeyView;
import uk.co.akm.cert.model.X509CertificateView;

/**
 * Created by mavroidt on 12/02/2017.
 */
final class X509CertificatePrinter extends AbstractMapPrinter<X509CertificateView> {

    X509CertificatePrinter(String padding, String titleEnd) {
        super(padding, titleEnd);
    }

    @Override
    final void init(X509CertificateView data) {
        add("subject", data.getName());
        add("issuer", data.getIssuerName());
        add("serial", data.getSerial());
        add("serial (hex)", data.getSerialHex());
        add("expiry", data.getExpiry());
        add("Certificate SHA1 fingerprint", data.getSHA1Hex());
        add("Certificate MD5  fingerprint", data.getMD5Hex());

        final X509CertificatePublicKeyView keyData = data.getPublicKeyView();
        add("key algorithm", keyData.getAlgorithm());
        add("key encoding format", keyData.getFormat());
        add("number of bytes in key", keyData.getNumberOfBytes());
        add("key (Hex)", keyData.getHex());
        add("key (Base 64)", keyData.getBase64());
        add("key SHA1 hash (Hex)", keyData.getSHA1Hex());
        add("key SHA1 hash (Base 64)", keyData.getSHA1Base64());
        add("RSA public key modulus bytes (Hex)", keyData.getModulusBytesHex());
        add("RSA public key modulus bytes SHA1 hash (Hex)", keyData.getModulusSHA1Hex());
        add("RSA public key modulus bytes SHA1 hash (Base 64)", keyData.getModulusSHA1Hex());
        add("RSA public key public exponent", keyData.getPublicExponent());
        add("RSA public key modulus", keyData.getModulus());
    }
}
