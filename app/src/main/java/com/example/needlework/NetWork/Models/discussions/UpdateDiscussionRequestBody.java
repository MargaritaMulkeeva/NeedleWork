package com.example.needlework.NetWork.Models.discussions;

import com.google.gson.annotations.SerializedName;

public class UpdateDiscussionRequestBody {
    @SerializedName("theme")
    private String theme;
    @SerializedName("textOfDiscussions")
    private String textOfDiscussions;
    @SerializedName("categoryOfDiscussionsId")
    private long categoryOfDiscussionsId;
    @SerializedName("userId")
    private long userId;

    public UpdateDiscussionRequestBody(String theme, String textOfDiscussions, long categoryOfDiscussionsId, long userId) {
        this.theme = theme;
        this.textOfDiscussions = textOfDiscussions;
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
