package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class ChangeAvatarRequestBody {
    @SerializedName("userId")
    private String userId;
    @SerializedName("avatar")
    private String avatar;

    public ChangeAvatarRequestBody(String userId, String avatar) {
        this.userId = userId;
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
