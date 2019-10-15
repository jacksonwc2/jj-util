package com.jj.util;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jj.util.validation.Validador;

/**
 * Classe responsável por possuir utilitários de validação de String.
 */
public final class StringUtil {

    private static Logger logger = Logger.getLogger(StringUtil.class.getName());

    public static final String STRING_VAZIA = "";
    public static final String SEPARADOR_BARRA = " / ";
    public static final String CODIGO_DA_EMPRESA = "CÓDIGO DA EMPRESA";
    public static final String CODIGO_DO_USUARIO = "CÓDIGO DO USUÁRIO";
    public static final String STATUS = "STATUS";
    public static final String DATA_INICIO = "DATA INICIAL";
    public static final String DATA_FIM = "DATA FINAL";
    public static final String VIRGULA = ",";
    public static final String ASPAS_SIMPLES = "'";
    public static final String ALIAS_QUERY = "query";
    public static final String STRING_ESPACO = " ";
    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";
    public static final String ZERO = "0";

    public static final String NAO_INFORMADO = "NÃO INFORMADO";

    public static final String TRACO = "-";
    public static final String UNDERLINE = "_";

    private StringUtil() {

    }

    /**
     * Valida uma String para verificar se ela é vazia.
     *
     * @param valor Valor String à ser analisado.
     * @return Retorna <b>true</b> se o valor informado for nulo ou vazio absoluto. <br>
     *         Retorna <b>false</b> se o valor informado não for nulo ou possui pelo menos 1 caracter.
     *         <p/>
     *         Obs.: Caso a variável possua espaços nas extremidades, esses serão removídos para a análise.
     */
    public static Boolean isVazio(String valor) {

        if (valor == null || StringUtil.STRING_VAZIA.equals(valor.trim())) {

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Remove aspas duplas das strings.
     *
     * @param valor Valor String à ser analisado.
     * @return Retorna <b>string</b> sem aspas.
     *         <p/>
     */
    public static StringBuilder removeCaracteresInvalidos(StringBuilder stringBuilder) {
        String valor = "";
        if (stringBuilder != null) {
            valor = stringBuilder.toString().replaceAll("[\"\']", "");
        }
        StringBuilder sb = new StringBuilder();
        return sb.append(valor);
    }

    /**
     * Remove espaços em brancos de Strings
     *
     * @param valor Valor String à ser analisado.
     * @return Retorna <b>string</b> sem espaços.
     *         <p/>
     */
    public static String removeEspacos(String valor) {
        if (valor == null) {
            return valor;
        } else {
            return valor.trim();
        }
    }

    /**
     * Remove espaços em brancos de Strings e tranforma para minusculo
     *
     * @param valor Valor String à ser analisado.
     * @return Retorna <b>string</b> sem espaços em brancos e em minusculo.
     *         <p/>
     */
    public static String transformaMininusculo(String valor) {
        String retorno = removeEspacos(valor);
        if (retorno != null) {
            return retorno.toLowerCase();
        } else {
            return STRING_VAZIA;
        }
    }

    /**
     * Conta quantidade de caracteres absoluto
     *
     * @param valor Valor String à ser analisado.
     * @return Retorna <b>Integer</b> quantidade de caracteres absoluto
     *         <p/>
     */
    public static Integer contaCaracteres(String valor) {
        String retorno = removeEspacos(valor);
        if (retorno != null) {
            return retorno.length();
        } else {
            return 0;
        }
    }

    /**
     * Verificar se a String for nula retornar vazio
     *
     * @param valor : Valor String à ser analisado.
     * @return retorna: String
     */
    public static String tratarNulo(String valor) {
        if (valor == null) {
            return STRING_VAZIA;
        }
        return valor;
    }

    public static boolean isNumericList(String valor) {
        if (valor.contains(",")) {
            String[] valores = valor.split(",");
            for (String item : valores) {
                if (!isNumeric(item)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isNumeric(String atributo) {
        try {
            Double.valueOf(atributo);
            return true;
        } catch (Exception e) {
            logger.fine("O valor não é um numérico" + e.getMessage());
            return false;
        }
    }

    public static String listToString(List<?> value) {
        String list = value.toString();
        list = list.replace("[", "");
        list = list.replace("]", "");
        return list;
    }

    public static String primeiraMaiscula(String palavra) {
        return palavra.substring(0, 1).toUpperCase().concat(palavra.substring(1));
    }

    public static String primeiraMinuscula(String palavra) {
        return palavra.substring(0, 1).toLowerCase().concat(palavra.substring(1));
    }

    public static boolean isNullOrEmpty(String texto) {

        return IfNull.get(texto, STRING_VAZIA).equals(STRING_VAZIA);
    }

    /**
     * Truncates trunca a string para o minimo do seu length ou para o valor informado.
     **/
    public static String truncate(String s, int len) {
        if (s == null) {
            return null;
        }
        return s.substring(0, Math.min(len, s.length()));
    }

    /**
     * Retorna uma lista dos caracteres que representam uma configuração. <br>
     * Este tipo de configuração é comum na aplicação delphi, por exemplo "NL" é uma configuração onde existe dois caracters concatenados.
     * 
     * @param valor
     * @return
     */
    public static List<String> toCaractersList(String valor) {

        return Arrays.asList(valor.split(""));
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(String valor) {

        String replace = valor.replace("[", "");
        replace = replace.replace("]", "");
        replace = replace.replace("\"", "");
        String[] codigos = replace.toUpperCase().split(",");

        List<T> lista = new ArrayList<T>();
        for (String item : codigos) {
            lista.add((T) item);
        }
        return lista;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> listS1toList(String valor) {

        String replace = valor.replace("<", "");
        replace = replace.replace(">", "");
        String[] codigos = replace.toUpperCase().split(",");

        List<T> lista = new ArrayList<T>();
        for (String item : codigos) {
            lista.add((T) item);
        }
        return lista;
    }

    public static String padLeft(String stringToPad, Integer padToLength) {
        String retValue;
        if (stringToPad.length() < padToLength) {
            retValue = String.format("%0" + (padToLength - stringToPad.length()) + "d%s", IntegerUtil.ZERO, stringToPad);
        } else {
            retValue = stringToPad;
        }
        return retValue;
    }

    public static String primeiraLetraMaiusculo(String palavra) {

        return palavra.substring(0, 1).toUpperCase().concat(palavra.substring(1).toLowerCase());

    }

    public static String trim(String string) {
        if (isNullOrEmpty(string)) {
            return STRING_VAZIA;
        }
        return string.trim();
    }

    public static Boolean isNumeric(Class<?> type, String valoPesquisa) {
        try {
            Constructor<?> constructor = type.getConstructor(String.class);
            constructor.newInstance(valoPesquisa);
            return Boolean.TRUE;

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | InstantiationException e) {
            return Boolean.FALSE;
        }
    }

    public static String replaceNaoNumeros(String valor) {
        return valor.replaceAll("[^0-9]", "");
    }

    public static String formataMoeda(BigDecimal valor, Integer casas, Boolean simbolo) {
        NumberFormat nf;
        if (simbolo) {
            nf = NumberFormat.getCurrencyInstance();
        } else {
            nf = NumberFormat.getNumberInstance();
        }
        nf.setMaximumFractionDigits(casas);
        nf.setMinimumFractionDigits(casas);

        return nf.format(valor);
    }

    public static String formataMoeda(BigDecimal valor) {
        return formataMoeda(valor, IntegerUtil.DOIS, Boolean.FALSE);
    }

    @SuppressWarnings("unchecked")
    /* Médodo que quebra o array de chave e valor que vem da tela e devolve uma lista de valores que pode ser convertida para o valor esperado */
    public static <T> List<T> quebrarArrayCodigos(Class<T> retorno, String chaves) {
        List<T> valores = new ArrayList<T>();

        String[] split = chaves.replace("<", "").replace(">", "").split(",");

        for (String codigo : split) {
            PropertyEditor editor = PropertyEditorManager.findEditor(retorno);
            editor.setAsText(codigo);
            valores.add((T) editor.getValue());
        }
        return valores;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> convertList(Class<T> retorno, List<String> valor) {

        List<T> valores = new ArrayList<T>();

        for (String codigo : valor) {
            PropertyEditor editor = PropertyEditorManager.findEditor(retorno);
            editor.setAsText(codigo.trim());
            valores.add((T) editor.getValue());
        }

        return valores;

    }

    public static String contarRegistros(Integer multiplicador, Long threads, Long registrosPorThread, Long totalRegistros) {

        Integer totalProcessado = ((Double) ((multiplicador + 1) * threads.doubleValue() * registrosPorThread)).intValue();

        return "PROCESSANDO REGISTROS : " + (totalProcessado.intValue() > totalRegistros ? totalRegistros : totalProcessado.intValue()) + " DE "
                + totalRegistros;
    }

    /**
     * Cria uma string para ser usado no in de selects Será pego a primeira posição do veto ex: {[1,2],[5,6]} => 1,5
     * 
     * @param strings
     * @return
     */

    public static String criarIdsIn(List<?> itensValidos) {
        StringBuilder builder = new StringBuilder();

        for (Object object : itensValidos) {

            Object id = object;

            if (!(object instanceof Number)) {

                Object[] origem = (Object[]) object;

                id = origem[0];
            }

            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(id.toString());

        }
        return builder.toString();
    }

    /**
     * Cria uma string para ser usado no in de selects Array ex: ["casa","arvore"] => 'casa','arvore'
     * 
     * @param strings
     * @return
     */
    public static String criarListaIn(List<?> strings) {
        StringBuilder builder = new StringBuilder();

        for (Object hash : strings) {

            if (builder.length() > 0) {
                builder.append(",");
            }

            builder.append("'").append(hash.toString()).append("'");

        }
        return builder.toString();
    }

    /**
     * 
     * Formata zero para CPF e CNPJ. O numero do documento é salvo com zero falso na base.
     * 
     * @param cpfCnpj
     * @param pessoaFisica TRUE PessoaFisica(CPF) e FALSE Pessoa Júridica(CNPJ)
     * @return
     */
    public static String formataZeroCPFCNPJ(String cpfCnpj, Boolean pessoaFisica) {

        if (pessoaFisica) {
            return padLeft(cpfCnpj, 11);

        } else {
            return padLeft(cpfCnpj, 14);
        }
    }

    /**
     * Envia um documento e ele formatara o documento e validara se ele for J - Juridico e F - Fisico
     * 
     * @param cnpjCpf cnpj ou cpf
     * @return
     */
    public static String verificaTipoPessoa(String cnpjCpf) {
        return Validador.validaCPFCNPJ(cnpjCpf, cnpjCpf.length() == LongUtil.ONZE) ? "F" : "J";
    }

    /**
     * Ofusca o email informado, desconsiderando as duas primeiras letras e o domínio do email
     * 
     * @param dadoContatoOfuscado StringBuilder dos dados.
     * @param email Email para ser ofuscado
     */
    public static String ofuscarEmail(String email) {

        StringBuilder dadoContatoOfuscado = new StringBuilder();

        // Separa o email em duas partes. A primeira parte ignora os dois primeiros caracteres (que não serão ofuscados) e vai até o @ A segunda parte vai do @ até o final do email

        String primeiraParte = email.substring(2, email.indexOf('@'));
        String segundaParte = email.substring(email.indexOf('@'), email.length());

        // Adiciona as duas primeiras letras que não são ofuscadas
        dadoContatoOfuscado.append(email.charAt(0));
        dadoContatoOfuscado.append(email.charAt(1));

        // Adiciona o restante do email até o @ com seu texto ofuscado
        dadoContatoOfuscado.append(primeiraParte.replaceAll("[a-zA-Z0-9_.]", "*"));

        // Adiciona a segunda parte do email
        dadoContatoOfuscado.append(segundaParte);

        return dadoContatoOfuscado.toString();
    }

    /**
     * Ofusca o celular informado. Mantém o DDD, os três primeiros números e os dois últimos números do telefone.
     * 
     * @param dadoContatoOfuscado
     * @param celular
     */
    public static String ofuscarCelular(String celular) {

        StringBuilder dadoContatoOfuscado = new StringBuilder();

        // Remove os caracteres não numéricos
        String celularSomenteNumeros = StringUtil.replaceNaoNumeros(celular);

        // Caracteres a serem ofuscados, dois antes e dois após o hífen
        String subsituirPorAsterisco = celularSomenteNumeros.substring(5, 9);

        // Adiciona os 5 primeiros caracteres
        for (int i = 0; i < 5; i++) {
            dadoContatoOfuscado.append(celularSomenteNumeros.charAt(i));
        }

        // Adiciona os caracteres ofuscados
        dadoContatoOfuscado.append(subsituirPorAsterisco.replaceAll("[0-9]", "*"));

        // Adiciona os dois últimos caracteres
        dadoContatoOfuscado.append(celularSomenteNumeros.charAt(9));
        dadoContatoOfuscado.append(celularSomenteNumeros.charAt(10));

        return dadoContatoOfuscado.toString();
    }

    /**
     * 
     * 
     * @param
     * @return senha
     */
    public static String gerarSenha() {
        return gerarSenha(LongUtil.SEIS);
    }

    /**
     * Gera uma senha aleatória da de caracteres passados, composto de letras maiúsculas e números.
     * 
     * @param quantidade
     * @return
     */
    public static String gerarSenha(Long quantidade) {
        String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O",
                "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

        StringBuilder senha = new StringBuilder();
        for (int x = 0; x < quantidade; x++) {
            int j = (int) (Math.random() * carct.length);
            senha.append(carct[j]);
        }

        return senha.toString();
    }

    public static String toCamelCase(final String init) {
        if (init == null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length() == init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

    public static boolean contains(String source, String item) {

        String pattern = "\\b" + item + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
    }

    public static String byteArrayToString(byte[] valor) {

        return new String(valor, StandardCharsets.UTF_8);
    }

    public static byte[] stringToByteArray(String valor) {

        return valor.getBytes(StandardCharsets.UTF_8);
    }

}
