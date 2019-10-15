package com.jj.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {
    private static Json instance;
    private static Gson gson;

    /**
     * Configura o Gson
     */
    private Json() {
        gson = new GsonBuilder().serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();
    }

    /**
     * Instancia Singleton da Classe
     * 
     * @return
     */
    public static Json getInstance() {
        if (instance == null) {
            instance = new Json();
        }
        return instance;
    }

    public <T> T fromJson(Class<T> t, String json) {
        return (T) gson.fromJson(json, t);
    }

    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    @SuppressWarnings("unchecked")
    public <T> T fromJson(Type listType, String json) {
        return (T) gson.fromJson(json, listType);
    }
}
