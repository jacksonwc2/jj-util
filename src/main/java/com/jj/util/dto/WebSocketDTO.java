package com.jj.util.dto;

import java.io.Serializable;

public class WebSocketDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long codigoNotificacao;

    private Long codigoTipoNotificacao;

    private Long codigoUsuario;

    private String titulo;

    private String dominioSistema;

    private String protocolo;

    private String versaoFacade;

    private String mensagem;

    private String conteudo;

    private String hashNotificacao;

    private String flagVisivel;

    public Long getCodigoTipoNotificacao() {
        return codigoTipoNotificacao;
    }

    public void setCodigoTipoNotificacao(Long codigoTipoNotificacao) {
        this.codigoTipoNotificacao = codigoTipoNotificacao;
    }

    public Long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDominioSistema() {
        return dominioSistema;
    }

    public void setDominioSistema(String dominioSistema) {
        this.dominioSistema = dominioSistema;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getHashNotificacao() {
        return hashNotificacao;
    }

    public void setHashNotificacao(String hashNotificacao) {
        this.hashNotificacao = hashNotificacao;
    }

    public String getFlagVisivel() {
        return flagVisivel;
    }

    public void setFlagVisivel(String flagVisivel) {
        this.flagVisivel = flagVisivel;
    }

    public String getVersaoFacade() {
        return versaoFacade;
    }

    public void setVersaoFacade(String versaoFacade) {
        this.versaoFacade = versaoFacade;
    }

    public Long getCodigoNotificacao() {
        return codigoNotificacao;
    }

    public void setCodigoNotificacao(Long codigoNotificacao) {
        this.codigoNotificacao = codigoNotificacao;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

}
