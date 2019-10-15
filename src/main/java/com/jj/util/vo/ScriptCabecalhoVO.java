package com.jj.util.vo;

public class ScriptCabecalhoVO {

    StringBuilder colunas = new StringBuilder();

    String cabecalho = "";

    public StringBuilder getColunas() {
        return colunas;
    }

    public void setColunas(StringBuilder colunas) {
        this.colunas = colunas;
    }

    public String getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(String cabecalho) {
        this.cabecalho = cabecalho;
    }

}
