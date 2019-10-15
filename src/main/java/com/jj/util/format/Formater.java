package com.jj.util.format;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jj.util.IntegerUtil;
import com.jj.util.StringUtil;

public class Formater {

    private Formater() {

    }

    public static boolean rengEx(String rengEx, Object value) {

        Pattern pattern = Pattern.compile(rengEx);
        Matcher matcher = pattern.matcher(value.toString());
        return matcher.matches();

    }

    public static String telefone(String telefone) {
        String numeroUtil = null;
        String numero = telefone.replaceAll("[^0-9]", "");

        switch (StringUtil.contaCaracteres(numero)) {
            case 11:
                numeroUtil = "(" + numero.substring(0, 2) + ")";
                numeroUtil = numeroUtil.concat(numero.substring(2, 3) + " ");
                numeroUtil = numeroUtil.concat(numero.substring(3, 7) + "-");
                numeroUtil = numeroUtil.concat(numero.substring(7, 11));
                return numeroUtil;

            case 10:
                numeroUtil = "(" + numero.substring(0, 2) + ")";
                numeroUtil = numeroUtil.concat(numero.substring(2, 6) + "-");
                numeroUtil = numeroUtil.concat(numero.substring(6, 10));
                return numeroUtil;

            case 9:
                numeroUtil = (numero.substring(0, 1) + " ");
                numeroUtil = numeroUtil.concat(numero.substring(1, 5) + "-");
                numeroUtil = numeroUtil.concat(numero.substring(5, 9));
                return numeroUtil;

            case 8:
                numeroUtil = (numero.substring(0, 4) + "-");
                numeroUtil = numeroUtil.concat(numero.substring(4, 8));
                return numeroUtil;

            default:
                return telefone;

        }
    }

    /**
     * Método para formatar string<br>
     * 
     * Ex: de utilizacao<br>
     * > Formater.formatString("teste %s teste %s", "01", "02")<br>
     * 
     * retornara: "teste 01 teste 02"
     */
    public static String formatString(String msg, Object... args) {
        return String.format(msg, args);
    }

    /**
     * Método para formatar numero da versao<br>
     */
    public static String numeroVersao(String versao) {
        String retVersao = null;
        String numero = versao.replaceAll("[^0-9]", "");

        if (StringUtil.contaCaracteres(numero) == IntegerUtil.OITO) {
            retVersao = StringUtil.STRING_VAZIA;
            retVersao = retVersao.concat(numero.substring(0, 1) + ".");
            retVersao = retVersao.concat(numero.substring(1, 3) + ".");
            retVersao = retVersao.concat(numero.substring(3, 5) + ".");
            retVersao = retVersao.concat(numero.substring(5, 8));
        }

        return StringUtil.isNullOrEmpty(retVersao) ? versao : retVersao;

    }

    /**
     * Remove tudo que que nao faz parte do link. <br>
     * Ex http://jj.com.br/intranet/user -> http://jj.com.b <br>
     * https://jj.com.br/intranet/user -> https://jj.com.br
     */
    public static String tratarLinkUrl(String link) {
        String pattern = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(link);

        if (matcher.find()) {
            return matcher.group(0);
        }
        return StringUtil.STRING_VAZIA;
    }

}
