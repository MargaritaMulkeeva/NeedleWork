package com.example.needlework.NetWork.Models.userRatingForPatterns;

import com.google.gson.annotations.SerializedName;

public class CreateUserRatingForPatternRequestBody {
    @SerializedName("token")
    private String token;
    @SerializedName("rating")
    private float rating;
    @SerializedName("userId")
    private long userId;
    @SerializedName("knittingPatternId")
    private long knittingPatternId;

    public CreateUserRatingForPatternRequestBody(String token, float rating, long userId, long knittingPatternId) {
        this.token = token;
        this.rating = rating;
        this.userId = userId;
        this.knittingPatternId = knittingPatternId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
