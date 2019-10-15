package com.jj.util;

public enum TimeLogger {
    MINUTES("MINUTES"), MILLISECONDS("SECONDS"), SECONDS("MILLISECONDS");

    private final String stringValue;

    private TimeLogger(final String s) {
        stringValue = s;
    }

    public String toString() {
        return stringValue;
    }

}
