package com.jj.util;

import java.math.BigDecimal;

public class SomaUtil {
    private BigDecimal valor = BigDecimal.ZERO;

    public SomaUtil() {
        // Construtor que define a classe como pública
    }

    public SomaUtil(BigDecimal valor) {
        this.valor = valor;
    }

    public SomaUtil(Integer valor) {
        this.valor = BigDecimal.valueOf(valor);
    }

    public BigDecimal somar(BigDecimal valor) {
        this.valor = this.valor.add(valor);

        return this.valor;
    }

    public BigDecimal somar(Integer valor) {
        this.valor = this.valor.add(BigDecimal.valueOf(valor.doubleValue()));

        return this.valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
