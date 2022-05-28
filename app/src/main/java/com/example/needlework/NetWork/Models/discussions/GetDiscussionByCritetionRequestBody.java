package com.example.needlework.NetWork.Models.discussions;

import com.google.gson.annotations.SerializedName;

public class GetDiscussionByCritetionRequestBody {
    @SerializedName("critetion")
    private String critetion;

    public GetDiscussionByCritetionRequestBody(String critetion) {
        this.critetion = critetion;
    }

    public String getCritetion() {
        return critetion;
    }

    public void setCritetion(String critetion) {
        this.critetion = critetion;
    }
}
