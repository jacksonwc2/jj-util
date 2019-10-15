package com.jj.util.exception;

public class ExceptionRestVO {

    private String codigo;

    private String mensagem;

    private String mensagemTecnica;

    public ExceptionRestVO() {
    }

    public ExceptionRestVO(String codigo, String mensagemTecnica, String mensagem) {

        this.codigo = codigo;
        this.mensagemTecnica = mensagemTecnica;
        this.mensagem = mensagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagemTecnica() {
        return mensagemTecnica;
    }

    public void setMensagemTecnica(String mensagemTecnica) {
        this.mensagemTecnica = mensagemTecnica;
    }
}
