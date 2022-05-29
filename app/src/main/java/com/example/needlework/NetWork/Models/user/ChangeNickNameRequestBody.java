package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class ChangeNickNameRequestBody {
    @SerializedName("userId")
    private long userId;
    @SerializedName("nickName")
    private String nickName;

    public ChangeNickNameRequestBody(long userId, String nickName) {
        this.userId = userId;
        this.nickName = nickName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
