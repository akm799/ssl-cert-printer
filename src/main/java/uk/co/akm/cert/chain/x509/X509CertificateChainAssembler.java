package uk.co.akm.cert.chain.x509;

import uk.co.akm.cert.chain.AbstractCertificateChain;
import uk.co.akm.cert.chain.AbstractCertificateChainAssembler;
import uk.co.akm.cert.chain.CertificateChain;

import java.security.cert.X509Certificate;

/**
 * Created by mavroidt on 11/02/2017.
 */
public final class X509CertificateChainAssembler extends AbstractCertificateChainAssembler<X509Certificate> {

    @Override
    protected Class<X509Certificate[]> getConcreteCertificateClass() {
        return X509Certificate[].class;
    }

    @Override
    protected boolean isRoot(X509Certificate cert) {
        return cert.getSubjectDN().getName().equals(cert.getIssuerDN().getName());
    }

    @Override
    protected CertificateChain<X509Certificate> initCertificateChain(X509Certificate rootCert) {
        return new X509CertificateChain(rootCert);
    }

    @Override
    protected boolean isParent(X509Certificate possibleParent, X509Certificate possibleChild) {
        return possibleParent.getSubjectDN().getName().equals(possibleChild.getIssuerDN().getName());
    }

    @Override
    protected CertificateChain<X509Certificate> addChild(CertificateChain<X509Certificate> parent, X509Certificate child) {
        return ((AbstractCertificateChain<X509Certificate>)parent).addChild(child);
    }
}
