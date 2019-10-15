package com.jj.util.typesafe;

import com.jj.util.conversor.dto.TestePerfilDTO;

public class STestePerfilDTO extends StaticBase<TestePerfilDTO> {
    private static final long serialVersionUID = 1L;

    public static final STestePerfilDTO testePerfilDTO = new STestePerfilDTO();

    public final StaticType<String> nivel = new StaticType<String>(String.class, "nivel");

    public STestePerfilDTO() {

    }

    public STestePerfilDTO(String name) {
        this.nivel.setParentName(name);
    }
}
