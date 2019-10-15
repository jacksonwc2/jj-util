package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifica o atributo que será utilizado na conversão para script.
 *
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoColumn {

    /**
     * Identifica o atributo no banco de dados para conversão de dto para script de insert ou update.
     * 
     * @return
     */
    String name();

}
