package com.jj.util.dto;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class MailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // host de conexao com o smtp
    private String host;
    // email do remetente para fazer a autentificacao
    private String emailRemetente;
    // nome de quem está enviando o email
    private String nomeRemetente;
    // senha do usuario para fazer a conexao com o servidor
    private String senhaEmail;
    // porta de acesso ao banco
    private Long porta;
    // flag ssl
    private Long flagSsl;
    /*
     * toda a mensagem de texto para ser enviado <br> O texto pode ser em formatação de HTML
     */
    private String mensagem;
    // Assunto da mensagem para o envio
    private String assunto;

    // Um arquivo ou varios para o envio de anexo como pdf, imagens.
    private List<File> anexo;
    // Lista de destinatarios ou somente um destinatario
    private List<String> destinatarios;
    
    private Boolean startTLS = Boolean.FALSE;

    public Boolean getStartTLS() {
        return startTLS;
    }

    public void setStartTLS(Boolean startTLS) {
        this.startTLS = startTLS;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEmailRemetente() {
        return emailRemetente;
    }

    public void setEmailRemetente(String emailRemetente) {
        this.emailRemetente = emailRemetente;
    }

    public String getNomeRemetente() {
        return nomeRemetente;
    }

    public void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }

    public String getSenhaEmail() {
        return senhaEmail;
    }

    public void setSenhaEmail(String senhaEmail) {
        this.senhaEmail = senhaEmail;
    }

    public Long getPorta() {
        return porta;
    }

    public void setPorta(Long porta) {
        this.porta = porta;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public List<File> getAnexo() {
        return anexo;
    }

    public void setAnexo(List<File> anexo) {
        this.anexo = anexo;
    }

    public void setAnexo(File anexo) {
        this.anexo.add(anexo);
    }

    public List<String> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<String> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public void setDestinatarios(String destinatario) {
        this.destinatarios.add(destinatario);
    }

    public Long getFlagSsl() {
        return flagSsl;
    }

    public void setFlagSsl(Long flagSsl) {
        this.flagSsl = flagSsl;
    }

}
