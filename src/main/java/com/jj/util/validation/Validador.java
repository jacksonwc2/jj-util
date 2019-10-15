package com.jj.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jj.util.LongUtil;
import com.jj.util.StringUtil;
import com.jj.util.exception.BusinessServerException;

public class Validador {

    public static final String PADRAO_YYYY_HIFEN_MM_HIFEN_DD_HH_MM_VALIDO = "([0-9]{4})-([0-1][0-9])-([0-3][0-9]) ([0-2][0-9]):([0-5][0-9])";

    private Validador() {

    }

    public static boolean validaCpf(String cpf) {
        return Cpf.validar(cpf);
    }

    public static boolean rengEx(String rengEx, Object value) {

        Pattern pattern = Pattern.compile(rengEx);
        Matcher matcher = pattern.matcher(value.toString());
        return matcher.matches();

    }

    public static boolean validaCnpj(String cnpj) {
        return Cnpj.validar(cnpj);
    }

    public static boolean isNumber(Object value) {
        String numberPattern = "^(([0-9]*)|(([0-9]*).([0-9]*)))$";
        if (value != null && rengEx(numberPattern, value) && !value.toString().isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean validaEmail(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }

    /**
     * Verifica se o documento é valido (cnpj ou cpf)
     * 
     * @param numeroDocumento numero do CPF ou CNPJ
     * @param cpf TRUE para cpf e FALSE para cnpj
     * @return TRUE Caso o numero enviado é valido
     */
    public static Boolean validaCPFCNPJ(String numeroDocumento, Boolean cpf) {

        if (cpf) {
            return Cpf.validar(StringUtil.formataZeroCPFCNPJ(numeroDocumento, cpf));
        } else {
            return Cnpj.validar(StringUtil.formataZeroCPFCNPJ(numeroDocumento, cpf));
        }

    }

    /**
     * Formata o CPF ou CNPJ e retorna caso algum dos dois esteja valido
     * 
     * @param numeroDocumento
     * @return
     */
    public static String formataCPFCNPJ(String numeroDocumento) {
        Boolean cpfValido = validaCPFCNPJ(numeroDocumento, Boolean.TRUE);
        if (cpfValido) {
            return StringUtil.formataZeroCPFCNPJ(numeroDocumento, Boolean.TRUE);
        } else {
            return validaCPFCNPJ(numeroDocumento, Boolean.FALSE) ? StringUtil.formataZeroCPFCNPJ(numeroDocumento, Boolean.FALSE) : "";
        }

    }

    /**
     * valida o CPF ou CNPJ e retorna TRUE caso algum dos dois esteja valido
     * 
     * @param numeroDocumento cpf/cnpj
     * @return True caso o documento é validado para cnpj ou cpf
     */
    public static Boolean validaCPFCNPJ(String numeroDocumento) {
        Boolean cpfValido = validaCPFCNPJ(numeroDocumento, Boolean.TRUE);

        if (!cpfValido) {
            cpfValido = validaCPFCNPJ(numeroDocumento, Boolean.FALSE);
        }

        return cpfValido;
    }

    /**
     * Valida numero do celular, se é um numero do celular.
     * 
     * @param celular
     * @return
     * @throws BusinessServerException
     */
    public static Boolean validaCelular(String celular) throws BusinessServerException {

        if (StringUtil.isNullOrEmpty(celular)) {
            return Boolean.FALSE;
        }

        if (StringUtil.replaceNaoNumeros(celular).length() < LongUtil.ONZE) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

}
