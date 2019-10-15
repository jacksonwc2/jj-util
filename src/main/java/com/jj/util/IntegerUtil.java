package com.jj.util;

public class IntegerUtil {

    public static final Integer UM_NEGATIVO = -1;
    public static final Integer ZERO = 0;
    public static final Integer UM = 1;
    public static final Integer DOIS = 2;
    public static final Integer TRES = 3;
    public static final Integer QUATRO = 4;
    public static final Integer CINCO = 5;
    public static final Integer SEIS = 6;
    public static final Integer SETE = 7;
    public static final Integer OITO = 8;
    public static final Integer NOVE = 9;
    public static final Integer DEZ = 10;
    public static final Integer ONZE = 11;
    public static final Integer DOZE = 12;
    public static final Integer TREZE = 13;
    public static final Integer QUATORZE = 14;
    public static final Integer QUINZE = 15;
    public static final Integer DEZESSETE = 17;
    public static final Integer VINTE = 20;
    public static final Integer DEZOITO = 18;
    public static final Integer VINTE_TRES = 23;
    public static final Integer VINTE_QUATRO = 24;
    public static final Integer VINTE_NOVE = 29;
    public static final Integer TRINTA = 30;
    public static final Integer TRINTA_UM = 31;
    public static final Integer TRINTA_DOIS = 32;
    public static final Integer CINQUENTA_NOVE = 59;
    public static final Integer SESSENTA = 60;
    public static final Integer OITENTA = 80;
    public static final Integer CEM = 100;
    public static final Integer TREZENTOS = 300;
    public static final Integer MIL = 1000;
    public static final Integer MIL_E_VINTE_QUATRO = 1024;
    public static final Integer DUAS_CASAS = 2;
    public static final Integer NOVESSENTOS_E_NOVENTA_E_NOVE = 999;
    public static final Integer CINCO_MIL = 5000;
    public static final Integer DEZ_MIL = 10000;
    public static final Integer CENTO_E_VINTE_MIL = 120000;
    public static final Integer SEISCENTOS_MIL = 600000;
    public static final Integer DOIS_MILHOES = 2000000;

    private IntegerUtil() {
        // Garante que a classe não seja instanciada.
    }

    public static boolean isNullOrZero(Integer valor) {
        return (valor == null || valor == 0) ? true : false;
    }

    public static boolean isNullOrLessOrEqualsZero(Long valor) {
        return (valor == null || valor <= ZERO) ? true : false;
    }
}
