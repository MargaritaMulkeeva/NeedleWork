package com.example.needlework.NetWork.Models.userBookmarks;

import com.google.gson.annotations.SerializedName;

public class CreateUserBookmarkRequestBody {
    @SerializedName("token")
    private String token;
    @SerializedName("userId")
    private long userId;
    @SerializedName("knittingPatternId")
    private long knittingPatternId;

    public CreateUserBookmarkRequestBody(String token, long userId, long knittingPatternId) {
        this.token = token;
        this.userId = userId;
        this.knittingPatternId = knittingPatternId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
