package com.example.needlework.NetWork.Models.discussions;

import com.google.gson.annotations.SerializedName;

public class CreateDiscussionRequestBody {
    @SerializedName("theme")
    private String theme;
    @SerializedName("textOfDiscussions")
    private String textOfDiscussions;
    @SerializedName("rating")
    private float rating;
    @SerializedName("categoryOfDiscussionsId")
    private long categoryOfDiscussionsId;
    @SerializedName("userId")
    private long userId;

    public CreateDiscussionRequestBody(String theme, String textOfDiscussions, float rating, long categoryOfDiscussionsId, long userId) {
        this.theme = theme;
        this.textOfDiscussions = textOfDiscussions;
        this.rating = rating;
        this.categoryOfDiscussionsId = categoryOfDiscussionsId;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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
}
