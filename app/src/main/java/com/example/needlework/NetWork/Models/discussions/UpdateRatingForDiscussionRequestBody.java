package com.example.needlework.NetWork.Models.discussions;

import com.google.gson.annotations.SerializedName;

public class UpdateRatingForDiscussionRequestBody {
    @SerializedName("discussionId")
    private long discussionId;
    @SerializedName("rating")
    private float rating;

    public UpdateRatingForDiscussionRequestBody(long discussionId, float rating) {
        this.discussionId = discussionId;
        this.rating = rating;
    }

    public long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(long discussionId) {
        this.discussionId = discussionId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
