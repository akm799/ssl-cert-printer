package uk.co.akm.cert.chain.x509;

import uk.co.akm.cert.chain.AbstractCertificateChain;
import uk.co.akm.cert.chain.CertificateChain;

import java.security.cert.X509Certificate;

/**
 * Created by mavroidt on 11/02/2017.
 */
public final class X509CertificateChain extends AbstractCertificateChain<X509Certificate> {

    public X509CertificateChain(X509Certificate certificate) {
        super(certificate);
    }

    @Override
    protected CertificateChain<X509Certificate> instance(X509Certificate cert) {
        return new X509CertificateChain(cert);
    }
}
