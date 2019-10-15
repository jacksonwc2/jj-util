package com.jj.util.enumerador;

public enum TockenEnum {
    // md5 da constante
    S1WEB("8ef5f00bc78aa9453dbfa8c9064c87eb"),
    S1MOBILE("b5c825a40419ed619a9b3e4586984fb4"),
    S1DESKTOP("26f3c687b2440191b387947d96045262"),
    S1SERVER("a9e0561398586d3032b80441aaf0a6f7"), 
    S1FACADE("2eff1cee111ab9ebd8accba4e63c14f4"), 
    S1VERSAO("9909d48ddcc567398435886ea786c39d"),
    B2CSERVER("881df968dd673e21137865f2a50177ff"),
    B2CMOBILE("f3cebb0cdaedb389fd967c48048de218"),
    B2CFACADE("9eaff739d4524bac139b9ed9e51b0194"); 
    
    private String key;

    TockenEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }   
}