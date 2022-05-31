package com.example.needlework.NetWork.Models.discussions;

import com.google.gson.annotations.SerializedName;

public class GetAllDiscussions {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getCategoryOfDiscussionsId() {
        return categoryOfDiscussionsId;
    }

    public void setCategoryOfDiscussionsId(long categoryOfDiscussionsId) {
        this.categoryOfDiscussionsId = categoryOfDiscussionsId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public GetAllDiscussions(long id, String theme, String textOfDiscussions, double rating, String createdAt, long categoryOfDiscussionsId, long userId) {
        this.id = id;
        this.theme = theme;
        this.textOfDiscussions = textOfDiscussions;
        this.rating = rating;
        this.createdAt = createdAt;
        this.categoryOfDiscussionsId = categoryOfDiscussionsId;
        this.userId = userId;
    }

    @SerializedName("id")
    private long id;

    @SerializedName("theme")
    private String theme;

    @SerializedName("textOfDiscussions")
    private String textOfDiscussions;

    @SerializedName("rating")
    private double rating;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("categoryOfDiscussionsId")
    private long categoryOfDiscussionsId;

    @SerializedName("userId")
    private long userId;
}
