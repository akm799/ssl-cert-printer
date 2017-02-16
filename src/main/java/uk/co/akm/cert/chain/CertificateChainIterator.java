package uk.co.akm.cert.chain;

import uk.co.akm.cert.chain.CertificateChain;

import java.security.cert.Certificate;
import java.util.Iterator;

/**
 * Created by mavroidt on 12/02/2017.
 */
public final class CertificateChainIterator<C extends Certificate> implements Iterator<C> {
    private CertificateChain<C> chain;

    public CertificateChainIterator(CertificateChain<C> root) {
        this.chain = root;
    }

    public boolean hasNext() {
        return (chain != null);
    }

    public C next() {
        final C certificate = chain.getCertificate();
        chain = chain.getChild();

        return certificate;
    }
}
