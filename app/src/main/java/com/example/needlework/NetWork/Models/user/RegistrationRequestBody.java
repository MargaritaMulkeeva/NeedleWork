package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class RegistrationRequestBody {

    @SerializedName("nickName")
    private String nickName;

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    @SerializedName("confirm")
    private String confirm;


    public RegistrationRequestBody(String nickName, String login, String password, String confirm) {
        this.nickName = nickName;
        this.login = login;
        this.password = password;
        this.confirm = confirm;
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

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
