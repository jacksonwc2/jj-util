package com.jj.util.typesafe;

import com.jj.util.StringUtil;

public class StaticType<T> {

    private String name;
    private String parentName;
    private Class<T> type;

    public StaticType(Class<T> type, String name) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getFullName() {
        return !StringUtil.isNullOrEmpty(this.getParentName()) ? this.getParentName().concat(".").concat(this.getName()) : this.getName();
    }

    public Class<T> getType() {

        return this.type;
    }
}
