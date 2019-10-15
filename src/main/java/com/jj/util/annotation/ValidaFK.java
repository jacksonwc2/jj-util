package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotation para validar uma fk esta presente na tabela<br>
 * 
 * ex: @ValidaFK(tabela = "tb_modalidadepromocao", fk = "id_modalidadepromocao")
 * 
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidaFK {
    String fk();

    String tabela();

    String descricaoTabela();
}
