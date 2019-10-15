package com.jj.util;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public final class CNPJUtil {

    private static final int TAMANHO_CNPJ = 14;

    private CNPJUtil() {
        // Garante que a classe não seja instanciada.
    }

    public static String formatar(String cnpj) {
        String numeroCNPJ = cnpj.replaceAll("[^0123456789]", "");

        while (StringUtil.contaCaracteres(numeroCNPJ) < TAMANHO_CNPJ) {
            numeroCNPJ = "0".concat(numeroCNPJ);
        }

        MaskFormatter mask;
        try {
            mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(numeroCNPJ);
        } catch (ParseException e) {
            return cnpj;
        }
    }

}
