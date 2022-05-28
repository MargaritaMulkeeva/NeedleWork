package com.example.needlework.NetWork.Models.messages;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMessageByDiscussionResponseBody {
    @SerializedName("messages")
    List<MessageResponseBody> messages;

    public GetMessageByDiscussionResponseBody(List<MessageResponseBody> messages) {
        this.messages = messages;
    }

    public List<MessageResponseBody> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponseBody> messages) {
        this.messages = messages;
    }
}
