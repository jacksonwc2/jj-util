package com.jj.util.typesafe;

import java.util.Date;

import com.jj.util.conversor.dto.TesteUsuarioDTO;

public class STesteUsuarioDTO extends StaticBase<TesteUsuarioDTO> {

    private static final long serialVersionUID = 1L;

    public static final STesteUsuarioDTO testeUsuarioDTO = new STesteUsuarioDTO();

    public final StaticType<Long> id = new StaticType<Long>(Long.class, "rua");

    public final StaticType<String> nome = new StaticType<String>(String.class, "rua");

    public final StaticType<Date> dataNascimento = new StaticType<Date>(Date.class, "rua");

    public final STestePerfilDTO perfil = new STestePerfilDTO("perfil");

    public final STesteEnderecoDTO enderecos = new STesteEnderecoDTO("enderecos");

}
