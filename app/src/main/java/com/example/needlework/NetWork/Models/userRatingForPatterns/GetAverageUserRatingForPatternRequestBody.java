package com.example.needlework.NetWork.Models.userRatingForPatterns;

import com.google.gson.annotations.SerializedName;

public class GetAverageUserRatingForPatternRequestBody {
    @SerializedName("knittingPatternId")
    private long knittingPatternId;

    public GetAverageUserRatingForPatternRequestBody(long knittingPatternId) {
        this.knittingPatternId = knittingPatternId;
    }

    public long getKnittingPatternId() {
        return knittingPatternId;
    }

    public void setKnittingPatternId(long knittingPatternId) {
        this.knittingPatternId = knittingPatternId;
    }
}
