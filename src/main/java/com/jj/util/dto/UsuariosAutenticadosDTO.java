package com.jj.util.dto;

import java.io.Serializable;
import java.util.List;

public class UsuariosAutenticadosDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Long> codigoUsuarios;

    private String dominioSistema;

    public List<Long> getCodigoUsuarios() {
        return codigoUsuarios;
    }

    public void setCodigoUsuarios(List<Long> codigoUsuarios) {
        this.codigoUsuarios = codigoUsuarios;
    }

    public String getDominioSistema() {
        return dominioSistema;
    }

    public void setDominioSistema(String dominioSistema) {
        this.dominioSistema = dominioSistema;
    }

}
