package com.example.needlework.NetWork.Models.knittingPatterns;

import com.google.gson.annotations.SerializedName;

public class UpdateRatingForPatternRequestBody {
    @SerializedName("patternId")
    private long patternId;
    @SerializedName("rating")
    private float rating;

    public UpdateRatingForPatternRequestBody(long patternId, float rating) {
        this.patternId = patternId;
        this.rating = rating;
    }

    public long getPatternId() {
        return patternId;
    }

    public void setPatternId(long patternId) {
        this.patternId = patternId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
