package uk.co.akm.cert.model.impl;

import uk.co.akm.cert.model.X509CertificateView;

import java.security.cert.X509Certificate;

/**
 * Created by mavroidt on 11/02/2017.
 */
public class X509CertificateViewFactory {

    public static X509CertificateView buildX509CertificateView(X509Certificate certificate) {
        return new X509CertificateViewImpl(certificate);
    }

    private X509CertificateViewFactory() {}
}
