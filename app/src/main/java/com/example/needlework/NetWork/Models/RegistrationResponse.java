package com.example.needlework.NetWork.Models;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private UserResponse user;

    public RegistrationResponse(String token, UserResponse user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
