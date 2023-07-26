package com.example.project_closet;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Custom_post {

    public float temp;
    public String imageUrl;
    public String closet;

    public String keyId;
    public String feel;

    public Custom_post() {
        // Default constructor required for calls to DataSnapshot.getValue(Custom_post.class)
    }

    public Custom_post(float temp, String imageUrl, String closet, String keyId, String feel) {
        this.temp = temp;
        this.imageUrl = imageUrl;
        this.closet = closet;
        this.keyId = keyId;
        this.feel = feel;
    }


    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCloset() {
        return closet;
    }

    public void setCloset(String closet) {
        this.closet = closet;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("temp",temp);
        result.put("imageUrl", imageUrl);
        result.put("closet", closet);
        result.put("keyId", keyId);
        result.put("feel", feel);
        return result;
    }




}
