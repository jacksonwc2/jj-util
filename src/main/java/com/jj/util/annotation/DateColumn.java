package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * A validação por intervalo de data deve ser informada no formato 'yyyy-MM-dd'.
 * </BR>Para data atual utilize a string 'NOW'.
 *
 */
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateColumn {
    String min() default "";
    String max() default "";

}
