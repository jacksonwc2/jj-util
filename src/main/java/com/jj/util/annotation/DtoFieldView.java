package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifica que o atributo anotado será convertido. <br>
 * <b>Parâmetros:</b> <br>
 * <u>atributo</u> = Identifica coluna correspondente para conversão.<br>
 * <u>field</u> = Identifica coluna do atributo que pertence ao objeto origem.
 *
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoFieldView {
    /**
     * Identifica coluna correspondente para conversão. <br>
     */
    String atributo();

    /**
     * Identifica qual campo do objeto informado no parâmetro "atributo" será carregado. <br>
     * Ex:<br>
     * @DtoFieldView(atributo="programa",field="descricao")  <br>
     * private String nomePrograma;
     * 
     * Neste exemplo o atributo 'nomePrograma' irá receber o valor do campo 'descricao' que pertente ao atributo 'programa' do objeto origem.
     */
    String field();

}
