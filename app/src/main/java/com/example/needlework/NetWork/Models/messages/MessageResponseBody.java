package com.example.needlework.NetWork.Models.messages;

import com.google.gson.annotations.SerializedName;

public class MessageResponseBody {
    @SerializedName("id")
    private long id;
    @SerializedName("text")
    private String text;
    @SerializedName("discussionsId")
    private long discussionsId;
    @SerializedName("userId")
    private long userId;
    @SerializedName("createdAt")
    private String createdAt;

    public MessageResponseBody(long id, String text, long discussionsId, long userId, String createdAt) {
        this.id = id;
        this.text = text;
        this.discussionsId = discussionsId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
