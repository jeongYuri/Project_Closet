package com.example.project_closet;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

// Data_post DTO
public class Data_post {


    public String top;
    public String bottom;
    public String season;

    public String custom;
    public String keyId;

    public String closet;
    public String feel;

    public float temp;
    public float hum;
    public float wind;
    public String rain;

    public Data_post() {

    }

    public Data_post(String keyId, String top, String bottom, String custom, String closet, String season, String feel,
                     float temp, float hum, float wind, String rain) {
        this.top = top;
        this.bottom = bottom;
        this.custom = custom;
        this.keyId = keyId;
        this.closet = closet;
        this.season = season;
        this.feel = feel;
        this.temp = temp;
        this.hum = hum;
        this.wind = wind;
        this.rain = rain;

    }


    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
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

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String getCloset() {
        return closet;
    }

    public void setCloset(String closet) {
        this.closet = closet;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public float getHum() {
        return hum;
    }

    public void setHum(float hum) {
        this.hum = hum;
    }

    public float getWind() {
        return wind;
    }

    public void setWind(float wind) {
        this.wind = wind;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("keyId", keyId);
        result.put("top", top);
        result.put("bottom", bottom);
        result.put("custom", custom);
        result.put("closet", closet);
        result.put("season", season);
        result.put("feel", feel);
        result.put("temp", temp);
        result.put("hum", hum);
        result.put("wind", wind);
        result.put("rain", rain);
        return result;
    }


}
