package com.jj.util.conversor.dto;

import java.io.Serializable;

import com.jj.util.annotation.Dto;
import com.jj.util.annotation.DtoField;

@Dto
public class TesteEnderecoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @DtoField
    private String rua;

    @DtoField
    private String cidade;

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

}
