package com.example.needlework.NetWork.Models.userRatingForPatterns;

import com.google.gson.annotations.SerializedName;

public class GetAverageUserRatingForPatternResponseBody {
    @SerializedName("knittingPatternId")
    private long knittingPatternId;
    @SerializedName("averageValue")
    private float averageRating;

    public GetAverageUserRatingForPatternResponseBody(long knittingPatternId, float averageRating) {
        this.knittingPatternId = knittingPatternId;
        this.averageRating = averageRating;
    }

    public long getKnittingPatternId() {
        return knittingPatternId;
    }

    public void setKnittingPatternId(long knittingPatternId) {
        this.knittingPatternId = knittingPatternId;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }
}
