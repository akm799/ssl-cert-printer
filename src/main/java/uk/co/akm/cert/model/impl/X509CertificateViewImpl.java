package uk.co.akm.cert.model.impl;

import uk.co.akm.cert.model.X509CertificatePublicKeyView;
import uk.co.akm.cert.model.X509CertificateView;
import uk.co.akm.cert.util.DigestEvaluator;
import uk.co.akm.cert.util.Formatter;

import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

/**
 * Created by mavroidt on 11/02/2017.
 */
final class X509CertificateViewImpl implements X509CertificateView {
    private final X509Certificate certificate;
    private final X509CertificatePublicKeyView publicKeyView;

    X509CertificateViewImpl(X509Certificate certificate) {
        this.certificate = certificate;
        this.publicKeyView = new X509CertificatePublicKeyViewImpl(certificate.getPublicKey());
    }

    public String getName() {
        return certificate.getSubjectDN().getName();
    }

    public String getIssuerName() {
        final String issuerName = certificate.getIssuerDN().getName();
        return (issuerName.equals(getName()) ? "SELF" : issuerName);
    }

    public BigInteger getSerial() {
        return certificate.getSerialNumber();
    }

    public String getSerialHex() {
        return Formatter.toHex(getSerial());
    }

    public String getExpiry() {
        return certificate.getNotAfter().toString();
    }

    public String getSHA1Hex() {
        try {
            final byte[] sha1 = DigestEvaluator.sha1(certificate.getEncoded());
            return Formatter.toHex(sha1);
        } catch (CertificateEncodingException cee) {
            System.err.println("Certificate encoding error: " + cee.getMessage());
            return null;
        }
    }

    public String getMD5Hex() {
        try {
            final byte[] md5 = DigestEvaluator.md5(certificate.getEncoded());
            return Formatter.toHex(md5);
        } catch (CertificateEncodingException cee) {
            System.err.println("Certificate encoding error: " + cee.getMessage());
            return null;
        }
    }

    public X509CertificatePublicKeyView getPublicKeyView() {
        return publicKeyView;
    }
}
