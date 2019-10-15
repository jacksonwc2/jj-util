package com.jj.util.conversor.model;

import java.io.Serializable;

public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String rua;
    private String cidade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
