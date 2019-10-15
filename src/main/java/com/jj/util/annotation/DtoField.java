package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jj.util.CharCase;

/**
 * Identifica que o atributo anotado será convertido. <br>
 * <b>Parâmetros:</b> <br>
 * <u>atributo</u> = Identifica coluna correspondente para conversão. <br>
 * Obs:Recebendo um objeto gerado através de uma consulta sql nativa, o valor deste parâmetro deverá ser o indice da coluna. Ex: @DtoField(atributo="1") <br>
 * <u>charCase</u> = (CharCase.NORMAL, CharCase.UPPER_CASE, CharCase.LOWER_CASE)
 *
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoField {
    /**
     * Identifica coluna correspondente para conversão. <br>
     * Obs:Recebendo um objeto gerado através de uma consulta sql nativa, o valor deste parâmetro deverá ser o indice da coluna. Ex: @DtoField(atributo="1")
     */
    String atributo() default "";

    /**
     * Obs:Este parâmetro somente será considerado quando a anotação estiver sobre um atributo do tipo String. CharCase.NORMAL,CharCase.UPPER_CASE,CharCase.LOWER_CASE
     */
    String charCase() default CharCase.DEFAULT;

    /**
     * Formata atributo string <br>
     * Ex 1: @DtoField(formatString="dd/MM/yyyy") <br>
     * Ex 2: @DtoField(formatString="DW") Retorna o dia da semana. <br>
     * Este parâmetro somente será considerado quando a anotação estiver sobre um atributo do tipo String e a coluna origem for do tipo Date. <br>
     */
    String formatString() default "";

    /**
     * Quando o atributo for nulo atribuir o valor.
     * 
     * @return
     */
    String ifnull() default "";

}
