package com.jj.util.push.apple;

import java.util.HashMap;
import java.util.Map;

import com.jj.util.Json;

public final class PayloadApple {

    private Map<String, String> alert;

    private String sound = "default";

    private String badge = "0";

    public PayloadApple(String title, String message) {
        this.alert = new HashMap<String, String>();
        this.alert.put("title", title);
        this.alert.put("body", message);
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String build() {

        return "{\"aps\":" + Json.getInstance().toJson(this) + "}";

    }

}
