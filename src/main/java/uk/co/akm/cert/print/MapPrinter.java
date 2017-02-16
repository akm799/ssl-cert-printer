package uk.co.akm.cert.print;

import java.io.PrintStream;

/**
 * Created by mavroidt on 12/02/2017.
 */
public interface MapPrinter<T> {

    void print(T data, PrintStream out);
}
