package com.example.needlework.NetWork.Models.discussions;

import com.google.gson.annotations.SerializedName;

public class UpdateDiscussionResponseBody {
    @SerializedName("message")
    private String message;

    public UpdateDiscussionResponseBody(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
