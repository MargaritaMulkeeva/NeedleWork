package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponseBody {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private UserResponseBody user;

    public RegistrationResponseBody(String token, UserResponseBody user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserResponseBody getUser() {
        return user;
    }

    public void setUser(UserResponseBody user) {
        this.user = user;
    }
}
