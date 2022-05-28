package com.example.needlework.NetWork.Models.userRatingForDiscussions;

import com.google.gson.annotations.SerializedName;

public class CreateUserRatingForDiscussionRequestBody {
    @SerializedName("token")
    private String token;
    @SerializedName("rating")
    private float rating;
    @SerializedName("userId")
    private long userId;
    @SerializedName("discussionId")
    private long discussionId;

    public CreateUserRatingForDiscussionRequestBody(String token, float rating, long userId, long discussionId) {
        this.token = token;
        this.rating = rating;
        this.userId = userId;
        this.discussionId = discussionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(long discussionId) {
        this.discussionId = discussionId;
    }
}
