package com.jj.util.conversor.dto;

import com.jj.util.annotation.Dto;
import com.jj.util.annotation.DtoField;

import java.io.Serializable;

@Dto
public class TesteDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @DtoField
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
