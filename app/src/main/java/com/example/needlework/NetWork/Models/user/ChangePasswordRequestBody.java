package com.example.needlework.NetWork.Models.user;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequestBody {
    @SerializedName("userId")
    private long userId;
    @SerializedName("oldPassword")
    private String oldPassword;
    @SerializedName("newPassword")
    private String newPassword;
    @SerializedName("confirm")
    private String confirm;

    public ChangePasswordRequestBody(long userId, String oldPassword, String newPassword, String confirm) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirm = confirm;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
