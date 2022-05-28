package com.example.needlework.NetWork.Models.discussions;

import com.google.gson.annotations.SerializedName;

public class GetDiscussionByIdRequestBody {
    @SerializedName("discussionId")
    private String discussionId;

    public GetDiscussionByIdRequestBody(String discussionId) {
        this.discussionId = discussionId;
    }

    public String getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }
}
