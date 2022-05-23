package com.example.needlework.NetWork.Models;

import com.google.gson.annotations.SerializedName;

public class DiscussionsResponse {

    @SerializedName("theme")
    private String theme;

    @SerializedName("textOfDiscussions")
    private String textOfDiscussions;

    @SerializedName("rating")
    private double rating;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("categoryofDiscussionsId")
    private long categoryofDiscussionsId;

    @SerializedName("userId")
    private long userId;

    public DiscussionsResponse(String theme, String textOfDiscussions, double rating, String createdAt, long categoryofDiscussionsId, long userId) {
        this.theme = theme;
        this.textOfDiscussions = textOfDiscussions;
        this.rating = rating;
        this.createdAt = createdAt;
        this.categoryofDiscussionsId = categoryofDiscussionsId;
        this.userId = userId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTextOfDiscussions() {
        return textOfDiscussions;
    }

    public void setTextOfDiscussions(String textOfDiscussions) {
        this.textOfDiscussions = textOfDiscussions;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getCategoryofDiscussionsId() {
        return categoryofDiscussionsId;
    }

    public void setCategoryofDiscussionsId(long categoryofDiscussionsId) {
        this.categoryofDiscussionsId = categoryofDiscussionsId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
