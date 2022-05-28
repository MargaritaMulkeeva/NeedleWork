package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class GetUserRequestBody {
    @SerializedName("token")
    private String token;

    public GetUserRequestBody(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
