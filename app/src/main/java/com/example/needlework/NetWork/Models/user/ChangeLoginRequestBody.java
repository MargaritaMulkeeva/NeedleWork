package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class ChangeLoginRequestBody {
    @SerializedName("userId")
    private long userId;
    @SerializedName("login")
    private String login;

    public ChangeLoginRequestBody(long userId, String login) {
        this.userId = userId;
        this.login = login;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
