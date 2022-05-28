package com.example.needlework.NetWork.Models.userBookmarks;

import com.google.gson.annotations.SerializedName;

public class GetUserBookmarkRequestBody {
    @SerializedName("token")
    private String token;

    public GetUserBookmarkRequestBody(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
