package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class ChangeNickNameRequestBody {
    @SerializedName("userId")
    private String userId;
    @SerializedName("nickName")
    private String nickName;

    public ChangeNickNameRequestBody(String userId, String nickName) {
        this.userId = userId;
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
