package com.jj.util.exception;

/**
 * Essa excessão é lançada quando um registro solicitado não é encontado no repositório de dados.
 *
 */
public class RegistroNaoEncontradoDAOException extends Exception {

    private static final long serialVersionUID = 1L;

    public RegistroNaoEncontradoDAOException() {
    }

    public RegistroNaoEncontradoDAOException(Throwable e) {
        super(e);
    }
}
