package uk.co.akm.cert.chain;


import java.security.cert.Certificate;
import java.util.Arrays;

/**
 * Created by mavroidt on 11/02/2017.
 */
public abstract class AbstractCertificateChainAssembler<C extends Certificate> {

    protected AbstractCertificateChainAssembler() {}

    public final CertificateChain<C> assembleChain(Certificate[] certs) {
        final C[] concreteCertificates = Arrays.copyOf(certs, certs.length, getConcreteCertificateClass());

        return assembleConcreteChain(concreteCertificates);
    }

    protected abstract Class<C[]> getConcreteCertificateClass();

    private CertificateChain<C> assembleConcreteChain(C[] certs) {
        final C rootCert = findRoot(certs);
        if (rootCert == null) {
            return null;
        }

        final CertificateChain<C> root = initCertificateChain(rootCert);
        findAndAddChild(root, certs);

        return root;
    }

    private C findRoot(C[] certs) {
        final C selfIssued = findSelfIssued(certs);

        return (selfIssued == null ? findOrphan(certs) : selfIssued);
    }

    private C findSelfIssued(C[] certs) {
        for (C cert: certs) {
            if (isRoot(cert)) {
                return cert;
            }
        }

        return null;
    }

    protected abstract boolean isRoot(C cert);

    private C findOrphan(C[] certs) {
        for (C cert : certs) {
            if (hasNoParent(cert, certs)) {
                return cert;
            }
        }

        return null;
    }

    private boolean hasNoParent(C cert, C[] certs) {
        for (C possibleParent : certs) {
            if (!cert.equals(possibleParent)) {
                if (isParent(possibleParent, cert)) {
                    return false;
                }
            }
        }

        return true;
    }

    protected abstract CertificateChain<C> initCertificateChain(C rootCert);

    private void findAndAddChild(CertificateChain<C> current, C[] certs) {
        for (C cert: certs) {
            if (hasDifferentCertificate(current, cert)) {
                if (isParent(current, cert)) {
                    final CertificateChain<C> child = addChild(current, cert);
                    findAndAddChild(child, certs); // Recursive call
                }
            }
        }
    }

    private boolean hasDifferentCertificate(CertificateChain<C> currentLevel, C certificate) {
        return !(currentLevel.getCertificate().equals(certificate));
    }

    private boolean isParent(CertificateChain<C> possibleParent, C possibleChild) {
        return isParent((C)possibleParent.getCertificate(), possibleChild);
    }

    protected abstract boolean isParent(C possibleParent, C possibleChild);

    protected abstract CertificateChain<C> addChild(CertificateChain<C> parent, C child);
}
