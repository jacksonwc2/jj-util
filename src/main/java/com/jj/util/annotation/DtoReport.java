package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoReport {

    String cabecalho() default "";

    String width() default "100";

    String height() default "15";

    String textAlignment() default "";

    String pattern() default "";

    String caseValue() default "";
}
