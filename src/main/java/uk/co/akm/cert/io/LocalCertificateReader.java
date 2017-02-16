package uk.co.akm.cert.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Created by mavroidt on 11/02/2017.
 */
public final class LocalCertificateReader {

    public Certificate readX509CertificateFromFile(String file) {
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            final CertificateFactory cf = CertificateFactory.getInstance("X.509");

            return cf.generateCertificate(inputStream);
        } catch (CertificateException ce) {
            System.err.println("Could not read X509 certificate from file '" + file + "' due to a certificate error: " + ce.getMessage());
            return null;
        } catch (FileNotFoundException fnfe) {
            System.err.print("Certificate file '" + file + "' not found.");
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignore) {}
            }
        }
    }
}
