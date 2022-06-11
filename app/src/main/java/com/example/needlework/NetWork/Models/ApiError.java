package com.example.needlework.NetWork.Models;

import com.google.gson.annotations.SerializedName;

public class ApiError {
    @SerializedName("message")
    private String message;

    public ApiError(String message) {
        this.message = message;
    }

    public String getError() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
