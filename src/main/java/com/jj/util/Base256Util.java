package com.jj.util;

public class Base256Util {

    public static final Integer QUANTIDADE = 256;

    private Base256Util() {
        // Garante que a classe não seja instanciada.
    }

    public static Integer toInteger(int[] valor) {

        Double result = 0.0;

        for (int i = 0; i < valor.length; i++) {
            result = result + (int) (valor[i]) * Math.pow(QUANTIDADE, valor.length - (i + i));
        }

        return result.intValue();
    }

}
