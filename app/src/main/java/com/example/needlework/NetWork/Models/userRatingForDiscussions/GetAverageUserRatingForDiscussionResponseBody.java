package com.example.needlework.NetWork.Models.userRatingForDiscussions;

import com.google.gson.annotations.SerializedName;

public class GetAverageUserRatingForDiscussionResponseBody {
    @SerializedName("knittingPatternId")
    private long discussionId;
    @SerializedName("averageRating")
    private float averageRating;

    public GetAverageUserRatingForDiscussionResponseBody(long discussionId, float averageRating) {
        this.discussionId = discussionId;
        this.averageRating = averageRating;
    }

    public long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(long discussionId) {
        this.discussionId = discussionId;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }
}
