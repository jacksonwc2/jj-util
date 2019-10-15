package com.jj.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.logging.Logger;

/**
 * Essa classe trata-se de um utilitário para converter uma String em MD5.
 *
 */
public final class ConversorMd5 {
    public static final String SECRET_KEY = "jj$#_clpfcc";

    private ConversorMd5() {
        // Garante que a classe não seja instanciada.
    }

    /**
     * Converte uma valor texto em formato MD5.
     * 
     * @param valor
     * @return Valor no formato MD5.
     */
    public static String converterStringParaMd5(String valor) {

        if (valor.isEmpty()) {
            return null;
        }

        Logger logger = Logger.getLogger(ConversorMd5.class.getName());
        logger.fine(">>Executando o método converterStringParaMd5.");

        String retornoMD5 = null;

        try {

            logger.fine("Adquirindo o algoritmo MD5.");
            MessageDigest md = MessageDigest.getInstance("MD5");

            logger.fine("Convertendo a valor texto para o formato MD5");
            BigInteger hash = new BigInteger(1, md.digest(valor.getBytes("UTF-8")));

            retornoMD5 = String.format("%1$032x", hash);

        } catch (Exception e) {

            logger.severe("Não foi possível converter o valor em formato MD5: " + e.getMessage());
        }

        logger.fine("Retornando o valor no formato MD5.");
        return retornoMD5;
    }

    public static String converterStringParaMd5jj(String valor) {

        if (StringUtil.isNullOrEmpty(valor)) {
            return null;
        }

        Logger logger = Logger.getLogger(ConversorMd5.class.getName());
        logger.fine(">>Executando o método converterStringParaMd5.");

        String retornoMD5 = null;

        try {
            valor = SECRET_KEY.concat(valor);
            logger.fine("Adquirindo o algoritmo MD5.");
            MessageDigest md = MessageDigest.getInstance("MD5");

            logger.fine("Convertendo a valor texto para o formato MD5");
            BigInteger hash = new BigInteger(1, md.digest(valor.getBytes("UTF-8")));

            retornoMD5 = String.format("%1$032x", hash);

        } catch (Exception e) {

            logger.severe("Não foi possível converter o valor em formato MD5: " + e.getMessage());
        }

        logger.fine("Retornando o valor no formato MD5.");
        return retornoMD5;
    }

    public static boolean detectarMD5(String valor) {

        if (StringUtil.isVazio(valor)) {
            return false;
        }

        return valor.matches("[0-9a-f]{31,32}");
    }
}