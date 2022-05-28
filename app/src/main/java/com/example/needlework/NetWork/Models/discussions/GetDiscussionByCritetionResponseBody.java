package com.example.needlework.NetWork.Models.discussions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDiscussionByCritetionResponseBody {
    @SerializedName("discussions")
    private List<DiscussionsResponseBody> discussions;

    public GetDiscussionByCritetionResponseBody(List<DiscussionsResponseBody> discussions) {
        this.discussions = discussions;
    }

    public List<DiscussionsResponseBody> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<DiscussionsResponseBody> discussions) {
        this.discussions = discussions;
    }
}
