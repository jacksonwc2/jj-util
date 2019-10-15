package com.jj.util.enumerador;

public enum HttpMethod {
    PUT("PUT"), GET("GET"), POST("POST"), DELETE("DELETE");

    private final String name;

    private HttpMethod(final String s) {
        name = s;
    }

    public String toString() {
        return name;
    }
}
