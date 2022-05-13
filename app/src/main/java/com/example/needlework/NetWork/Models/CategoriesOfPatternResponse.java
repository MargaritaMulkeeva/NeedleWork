package com.example.needlework.NetWork.Models;

import com.google.gson.annotations.SerializedName;

public class CategoriesOfPatternResponse {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoriesOfPatternResponse(String name) {
        this.name = name;
    }
}
