package com.jj.util.conversor.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.jj.util.DateUtil;
import com.jj.util.annotation.Dto;
import com.jj.util.annotation.DtoColumn;
import com.jj.util.annotation.DtoField;
import com.jj.util.annotation.DtoPk;

@Dto(tableName = "tb_usuario")
public class TesteUsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Representa o ID do objeto.
     */
    @DtoField
    @DtoPk(generator = "gen_usuario")
    @DtoColumn(name = "id")
    private Long id;

    @DtoField
    @DtoColumn(name = "nome")
    private String nome;

    @DtoField(formatString = DateUtil.FORMATO_YYYY_MM_DD)
    @DtoColumn(name = "dt_nascimento")
    private Date dataNascimento;

    @DtoField
    private TestePerfilDTO perfil;

    @DtoField
    private List<TesteEnderecoDTO> enderecos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public TestePerfilDTO getPerfil() {
        return perfil;
    }

    public void setPerfil(TestePerfilDTO perfil) {
        this.perfil = perfil;
    }

    public List<TesteEnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<TesteEnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }
}
