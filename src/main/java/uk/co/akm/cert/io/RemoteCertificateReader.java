package uk.co.akm.cert.io;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * Created by mavroidt on 11/02/2017.
 */
public final class RemoteCertificateReader {

    public Certificate[] fetchCertificatesFromHost(String hostName) {
        try {
            final URL url = new URL("https://" + hostName);

            return fetchCertificates(url);
        } catch (MalformedURLException mue) {
            System.err.println("Malformed input URL: '" + (("https://" + hostName)) + "'.");
            return null;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    private Certificate[] fetchCertificates(URL url) throws IOException {
        final HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
        conn.connect();

        return conn.getServerCertificates();
    }

    public Certificate[] fetchCertificatesFromHostViaProxy(String hostName, String proxyHost, int proxyPort) {
        try {
            final URL url = new URL("https://" + hostName);

            return fetchCertificatesViaProxy(url, proxyHost, proxyPort);
        } catch (MalformedURLException mue) {
            System.err.println("Malformed input URL: '" + (("https://" + hostName)) + "'.");
            return null;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    private Certificate[] fetchCertificatesViaProxy(URL url, String proxyHost, int proxyPort) throws IOException {
        trustAllCertificates();

        final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        final HttpsURLConnection conn = (HttpsURLConnection)url.openConnection(proxy);
        conn.connect();

        return conn.getServerCertificates();
    }

    private void trustAllCertificates() {
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                }
        };

        try {
            final SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
