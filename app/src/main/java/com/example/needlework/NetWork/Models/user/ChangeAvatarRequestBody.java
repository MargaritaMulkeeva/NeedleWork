package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class ChangeAvatarRequestBody {
    @SerializedName("userId")
    private long userId;
    @SerializedName("avatar")
    private String avatar;

    public ChangeAvatarRequestBody(long userId, String avatar) {
        this.userId = userId;
        this.avatar = avatar;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
