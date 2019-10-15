package com.jj.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class LongUtil {

    public static final Long UM_NEGATIVO = -1L;
    public static final Long SETE_NEGATIVO = -7L;
    public static final Long ZERO = 0L;
    public static final Long UM = 1L;
    public static final Long DOIS = 2L;
    public static final Long TRES = 3L;
    public static final Long QUATRO = 4L;
    public static final Long CINCO = 5L;
    public static final Long SEIS = 6L;
    public static final Long SETE = 7L;
    public static final Long OITO = 8L;
    public static final Long NOVE = 9L;
    public static final Long DEZ = 10L;
    public static final Long ONZE = 11L;
    public static final Long DOZE = 12L;
    public static final Long TREZE = 13L;
    public static final Long QUATORZE = 14L;
    public static final Long QUINZE = 15L;
    public static final Long DEZESSEIS = 16L;
    public static final Long DEZESSETE = 17L;
    public static final Long DEZOITO = 18L;
    public static final Long DEZENOVE = 19L;
    public static final Long VINTE = 20L;
    public static final Long VINTE_UM = 21L;
    public static final Long VINTE_DOIS = 22L;
    public static final Long VINTE_TRES = 23L;
    public static final Long VINTE_QUATRO = 24L;
    public static final Long VINTE_CINCO = 25L;
    public static final Long VINTE_SEIS = 26L;
    public static final Long VINTE_SETE = 27L;
    public static final Long VINTE_OITO = 28L;
    public static final Long VINTE_NOVE = 29L;
    public static final Long TRINTA = 30L;
    public static final Long TRINTA_UM = 31L;
    public static final Long TRINTA_DOIS = 32L;
    public static final Long TRINTA_TRES = 33L;
    public static final Long TRINTA_QUATRO = 34L;
    public static final Long TRINTA_CINCO = 35L;
    public static final Long TRINTA_SEIS = 36L;
    public static final Long TRINTA_SETE = 37L;
    public static final Long TRINTA_OITO = 38L;
    public static final Long TRINTA_NOVE = 39L;
    public static final Long QUARENTA = 40L;
    public static final Long QUARENTA_UM = 41L;
    public static final Long QUARENTA_DOIS = 42L;
    public static final Long QUARENTA_TRES = 43L;
    public static final Long QUARENTA_QUATRO = 44L;
    public static final Long QUARENTA_CINCO = 45L;
    public static final Long QUARENTA_SEIS = 46L;
    public static final Long QUARENTA_SETE = 47L;
    public static final Long QUARENTA_OITO = 48L;
    public static final Long QUARENTA_NOVE = 49L;
    public static final Long CINQUENTA = 50L;
    public static final Long CINQUENTA_UM = 51L;
    public static final Long CINQUENTA_DOIS = 52L;
    public static final Long CINQUENTA_TRES = 53L;
    public static final Long CINQUENTA_QUATRO = 54L;
    public static final Long CINQUENTA_CINCO = 55L;
    public static final Long CINQUENTA_SEIS = 56L;
    public static final Long CINQUENTA_SETE = 57L;
    public static final Long CINQUENTA_OITO = 58L;
    public static final Long CINQUENTA_NOVE = 59L;
    public static final Long SESSENTA = 60L;
    public static final Long SESSENTA_UM = 61L;
    public static final Long SESSENTA_DOIS = 62L;
    public static final Long SESSENTA_TRES = 63L;
    public static final Long SESSENTA_QUATRO = 64L;
    public static final Long SESSENTA_CINCO = 65L;
    public static final Long SESSENTA_SEIS = 66L;
    public static final Long SESSENTA_SETE = 67L;
    public static final Long SESSENTA_OITO = 68L;
    public static final Long SESSENTA_NOVE = 69L;
    public static final Long SETENTA = 70L;
    public static final Long SETENTA_UM = 71L;
    public static final Long SETENTA_DOIS = 72L;
    public static final Long SETENTA_TRES = 73L;
    public static final Long SETENTA_QUATRO = 74L;
    public static final Long SETENTA_CINCO = 75L;
    public static final Long SETENTA_SEIS = 76L;
    public static final Long SETENTA_SETE = 77L;
    public static final Long SETENTA_OITO = 78L;
    public static final Long SETENTA_NOVE = 79L;
    public static final Long OITENTA = 80L;
    public static final Long OITENTA_UM = 81L;
    public static final Long UM_SEGUNDO = 1000L;
    public static final Long CINCO_MINUTOS = 300000L;
    public static final Long CINCO_SEGUNDOS = 5000L;
    public static final Long UMA_HORA = 3600000L;
    public static final Long UM_MINUTO = 60000L;
    public static final Long NOVENTA = 90L;
    public static final Long NOVENTA_NOVE = 99L;
    public static final Long CEM = 100L;
    public static final Long CENTO_OITO = 108L;
    public static final Long CENTO_VINTE = 120L;
    public static final Long CENTO_SESSENTA = 160L;
    public static final Long NOVECENTOS_NOVENTA_NOVE = 999L;
    public static final Long MIL = 1000L;
    public static final Long DUZENTOS = 200L;
    public static final Long QUATROCENTOS_E_CINQUENTA = 450L;
    public static final Long MIL_NOVECENTOS_E_VINTE = 1920L;
    public static final Long MIL_E_DUZENTOS = 1200L;
    public static final Long DEZ_MIL = 10000L;
    /*
     * select hashtext('SG')
     */
    public static final Long SEM_GRUPO = 1115886544L;

    private LongUtil() {
        // Garante que a classe não seja instanciada.
    }

    public static boolean isNullOrZero(Long valor) {
        return (valor == null || valor.equals(LongUtil.ZERO)) ? true : false;
    }

    public static boolean isNull(Long valor) {
        return (valor == null) ? true : false;
    }

    public static boolean isNullOrLessOrEqualsZero(Long valor) {
        return (valor == null || valor <= LongUtil.ZERO) ? true : false;
    }

    public static boolean equals(Long valorUm, Long valorDois) {
        if (valorUm != null && valorDois != null) {
            return valorUm.equals(valorDois);
        }

        return false;
    }

    public static boolean isNumber(String parametrosProdutos) {
        if (StringUtil.isNullOrEmpty(parametrosProdutos)) {
            return false;
        }
        for (char c : parametrosProdutos.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static List<Long> asLong(List<String> value) {
        try {
            List<Long> longs = new ArrayList<Long>();
            for (String strings : value) {
                longs.add(Long.valueOf(strings));
            }
            return longs;
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getNumber(String stringNumber) {
        try {
            return new BigDecimal(stringNumber.replaceAll("[^0-9,]", "").replaceAll("[^0-9]", ".")).setScale(0, BigDecimal.ROUND_UP).longValueExact();
        } catch (Exception e) {
            return null;
        }
    }
}