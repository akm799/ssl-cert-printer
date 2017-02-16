package uk.co.akm.cert.util;

import java.math.BigInteger;
import java.util.Base64;

/**
 * Created by mavroidt on 11/02/2017.
 */
public class Formatter {

    public static String toBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String toHex(BigInteger value) {
        final String hex = value.toString(16).toUpperCase();
        return insertSpaces(hex);
    }

    private static String insertSpaces(String hex) {
        final StringBuilder sb = new StringBuilder(4*hex.length()/3 + 1);
        for (int i=0 ; i<hex.length() ; i++) {
            sb.append(hex.charAt(i));
            if (i%2 != 0) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    public static String toHex(byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            final String s = Integer.toHexString(b & 0x000000FF).toUpperCase();
            if (s.length() == 1) {
                sb.append('0');
            }
            sb.append(s);
            sb.append(' ');
        }

        return sb.toString();
    }

    private Formatter() {}
}
