package com.jj.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotation para validar varias fk esta presente na tabela<br>
 * Só necessita colocar na primeira coluna, e passar a lista das demais colunas para validar em sequencia... A coluna que for colocada essa anotacao, deve corresponder o primeiro item da lista fk.
 * 
 * ex: @ValidaMultiFK(tabela = "tb_estruturadois", fk = { "id_estruturadois", "cd_estruturaum" }) foi colocado na coluna @Column(name = "cd_estruturadois")
 * 
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidaMultiFK {
    String[] fk();

    String tabela();

    String descricaoTabela();
}
