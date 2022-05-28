package com.example.needlework.NetWork.Models.messages;

import com.google.gson.annotations.SerializedName;

public class CreateMessageRequestBody {
    @SerializedName("token")
    private String token;
    @SerializedName("text")
    private String text;
    @SerializedName("discussionsId")
    private long discussionsId;
    @SerializedName("userId")
    private long userId;

    public CreateMessageRequestBody(String token, String text, long discussionsId, long userId) {
        this.token = token;
        this.text = text;
        this.discussionsId = discussionsId;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDiscussionsId() {
        return discussionsId;
    }

    public void setDiscussionsId(long discussionsId) {
        this.discussionsId = discussionsId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
