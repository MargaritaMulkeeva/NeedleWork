package com.example.needlework.NetWork.Models.userRatingForDiscussions;

import com.google.gson.annotations.SerializedName;

public class UserRatingsForDiscussionsResponseBody {
    @SerializedName("id")
    private long id;
    @SerializedName("rating")
    private float rating;
    @SerializedName("userId")
    private long userId;
    @SerializedName("discussionId")
    private long discussionId;

    public UserRatingsForDiscussionsResponseBody(long id, float rating, long userId, long discussionId) {
        this.id = id;
        this.rating = rating;
        this.userId = userId;
        this.discussionId = discussionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
