package com.jj.util;

/*Contador utilizado para lambdas*/
public class ContadorUtil {

    private Long contador;
    private final Long steep;

    public ContadorUtil(Long contador, Long steep) {
        this.contador = contador;
        this.steep = steep;
    }

    public ContadorUtil() {
        contador = LongUtil.ZERO;
        steep = LongUtil.UM;
    }

    public Long next() {
        contador = contador + steep;
        return contador;
    }

    public Long back() {
        contador = contador - steep;
        return contador;
    }

    public Long getValue() {
        return contador;
    }

    public void setValorInicial(Long contador) {
        this.contador = contador;
    }

}