package com.jj.util.conversor.dto;

import java.io.Serializable;

import com.jj.util.annotation.Dto;
import com.jj.util.annotation.DtoField;

@Dto
public class TesteCompareToDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @DtoField(atributo="0")
    private Long codigo;
    /*
     * Representa o eixo X para os gráficos
     */
    @DtoField(atributo="1",formatString="dd/MM/yyyy")
    private String identificador;
    /*
     * Representa a lengenda dos gráficos
     */
    @DtoField(atributo="2")
    private String legenda;
    /*
     * Representa o valor atribuido ao identificador
     */
    @DtoField(atributo="3")
    private Double valor;
    
    @DtoField(atributo="4")
    private Long fluxoDeClientes;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getFluxoDeClientes() {
        return fluxoDeClientes;
    }

    public void setFluxoDeClientes(Long fluxoDeClientes) {
        this.fluxoDeClientes = fluxoDeClientes;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}
