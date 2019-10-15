package com.jj.util.enumerador;

public enum Platform {
    APPLE("iOS"), ANDROID("Android"), WINDOWS("Win32NT");

    private final String name;

    private Platform(final String s) {
        name = s;
    }

    public static Platform enumById(Long codigoPlataforma) {
        switch (codigoPlataforma.toString()) {
            case "1":
                return ANDROID;
            case "2":
                return APPLE;
            case "3":
                return WINDOWS;
            default:
                return ANDROID;
        }
    }

    public String toString() {
        return name;
    }
}
