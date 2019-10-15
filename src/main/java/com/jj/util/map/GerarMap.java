package com.jj.util.map;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.jj.util.IntegerUtil;

public class GerarMap {

    private GerarMap() {
    }

    public static Map<String, String> gerarMapString(String chave, String valor) {

        Map<String, String> map = new TreeMap<>();
        map.put(chave, valor);
        return map;
    }

    /**
     * Ex: Map<String, String> requestProperty = GerarMap.gerarMapString(GerarMap.value("browserInfo",browserInfo),GerarMap.value("sistema",sistema));
     * 
     * @param values
     * @return
     */
    public static Map<String, String> gerarMapString(String[]... values) {
        Map<String, String> map = new HashMap<>();
        for (String[] strings : values) {
            if (strings.length == IntegerUtil.DOIS) {

                map.put(strings[0], strings[1]);

            }
        }
        return map;
    }

    public static String[] value(String chave, Object valor) {

        if (valor != null) {
            return new String[] { chave, valor.toString() };
        } else {
            return new String[] {};
        }
    }
}
