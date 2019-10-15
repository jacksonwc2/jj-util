package com.jj.util.conversor.model;

import java.io.Serializable;

public class Perfil implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String nivel;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNivel() {
        return nivel;
    }
}
