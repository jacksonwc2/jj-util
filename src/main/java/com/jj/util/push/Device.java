package com.jj.util.push;

import com.jj.util.enumerador.Platform;

public class Device {

    private String token;

    private Platform platform;

    public Device() {

    }

    public Device(Platform platform, String token) {
        this.platform = platform;
        this.token = token;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
