package com.example.needlework.NetWork.Models.messages;

import com.google.gson.annotations.SerializedName;

public class CreateMessageResponseBody {
    @SerializedName("message")
    private MessageResponseBody message;

    public CreateMessageResponseBody(MessageResponseBody message) {
        this.message = message;
    }

    public MessageResponseBody getMessage() {
        return message;
    }

    public void setMessage(MessageResponseBody message) {
        this.message = message;
    }
}
