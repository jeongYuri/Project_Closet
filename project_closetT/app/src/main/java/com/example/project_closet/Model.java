package com.example.project_closet;

public class Model {
    private String imageUrl;
    private String top;
    private String bottom;
    private String outer;
    private String season;
    private String custom;


    private String keyId;


    Model() {

    }

    public Model(String imageUrl, String top, String bottom, String outer, String season, String custom, String key) {
        this.imageUrl = imageUrl;
        this.top = top;
        this.bottom = bottom;
        this.outer = outer;
        this.season = season;
        this.custom = custom;

        this.keyId = key;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public String getOuter() {
        return outer;
    }

    public void setOuter(String outer) {
        this.outer = outer;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

}
