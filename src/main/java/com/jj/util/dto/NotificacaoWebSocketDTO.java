package com.jj.util.dto;

import java.io.Serializable;

public class NotificacaoWebSocketDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer chaveSessao;

    private String mensagem;

    private String flagVisivel;

    public Integer getChaveSessao() {
        return chaveSessao;
    }

    public void setChaveSessao(Integer chaveSessao) {
        this.chaveSessao = chaveSessao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getFlagVisivel() {
        return flagVisivel;
    }

    public void setFlagVisivel(String flagVisivel) {
        this.flagVisivel = flagVisivel;
    }

}
