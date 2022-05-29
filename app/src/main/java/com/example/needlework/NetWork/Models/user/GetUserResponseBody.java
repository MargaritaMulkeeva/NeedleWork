package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class GetUserResponseBody {
    @SerializedName("user")
    private UserResponseBody user;

    public GetUserResponseBody(UserResponseBody user) {
        this.user = user;
    }

    public UserResponseBody getUser() {
        return user;
    }

    public void setUser(UserResponseBody user) {
        this.user = user;
    }
}
