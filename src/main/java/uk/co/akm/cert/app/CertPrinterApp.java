package uk.co.akm.cert.app;

import uk.co.akm.cert.chain.CertificateChain;
import uk.co.akm.cert.chain.CertificateChainIterator;
import uk.co.akm.cert.chain.x509.X509CertificateChainAssembler;
import uk.co.akm.cert.io.LocalCertificateReader;
import uk.co.akm.cert.io.RemoteCertificateReader;
import uk.co.akm.cert.model.X509CertificateView;
import uk.co.akm.cert.model.impl.X509CertificateViewFactory;
import uk.co.akm.cert.print.MapPrinter;
import uk.co.akm.cert.print.impl.MapPrinterFactory;

import java.security.cert.*;
import java.util.Iterator;

/**
 * Created by mavroidt on 06/10/2016.
 */
public class CertPrinterApp {
    private static final int N_ARGS_1 = 2;
    private static final int N_ARGS_2 = 4;
    private static final int ARG_TYPE_INDEX = 0;
    private static final int ARG_SRC_INDEX = 1;
    private static final int ARG_PROXY_HOST_INDEX = 2;
    private static final int ARG_PROXY_PORT_INDEX = 3;

    enum SourceType {HOST, FILE}

    public static void main(String[] args) {
        final CertPrinterApp certPrinter = new CertPrinterApp();

        if (args.length == N_ARGS_1) {
            final SourceType type = findType(args[ARG_TYPE_INDEX].trim());
            final String source = args[ARG_SRC_INDEX].trim();
            if (type == null || source.length() == 0) {
                help();
            } else {
                switch (type) {
                    case HOST:
                        certPrinter.printCertsFromHost(source, null, 0);
                        break;

                    case FILE:
                        certPrinter.printCertsFromFile(source);
                        break;

                    default:
                        help();
                        break;
                }
            }
        } else if (args.length == N_ARGS_2) {
            final SourceType type = findType(args[ARG_TYPE_INDEX].trim());
            final String source = args[ARG_SRC_INDEX].trim();
            if (type == SourceType.HOST) {
                try {
                    final String proxyHost = args[ARG_PROXY_HOST_INDEX];
                    final int proxyPort = Integer.parseInt(args[ARG_PROXY_PORT_INDEX]);
                    certPrinter.printCertsFromHost(source, proxyHost, proxyPort);
                } catch (NumberFormatException nfe) {
                    help("Invalid proxy port number '" + args[ARG_PROXY_PORT_INDEX] + "'.");
                }
            } else {
                help();
            }
        } else {
            help();
        }
    }

    private static SourceType findType(String typeStr) {
        for (SourceType type : SourceType.values()) {
            if (type.name().equalsIgnoreCase(typeStr)) {
                return type;
            }
        }

        return null;
    }

    private static void help() {
        help(null);
    }

    private static void help(String errorMessage) {
        if (errorMessage != null) {
            System.err.println("Error: " + errorMessage);
        }
        System.out.println("java CertPrinterApp host <hostName>");
        System.out.println("java CertPrinterApp host <hostName> <proxyHostName> <proxyPortNumber>");
        System.out.println("java CertPrinterApp file <file_path>");
    }

    private void printCertsFromHost(String hostName, String proxyHost, int proxyPort) {
        final Certificate[] certs;
        final RemoteCertificateReader reader = new RemoteCertificateReader();
        if (proxyHost == null) {
            certs = reader.fetchCertificatesFromHost(hostName);
        } else {
            certs = reader.fetchCertificatesFromHostViaProxy(hostName, proxyHost, proxyPort);
        }

        final CertificateChain<X509Certificate> chain = (new X509CertificateChainAssembler()).assembleChain(certs);

        System.out.println("Public key hashes for certificate chain:");
        printChainKeyHashes(chain);

        System.out.println("\n\nCertificate chain:\n");
        printChain(chain);
    }

    private void printCertsFromFile(String file) {
        final LocalCertificateReader reader = new LocalCertificateReader();
        final Certificate certificate = reader.readX509CertificateFromFile(file);
        System.out.println("Certificate information from file " + file + " :\n\n");
        printCert(certificate);
    }

    private void printChain(CertificateChain<X509Certificate> root) {
        final StringBuilder padding = new StringBuilder();

        final Iterator<X509Certificate> iterator = new CertificateChainIterator<X509Certificate>(root);
        while (iterator.hasNext()) {
            printX509Cert(padding.toString(), iterator.next());
            padding.append("  ");
            System.out.println("");
        }
    }

    private void printCert(Certificate cert) {
        if (cert instanceof X509Certificate) {
            printX509Cert("", (X509Certificate)cert);
        } else {
            System.out.println("Non X509 certificate encountered: " + cert.getClass());
        }
    }

    private void printChainKeyHashes(CertificateChain<X509Certificate> root) {
        final Iterator<X509Certificate> iterator = new CertificateChainIterator<X509Certificate>(root);
        while (iterator.hasNext()) {
            final X509CertificateView view = X509CertificateViewFactory.buildX509CertificateView(iterator.next());
            System.out.println("sha1/" + view.getPublicKeyView().getSHA1Base64());
        }
    }

    private void printX509Cert(String padding, X509Certificate cert) {
        final X509CertificateView view = X509CertificateViewFactory.buildX509CertificateView(cert);
        final MapPrinter certificatePrinter = MapPrinterFactory.x509CertificatePrinter(padding, ":");
        certificatePrinter.print(view, System.out);
    }
}
