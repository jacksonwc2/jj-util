package com.jj.util.dto;

import com.jj.util.annotation.Dto;
import com.jj.util.annotation.DtoField;

@Dto
public class SortDTO {

    @DtoField
    private String property;

    @DtoField
    private String direction;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
