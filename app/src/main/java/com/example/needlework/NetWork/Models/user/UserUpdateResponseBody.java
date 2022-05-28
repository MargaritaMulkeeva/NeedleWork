package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class UserUpdateResponseBody {
    @SerializedName("message")
    private String message;

    public UserUpdateResponseBody(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
