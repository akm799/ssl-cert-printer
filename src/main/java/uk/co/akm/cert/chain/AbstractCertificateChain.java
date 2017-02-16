package uk.co.akm.cert.chain;

import java.security.cert.Certificate;

/**
 * Created by mavroidt on 11/02/2017.
 */
public abstract class AbstractCertificateChain<C extends Certificate> implements CertificateChain<C> {
    private CertificateChain<C> parent;
    private CertificateChain<C> child;
    private final C certificate;

    protected AbstractCertificateChain(C certificate) {
        this.certificate = certificate;
    }

    public final AbstractCertificateChain addChild(C cert) {
        child = instance(cert);
        ((AbstractCertificateChain)child).parent = this;

        return (AbstractCertificateChain<C>)child;
    }

    protected abstract CertificateChain<C> instance(C cert);

    public final boolean isRoot() {
        return (parent == null);
    }

    public final boolean isLeaf() {
        return (child == null);
    }

    public final C getCertificate() {
        return certificate;
    }

    public final CertificateChain<C> getParent() {
        return parent;
    }

    public final CertificateChain<C> getChild() {
        return child;
    }
}
