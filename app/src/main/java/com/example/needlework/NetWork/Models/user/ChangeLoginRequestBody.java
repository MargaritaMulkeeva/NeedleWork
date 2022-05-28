package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class ChangeLoginRequestBody {
    @SerializedName("userId")
    private String userId;
    @SerializedName("login")
    private String login;

    public ChangeLoginRequestBody(String userId, String login) {
        this.userId = userId;
        this.login = login;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
