package com.jj.util.typesafe;

import com.jj.util.conversor.dto.TesteEnderecoDTO;

public class STesteEnderecoDTO extends StaticBase<TesteEnderecoDTO> {

    private static final long serialVersionUID = 1L;

    public static final STesteEnderecoDTO testeEnderecoDTO = new STesteEnderecoDTO();

    public final StaticType<String> rua = new StaticType<String>(String.class, "rua");

    public final StaticType<String> cidade = new StaticType<String>(String.class, "cidade");

    public STesteEnderecoDTO() {

    }

    public STesteEnderecoDTO(String name) {
        this.rua.setParentName(name);
        this.cidade.setParentName(name);
    }

}
