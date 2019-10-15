package com.jj.util.conversor.dto;

import java.io.Serializable;

import com.jj.util.annotation.Dto;

@Dto
public class TestePerfilDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nivel;

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
