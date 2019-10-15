package com.jj.util;

public class ChaveValorVO {
    private Object valor;
    private Object chave;

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public Object getChave() {
        return chave;
    }

    public void setChave(Object chave) {
        this.chave = chave;
    }

    public ChaveValorVO(Object valor, Object chave) {
        super();
        this.valor = valor;
        this.chave = chave;
    }

    public ChaveValorVO() {

    }

}
