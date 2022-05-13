package com.example.needlework.NetWork.Models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    public LoginResponse(String token) {
        this.token = token;
    }

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
