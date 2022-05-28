package com.example.needlework.NetWork.Models.userRatingForPatterns;

import com.google.gson.annotations.SerializedName;

public class UserRatingForPatternsResponseBody {
    @SerializedName("id")
    private long id;
    @SerializedName("rating")
    private float rating;
    @SerializedName("userId")
    private long userId;
    @SerializedName("knittingPatternId")
    private long knittingPatternId;

    public UserRatingForPatternsResponseBody(long id, float rating, long userId, long knittingPatternId) {
        this.id = id;
        this.rating = rating;
        this.userId = userId;
        this.knittingPatternId = knittingPatternId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getKnittingPatternId() {
        return knittingPatternId;
    }

    public void setKnittingPatternId(long knittingPatternId) {
        this.knittingPatternId = knittingPatternId;
    }
}
