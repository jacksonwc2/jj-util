package com.jj.util.dto;

public class WebSocketMensagemDTO {

    private Long codigoNotificacao;

    private Long codigoTipoNotificacao;

    private Long codigoUsuarioDestino;

    private String mensagem;

    private String conteudo;

    public WebSocketMensagemDTO(Long codigoNotificacao, Long codigoTipoNotificacao, Long codigoUsuarioDestino, String mensagem, String conteudo) {

        this.codigoNotificacao = codigoNotificacao;
        this.codigoTipoNotificacao = codigoTipoNotificacao;
        this.codigoUsuarioDestino = codigoUsuarioDestino;
        this.mensagem = mensagem;
        this.conteudo = conteudo;
    }

    public WebSocketMensagemDTO() {

    }

    public Long getCodigoTipoNotificacao() {
        return codigoTipoNotificacao;
    }

    public void setCodigoTipoNotificacao(Long codigoTipoNotificacao) {
        this.codigoTipoNotificacao = codigoTipoNotificacao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getCodigoNotificacao() {
        return codigoNotificacao;
    }

    public void setCodigoNotificacao(Long codigoNotificacao) {
        this.codigoNotificacao = codigoNotificacao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Long getCodigoUsuarioDestino() {
        return codigoUsuarioDestino;
    }

    public void setCodigoUsuarioDestino(Long codigoUsuarioDestino) {
        this.codigoUsuarioDestino = codigoUsuarioDestino;
    }

}
