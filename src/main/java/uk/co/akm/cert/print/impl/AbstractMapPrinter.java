package uk.co.akm.cert.print.impl;

import uk.co.akm.cert.print.MapPrinter;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by mavroidt on 12/02/2017.
 */
abstract class AbstractMapPrinter<T> implements MapPrinter<T> {
    private String padding = "";
    private String titleEnd = "";
    private final Map<String, String> map = new LinkedHashMap<String, String>();

    private int maxTitleLength;
    private final Map<Integer, String > rhsMargins = new HashMap<Integer, String>();

    AbstractMapPrinter(String padding, String titleEnd) {
        setPadding(padding);
        setTitleEnd(titleEnd);
    }

    private void setPadding(String padding) {
        if (padding == null) {
            this.padding = "";
        } else {
            this.padding = padding;
        }
    }

    private void setTitleEnd(String titleEnd) {
        if (titleEnd != null) {
            this.titleEnd = titleEnd.trim();
        }
    }

    final void add(String title, Object value) {
        if (title == null) {
            title = "";
        }

        if (value == null) {
            value = "";
        }

        title = title.trim();
        value = value.toString().trim();

        if (title.length() > maxTitleLength) {
            maxTitleLength = title.length();
        }

        map.put(title, value.toString());
    }

    public final void print(T data, PrintStream out) {
        init(data);
        print(out);
    }

    abstract void init(T data);

    private void print(PrintStream out) {
        for (Map.Entry<String, String> row : map.entrySet()) {
            printRow(row, out);
        }
    }

    private void printRow(Map.Entry<String, String> row, PrintStream out) {
        final String fullTitle = buildTitle(row.getKey());
        out.print(fullTitle);
        out.println(row.getValue());
    }

    private String buildTitle(String titleValue) {
        final StringBuilder sb = new StringBuilder(padding.length() + maxTitleLength + 2);

        if (padding.length() > 0) {
            sb.append(padding);
        }

        sb.append(titleValue);

        final String rhsMargin = getRhsMargin(titleValue);
        if (rhsMargin != null) {
            sb.append(rhsMargin);
        }

        if (titleEnd.length() > 0) {
            sb.append(titleEnd);
        }

        sb.append(' ');

        return sb.toString();
    }

    private String getRhsMargin(String title) {
        final int marginLength = maxTitleLength - title.length();
        if (marginLength == 0) {
            return null;
        }

        return getRhsMargin(marginLength);
    }

    private String getRhsMargin(Integer marginLength) {
        if (!rhsMargins.containsKey(marginLength)) {
            rhsMargins.put(marginLength, buildRhsMargin(marginLength));
        }

        return rhsMargins.get(marginLength);
    }

    private String buildRhsMargin(int maxTitleLength) {
        final char[] whiteSpaces = new char[maxTitleLength];
        Arrays.fill(whiteSpaces, ' ');

        return new String(whiteSpaces);
    }
}
