package com.jj.util;

public class EANUtil {

    private EANUtil() {
        // Garante que a classe não seja instanciada.
    }

    public static Boolean isCodigoPesavel(String codigoProduto) {
        Boolean pesavel = Boolean.FALSE;
        if (codigoProduto.length() == LongUtil.TREZE && codigoProduto.substring(0, 1).equals(LongUtil.DOIS.toString())) {
            pesavel = Boolean.TRUE;
        }
        return pesavel;
    }

    public static Boolean isEAN13Valid(String codigoBarras) {
        Boolean retorno = Boolean.FALSE;
        if (codigoBarras.length() == LongUtil.TREZE) {
            String digitoVerificador = codigoBarras.substring(codigoBarras.length() - 1, codigoBarras.length());
            String digitoCalculado = calcularDigitoVerificador(codigoBarras.substring(0, codigoBarras.length() - 1));
            if (digitoVerificador.equals(digitoCalculado)) {
                retorno = Boolean.TRUE;
            }
        }
        return retorno;
    }

    public static String calcularDigitoVerificador(String codigoBarra) {
        int[] numeros = codigoBarra.chars().map(Character::getNumericValue).toArray();
        int somaPares = numeros[IntegerUtil.UM] + numeros[IntegerUtil.TRES] + numeros[IntegerUtil.CINCO] + numeros[IntegerUtil.SETE]
                + numeros[IntegerUtil.NOVE] + numeros[IntegerUtil.ONZE];
        int somaImpares = numeros[IntegerUtil.ZERO] + numeros[IntegerUtil.DOIS] + numeros[IntegerUtil.QUATRO] + numeros[IntegerUtil.SEIS]
                + numeros[IntegerUtil.OITO] + numeros[IntegerUtil.DEZ];
        int resultado = somaImpares + (somaPares * IntegerUtil.TRES);

        int somaMultipla = resultado;

        while ((somaMultipla % IntegerUtil.DEZ) != IntegerUtil.ZERO) {
            somaMultipla++;
        }

        int digitoVerificador = somaMultipla - resultado;

        return String.valueOf(digitoVerificador);
    }

}
