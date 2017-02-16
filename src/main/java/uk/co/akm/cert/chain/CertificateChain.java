package uk.co.akm.cert.chain;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * Created by mavroidt on 11/02/2017.
 */
public interface CertificateChain<C extends Certificate> {

    boolean isRoot();

    boolean isLeaf();

    C getCertificate();

    CertificateChain<C> getParent();

    CertificateChain<C> getChild();
}
