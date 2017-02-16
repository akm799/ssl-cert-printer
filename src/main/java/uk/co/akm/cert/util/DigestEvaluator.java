package uk.co.akm.cert.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mavroidt on 11/02/2017.
 */
public class DigestEvaluator {

    public static byte[] sha1(byte[] bytes) {
        return getDigestBytes(bytes, "SHA-1");
    }

    public static byte[] md5(byte[] bytes) {
        return getDigestBytes(bytes, "MD5");
    }

    private static byte[] getDigestBytes(byte[] bytes, String algorithm) {
        try {
            final MessageDigest md = MessageDigest.getInstance(algorithm);
            return md.digest(bytes);
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Invalid message util algorithm '" + algorithm + "'.");
            nsae.printStackTrace();
            return null;
        }
    }

    private DigestEvaluator() {}
}
