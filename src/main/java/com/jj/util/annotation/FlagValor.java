package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta annotation serve para adicionar uma lista de valores que ele apenas aceita<br>
 * ex: @FlagValor(valor={"A","I","U"})
 * 
 * 
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FlagValor {
    String[] valores();
}
