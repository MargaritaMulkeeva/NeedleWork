package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class UserResponseBody {
    @SerializedName("id")
    private long id;
    @SerializedName("nickName")
    private String nickName;
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;
    @SerializedName("avatar")
    private String avatar;

    public UserResponseBody(long id, String nickName, String login, String password, String avatar) {
        this.id = id;
        this.nickName = nickName;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
