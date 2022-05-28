package com.example.needlework.NetWork.Models.messages;

import com.google.gson.annotations.SerializedName;

public class GetMessageByDiscussionRequestBody {
    @SerializedName("discussionsId")
    private long discussionsId;

    public GetMessageByDiscussionRequestBody(long discussionsId) {
        this.discussionsId = discussionsId;
    }

    public long getDiscussionsId() {
        return discussionsId;
    }

    public void setDiscussionsId(long discussionsId) {
        this.discussionsId = discussionsId;
    }
}
