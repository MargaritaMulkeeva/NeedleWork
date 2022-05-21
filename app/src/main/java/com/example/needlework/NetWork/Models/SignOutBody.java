package com.example.needlework.NetWork.Models;

import com.google.gson.annotations.SerializedName;

public class SignOutBody {
    @SerializedName("token")
    private String token;

    public SignOutBody(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
