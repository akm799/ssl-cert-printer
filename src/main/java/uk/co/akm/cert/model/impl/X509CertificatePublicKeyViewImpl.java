package uk.co.akm.cert.model.impl;

import uk.co.akm.cert.model.X509CertificatePublicKeyView;
import uk.co.akm.cert.util.DigestEvaluator;
import uk.co.akm.cert.util.Formatter;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by mavroidt on 11/02/2017.
 */
final class X509CertificatePublicKeyViewImpl implements X509CertificatePublicKeyView {
    private static final int PUBLIC_KEY_MODULUS_BYTES_START_INDEX = 33;

    private final PublicKey publicKey;
    private final byte[] modulusBytes;

    X509CertificatePublicKeyViewImpl(PublicKey publicKey) {
        this.publicKey = publicKey;
        this.modulusBytes = extractModulusBytes(publicKey.getEncoded());
    }

    private byte[] extractModulusBytes(byte[] publicKeyBytes) {
        final int publicKeyModulusLength = (publicKeyBytes.length - PUBLIC_KEY_MODULUS_BYTES_START_INDEX);
        final byte[] publicKeyModulusBytes = new byte[publicKeyModulusLength];
        System.arraycopy(publicKeyBytes, PUBLIC_KEY_MODULUS_BYTES_START_INDEX, publicKeyModulusBytes, 0, publicKeyModulusLength);

        return publicKeyModulusBytes;
    }

    public String getAlgorithm() {
        return publicKey.getAlgorithm();
    }

    public String getFormat() {
        return publicKey.getFormat();
    }

    public int getNumberOfBytes() {
        return publicKey.getEncoded().length;
    }

    public String getHex() {
        return Formatter.toHex(publicKey.getEncoded());
    }

    public String getBase64() {
        return Formatter.toBase64(publicKey.getEncoded());
    }

    public String getSHA1Hex() {
        final byte[] sha1 = DigestEvaluator.sha1(publicKey.getEncoded());
        return Formatter.toHex(sha1);
    }

    public String getSHA1Base64() {
        final byte[] sha1 = DigestEvaluator.sha1(publicKey.getEncoded());
        return Formatter.toBase64(sha1);
    }

    public String getModulusBytesHex() {
        return Formatter.toHex(modulusBytes);
    }

    public String getModulusSHA1Hex() {
        final byte[] sha1 = DigestEvaluator.sha1(modulusBytes);
        return Formatter.toHex(sha1);
    }

    public String getModulusSHA1Base64() {
        final byte[] sha1 = DigestEvaluator.sha1(modulusBytes);
        return Formatter.toBase64(sha1);
    }

    public BigInteger getPublicExponent() {
        if (publicKey instanceof RSAPublicKey) {
            return ((RSAPublicKey)publicKey).getPublicExponent();
        } else {
            return null;
        }
    }

    public BigInteger getModulus() {
        if (publicKey instanceof RSAPublicKey) {
            return ((RSAPublicKey)publicKey).getModulus();
        } else {
            return null;
        }
    }
}
