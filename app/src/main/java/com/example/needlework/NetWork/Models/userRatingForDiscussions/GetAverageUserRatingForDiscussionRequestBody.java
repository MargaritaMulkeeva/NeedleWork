package com.example.needlework.NetWork.Models.userRatingForDiscussions;

import com.google.gson.annotations.SerializedName;

public class GetAverageUserRatingForDiscussionRequestBody {
    @SerializedName("discussionId")
    private long discussionId;

    public GetAverageUserRatingForDiscussionRequestBody(long discussionId) {
        this.discussionId = discussionId;
    }

    public long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(long discussionId) {
        this.discussionId = discussionId;
    }
}
