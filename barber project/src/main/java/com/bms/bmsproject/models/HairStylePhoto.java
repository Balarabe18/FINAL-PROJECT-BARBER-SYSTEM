package com.bms.bmsproject.models;


public class HairStylePhoto {
    private String url;
    private String description;

    public HairStylePhoto(String url, String description) {
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}
