package com.jj.util.validation;

import java.util.Arrays;
import java.util.List;

import com.jj.util.IntegerUtil;

public class Cpf {

    private static final List<String> FORMATO_INVALIDO = Arrays.asList("00000000000", "11111111111", "22222222222", "33333333333", "44444444444",
            "55555555555", "66666666666", "77777777777", "88888888888", "99999999999");

    private Cpf() {

    }

    public static Boolean validar(String valor) {

        String cpf = valor;

        cpf = removerCaracteres(cpf);

        if (estaComFormatoInvalido(cpf)) {
            return false;
        }

        int primeiroDigitoVerificador;
        int segundoDigitoVerificador;
        String digitos = cpf.substring(0, IntegerUtil.NOVE);
        String digitosVerificador = cpf.substring(IntegerUtil.NOVE);

        primeiroDigitoVerificador = calcularPrimeiroDigitoVerificador(digitos);
        segundoDigitoVerificador = calcularSegundoDigitoVerificador(digitos + primeiroDigitoVerificador);

        StringBuilder digitosVerificadorCalculado = new StringBuilder().append(primeiroDigitoVerificador).append(segundoDigitoVerificador);

        return digitosVerificador.equals(digitosVerificadorCalculado.toString());
    }

    private static String removerCaracteres(String cpf) {
        return cpf.replaceAll("-", "").replaceAll("/", "").replaceAll("\\.", "").replaceAll(",", "").trim();
    }

    private static boolean estaComFormatoInvalido(String cpf) {
        return FORMATO_INVALIDO.contains(cpf) || cpf.length() != IntegerUtil.ONZE;
    }

    private static Integer calcularPrimeiroDigitoVerificador(String digitos) {
        int soma = IntegerUtil.ZERO;
        int digitoVerificador;
        int digito;
        int multiplicador;
        for (int i = IntegerUtil.ZERO; i < IntegerUtil.NOVE; i++) {
            multiplicador = i + IntegerUtil.UM;
            digito = Character.getNumericValue(digitos.charAt(i));
            soma += digito * multiplicador;
        }
        digitoVerificador = soma % IntegerUtil.ONZE;
        digitoVerificador = digitoVerificador >= IntegerUtil.DEZ ? 0 : digitoVerificador;
        return digitoVerificador;
    }

    private static Integer calcularSegundoDigitoVerificador(String digitos) {
        int soma = 0;
        int digitoVerificador;
        int digito;
        int multiplicador;
        for (int i = 0; i < IntegerUtil.DEZ; i++) {
            multiplicador = i;
            digito = Character.getNumericValue(digitos.charAt(i));
            soma += digito * multiplicador;
        }
        digitoVerificador = soma % IntegerUtil.ONZE;
        digitoVerificador = digitoVerificador >= IntegerUtil.DEZ ? 0 : digitoVerificador;
        return digitoVerificador;
    }

}