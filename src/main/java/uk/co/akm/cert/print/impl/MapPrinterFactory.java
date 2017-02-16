package uk.co.akm.cert.print.impl;

import uk.co.akm.cert.print.MapPrinter;

/**
 * Created by mavroidt on 12/02/2017.
 */
public class MapPrinterFactory {

    public static MapPrinter x509CertificatePrinter(String padding, String titleEnd) {
        return new X509CertificatePrinter(padding, titleEnd);
    }

    private MapPrinterFactory() {}
}
