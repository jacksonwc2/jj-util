package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta annotation serve para adicionar um nome mais legivel ao atributo quando for necessario<br>
 * ex: codigoEmpresa seja > Código da empresa
 * 
 * 
 */
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumClasse {
    int codigo();
}
