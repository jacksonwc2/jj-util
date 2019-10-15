package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifica que o atributo anotado ira receber o retorno de uma expressão booleana <br>
 * <b>Parâmetros:</b> <br>
 * <u>atributoValidar</u> = Identifica coluna correspondente para validação. <br>
 * <u>condicao</u> = Condição lógica, podendo ser '=' ou '!=' <br>
 * <u>valorAtributo</u> = valor utilizado na validação.<br>
 * Obs:O atributo anotado com DtoFieldLogic deve ser obrigatóriamente do tipo Boolean.
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoFieldLogic {

    /**
     * Identifica coluna correspondente para validação.
     */
    String atributoValidar();

    /**
     * Condição lógica, podendo ser '=' ou '!='.
     */
    String condicao();

    /**
     * valor utilizado na validação
     */
    String valorAtributo();

}
