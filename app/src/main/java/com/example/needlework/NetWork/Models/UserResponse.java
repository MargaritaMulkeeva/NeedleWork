package com.example.needlework.NetWork.Models;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("nickName")
    private String nickName;
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;
    @SerializedName("avatar")
    private String avatar;

    public UserResponse(String id, String name, String nickName, String login, String password, String avatar) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
