package com.example.needlework.NetWork.Models;

import com.google.gson.annotations.SerializedName;

public class GetKnittingPatternBody {
    @SerializedName("patternId")
    private String patternId;

    public GetKnittingPatternBody(String patternId) {
        this.patternId = patternId;
    }

    public String getPatternId() {
        return patternId;
    }

    public void setPatternId(String patternId) {
        this.patternId = patternId;
    }
}
