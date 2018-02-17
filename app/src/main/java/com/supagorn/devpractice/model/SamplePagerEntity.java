package com.supagorn.devpractice.model;

import com.google.gson.annotations.SerializedName;

public class SamplePagerEntity {

    @SerializedName("imageUrl")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
