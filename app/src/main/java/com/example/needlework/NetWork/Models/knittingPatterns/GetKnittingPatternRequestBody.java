package com.example.needlework.NetWork.Models.knittingPatterns;

import com.google.gson.annotations.SerializedName;

public class GetKnittingPatternRequestBody {
    @SerializedName("patternId")
    private String patternId;

    public GetKnittingPatternRequestBody(String patternId) {
        this.patternId = patternId;
    }

    public String getPatternId() {
        return patternId;
    }

    public void setPatternId(String patternId) {
        this.patternId = patternId;
    }
}
