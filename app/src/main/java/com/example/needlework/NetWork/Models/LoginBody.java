package com.example.needlework.NetWork.Models;

import com.google.gson.annotations.SerializedName;

public class LoginBody {

    public LoginBody(String login, String password) {
        this.login = login;
        this.password = password;
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

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;


}
