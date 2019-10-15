package com.jj.util.validation;

import com.jj.util.IntegerUtil;

public class Cnpj {
    private static final int[] SEQUENCIA_CALCULO_PRIMEIRO_DIGITO = { 6, 7, 8, 9, 2, 3, 4, 5, 6, 7, 8, 9 };
    private static final int[] SEQUENCIA_CALCULO_SEGUNDO_DIGITO = { 5, 6, 7, 8, 9, 2, 3, 4, 5, 6, 7, 8, 9 };

    private Cnpj() {

    }

    public static Boolean validar(String valor) {

        String cnpj = valor;

        cnpj = removerCaracteres(cnpj);

        if (estaComFormatoInvalido(cnpj)) {
            return false;
        }

        int primeiroDigitoVerificador;
        int segundoDigitoVerificador;
        String digitos = cnpj.substring(IntegerUtil.ZERO, IntegerUtil.DOZE);
        String digitosVerificador = cnpj.substring(IntegerUtil.DOZE);

        primeiroDigitoVerificador = calcularPrimeiroDigitoVerificador(digitos);
        segundoDigitoVerificador = calcularSegundoDigitoVerificador(digitos + primeiroDigitoVerificador);

        String digitosVerificadorCalculado = new StringBuilder().append(primeiroDigitoVerificador).append(segundoDigitoVerificador).toString();

        return digitosVerificador.equals(digitosVerificadorCalculado);
    }

    private static String removerCaracteres(String cnpj) {
        return cnpj.replaceAll("-", "").replaceAll("/", "").replaceAll("\\.", "").replaceAll(",", "").trim();
    }

    private static boolean estaComFormatoInvalido(String cnpj) {
        return cnpj.length() != IntegerUtil.QUATORZE;
    }

    private static Integer calcularPrimeiroDigitoVerificador(String digitos) {
        int soma = 0;
        int digitoVerificador;
        int multiplicador;
        int digito;
        for (int i = 0; i < IntegerUtil.DOZE; i++) {
            multiplicador = SEQUENCIA_CALCULO_PRIMEIRO_DIGITO[i];
            digito = Character.getNumericValue(digitos.charAt(i));
            soma += digito * multiplicador;
        }
        digitoVerificador = soma % IntegerUtil.ONZE;
        digitoVerificador = digitoVerificador >= IntegerUtil.DEZ ? IntegerUtil.ZERO : digitoVerificador;
        return digitoVerificador;
    }

    private static Integer calcularSegundoDigitoVerificador(String digitos) {
        int soma = 0;
        int digitoVerificador;
        int digito;
        int multiplicador;
        for (int i = 0; i < IntegerUtil.TREZE; i++) {
            multiplicador = SEQUENCIA_CALCULO_SEGUNDO_DIGITO[i];
            digito = Character.getNumericValue(digitos.charAt(i));
            soma += digito * multiplicador;
        }
        digitoVerificador = soma % IntegerUtil.ONZE;
        digitoVerificador = digitoVerificador >= IntegerUtil.DEZ ? IntegerUtil.ZERO : digitoVerificador;
        return digitoVerificador;
    }

}