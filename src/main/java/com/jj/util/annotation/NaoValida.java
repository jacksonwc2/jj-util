package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifica que o atributo que será usado na cláusula where para criar o script de insert ou update.
 * 
 * passar o generator da tabela desejada <br>
 * 
 * ex: gen_modalidadepromocao ou "false" quando recebe o id pronto
 *
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NaoValida {

}
