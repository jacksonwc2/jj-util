package com.jj.util.exception;

import javax.ejb.ApplicationException;

/**
 * Exceção que os BusinessComponent lançam quando ocorre alguma falha de negócio. Essa classe não poderá ser extensível e somente pode ser instanciada pelo Resource de mensagens.
 */
@ApplicationException(rollback = true)
public final class BusinessServerException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Nome da propriedade do resource da mensagem.
     */
    private final String codigo;

    /**
     * Nome da propriedade do resource da mensagem.
     */
    private final String mensagem;

    /**
     * Constroi o objeto da exceção baseado no Resource de Mensagem. <br>
     * Somente a classe Resource pode criar essa exceção.
     * <p/>
     * <b>Nem pense em modificar essa função para public!</b>
     *
     * @param codigoErro Código de erro configurado no resource do BusinessComponent.
     * @param mensagem Texto da mensagem.
     */
    public BusinessServerException(String codigo, String mensagemTecnica, String mensagem) {

        super(mensagemTecnica);

        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    /**
     * Constroi o objeto da exceção baseado no Resource de Mensagem. <br>
     * Somente a classe Resource pode criar essa exceção.
     * <p/>
     * <b>Nem pense em modificar essa função para public!</b>
     *
     * @param codigoErro Código de erro configurado no resource do BusinessComponent.
     * @param mensagem Texto da mensagem.
     * @param throwable exceção que originou a exceção de negócio.
     */
    public BusinessServerException(String codigo, String mensagemTecnica, String mensagem, Throwable throwable) {

        super(mensagemTecnica, throwable);

        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    /**
     * Apenas retorna a excecao
     * 
     * @param throwable
     */
    public BusinessServerException(Throwable throwable) {

        super(throwable);

        this.codigo = "";
        this.mensagem = throwable.getMessage();
    }

    /**
     * Retorna o código de erro.
     *
     * @return
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna o texto da mensagem Técnica.
     *
     * @return
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Retorna o texto da mensagem ao usuário.
     *
     * @return
     */
    public String getMensagemTecnica() {

        return super.getMessage();
    }
}
