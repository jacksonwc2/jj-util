package com.jj.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalUtil {

    private static final int DUAS_CASAS_DECIMAIS = 2;
    private static final String PONTO = ".";
    private static final String VAZIO = "";
    private static final String VIRGULA = ",";
    public static final int MODO_ARREDONDAMENTO_PADRAO_SISTEMA = BigDecimal.ROUND_HALF_EVEN;

    public static final BigDecimal ZERO = new BigDecimal(0);
    public static final BigDecimal UM = new BigDecimal(1);
    public static final BigDecimal DOIS = new BigDecimal(2);
    public static final BigDecimal CINCO = new BigDecimal(5);
    public static final BigDecimal DEZ = new BigDecimal(10);
    public static final BigDecimal VINTE = new BigDecimal(20);
    public static final BigDecimal VINTE_E_UM = new BigDecimal(21);
    public static final BigDecimal TRINTA = new BigDecimal(30);
    public static final BigDecimal TRINTA_E_UM = new BigDecimal(31);
    public static final BigDecimal QUARENTA = new BigDecimal(40);
    public static final BigDecimal QUARENTA_E_UM = new BigDecimal(41);
    public static final BigDecimal CINQUENTA = new BigDecimal(50);
    public static final BigDecimal CINQUENTA_E_UM = new BigDecimal(51);
    public static final BigDecimal CEM = new BigDecimal(100);
    public static final BigDecimal CENTO_E_VINTE = new BigDecimal(120);
    public static final BigDecimal CENTO_E_TRINTA_NEGATIVO = new BigDecimal(-130);
    public static final BigDecimal SALARIO_MINIMO = new BigDecimal(880);
    public static final BigDecimal PRECO_MAXIMO = new BigDecimal(999999.99);
    public static final BigDecimal MEIO = new BigDecimal(0.5);
    /**
     * Constante para arredondamento utilizado nas procedures do S1<br>
     * Valor: 0.490
     */
    public static final BigDecimal CONSTANTE_ARREDONDAMENTO = new BigDecimal(0.490);

    /**
     * Constante para arredondamento utilizado nas procedures do S1<br>
     * Valor: 0.00999999
     */
    public static final BigDecimal CONSTANTE_ARREDONDAMENTO_PRECO = new BigDecimal(0.00999999);

    public static final BigDecimal UMA_HORA = new BigDecimal(3600000);

    public static final BigDecimal UMA_HORA_SEGUNDOS = new BigDecimal(3600);

    public static final BigDecimal SESSENTA = new BigDecimal(60);

    public static final BigDecimal SETENTA = new BigDecimal(70);

    public static final BigDecimal MIL = new BigDecimal(1000);

    public static final BigDecimal MARGEM_MAXIMA_CLD = new BigDecimal(99.99, MathContext.DECIMAL32);

    private BigDecimal valor;

    private BigDecimalUtil() {
    }

    /**
     * Compara se o valor1 é igual ao valor2.
     * 
     * @param valor1
     * @param valor2
     * @return
     */
    public static Boolean igual(BigDecimal valor1, BigDecimal valor2) {

        return valor1.compareTo(valor2) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Compara se o valor1 é diferente do valor2.
     * 
     * @param valor1
     * @param valor2
     * @return
     */
    public static boolean diferente(BigDecimal valor1, BigDecimal valor2) {

        return valor1.compareTo(valor2) == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * Compara se o valor1 é maior ou igual ao valor2.
     * 
     * @param valor1
     * @param valor2
     * @return
     */
    public static Boolean maiorOuIgual(BigDecimal valor1, BigDecimal valor2) {
        return valor1.compareTo(valor2) > 0 ? Boolean.TRUE : valor1.compareTo(valor2) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Compara se o valor1 é menor ou igual ao valor2.
     * 
     * @param valor1
     * @param valor2
     * @return
     */
    public static Boolean menorOuIgual(BigDecimal valor1, BigDecimal valor2) {
        return valor1.compareTo(valor2) < 0 ? Boolean.TRUE : valor1.compareTo(valor2) == 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Compara se o valor1 é maior que o valor2.
     * 
     * @param valor1
     * @param valor2
     * @return
     */
    public static Boolean maior(BigDecimal valor1, BigDecimal valor2) {
        return valor1.compareTo(valor2) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Boolean maiorQueZero(BigDecimal valor1) {
        return IfNull.get(valor1, BigDecimalUtil.ZERO).compareTo(BigDecimalUtil.ZERO) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Compara se o valor1 é menor que o valor2.
     * 
     * @param valor1
     * @param valor2
     * @return
     */
    public static Boolean menor(BigDecimal valor1, BigDecimal valor2) {
        return valor1.compareTo(valor2) < 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Divide valor1 por valor2 com escala de 2 casas decimais
     * 
     * @param valor1
     * @param valor2
     * @return BigDecimal
     */
    public static BigDecimal divide(BigDecimal valor1, BigDecimal valor2) {
        return divide(valor1, valor2, DUAS_CASAS_DECIMAIS);
    }

    /**
     * Divide valor1 por valor2 com escala informada por parametro.
     * 
     * @param valor1
     * @param valor2
     * @param escala
     * @return BigDecimal
     */
    public static BigDecimal divide(BigDecimal valor1, BigDecimal valor2, int escala, int roundingMode) {
        BigDecimal retorno = ZERO;

        if (diferente(valor1, BigDecimalUtil.ZERO)) {
            retorno = valor1.divide(valor2, java.math.MathContext.DECIMAL64).setScale(escala, roundingMode);
        }

        return retorno;
    }

    public static BigDecimal divide(BigDecimal valor1, BigDecimal valor2, int escala) {
        return divide(valor1, valor2, escala, RoundingMode.HALF_EVEN.ordinal());

    }

    public static boolean isNullOrZero(BigDecimal valor) {
        return (valor == null || valor.compareTo(BigDecimal.ZERO) == 0) ? true : false;
    }

    public static boolean isNull(BigDecimal valor) {
        return valor == null;
    }

    /**
     * Arredondamento padrão nas procedures do S1 Delphi.<br>
     * Exemplo:<br>
     * SELECT cast( 10.45 as numeric(9,0));<br>
     * Resultado: 10<br>
     * SELECT cast( 10.50 as numeric(9,0));<br>
     * Resultado: 11
     * 
     * @param valor
     * @return
     */
    public static BigDecimal roundS1(BigDecimal valor) {
        return valor.setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Cria instância do objeto para atribuir um valor não estático.
     * 
     * @param value
     * @return
     */
    public static BigDecimalUtil valor(BigDecimal value) {
        BigDecimalUtil bigDecimalUtil = new BigDecimalUtil();
        bigDecimalUtil.setValor(value);
        return bigDecimalUtil;
    }

    /**
     * Permite recuperar o valor não estático.
     * 
     * @return
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Permite atribuir um valor não estático.
     * 
     * @param valor
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Verifica se o valor está entre determinados valores. <br>
     * Ex:<br>
     * Boolean retorno = BigDecimalUtil.valor(CEM).between(ZERO, CENTO_E_VINTE)
     * 
     * @param valor1
     * @param valor2
     * @return
     */
    public boolean between(BigDecimal valor1, BigDecimal valor2) {

        return maiorOuIgual(this.getValor(), valor1) && menorOuIgual(this.getValor(), valor2);
    }

    /**
     * Atribui escala padrão
     * 
     * @param valor
     * @return
     */
    public static BigDecimal setScale(BigDecimal valor) {

        return valor.setScale(DUAS_CASAS_DECIMAIS, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal valueOf(String number) {
        if (number.isEmpty()) {
            return null;
        }
        return BigDecimal.valueOf(Double.valueOf(number.replace(PONTO, VAZIO).replace(VIRGULA, PONTO)));
    }

    public static BigDecimal valueOf(Double value) {
        return BigDecimal.valueOf(value).setScale(DUAS_CASAS_DECIMAIS, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal divide(Long l, BigDecimal valor2) {

        return BigDecimalUtil.divide(BigDecimal.valueOf(l), valor2);
    }

    public static BigDecimal divide(BigDecimal valor1, Long valor2) {
        return BigDecimalUtil.divide(valor1, BigDecimal.valueOf(valor2));
    }

    /**
     * Atribui escala padrão
     * 
     * @param valor
     * @return
     */
    public static BigDecimal divideDown(BigDecimal valor1, BigDecimal valor2) {
        return valor1.divide(valor2, IntegerUtil.QUATRO, RoundingMode.DOWN).setScale(IntegerUtil.DOIS, RoundingMode.HALF_DOWN);
    }

    public static boolean menorQueZero(BigDecimal valor) {
        if (valor == null) {
            return Boolean.FALSE;
        }
        return valor.compareTo(BigDecimalUtil.ZERO) < 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    public static BigDecimal percentage(BigDecimal base, BigDecimal pct, int escala) {

        BigDecimal percentage = BigDecimal.ZERO;

        if (diferente(pct, BigDecimal.ZERO)) {

            percentage = divide(base.multiply(pct).setScale(escala, RoundingMode.HALF_EVEN), CEM, escala);
        }

        return percentage;
    }

    public static BigDecimal percentage(BigDecimal base, BigDecimal pct) {

        return percentage(base, pct, DUAS_CASAS_DECIMAIS);

    }

    public static boolean valorEntre(BigDecimal porcentagem, BigDecimal valorInicio, BigDecimal valorFim) {
        return maiorOuIgual(porcentagem, valorInicio) && menorOuIgual(porcentagem, valorFim);
    }

    public static BigDecimal menos(BigDecimal valor1, BigDecimal valor2) {

        return menos(valor1, valor2, DUAS_CASAS_DECIMAIS);
    }

    public static BigDecimal menos(BigDecimal valor1, BigDecimal valor2, Integer escala) {

        BigDecimal retorno = ZERO;

        if (diferente(valor1, valor2)) {

            retorno = valor1.subtract(valor2, new MathContext(escala));
        }

        return retorno;

    }

    public static BigDecimal trunc(BigDecimal valor, int size) {

        return valor.setScale(size, BigDecimal.ROUND_DOWN);
    }

}
