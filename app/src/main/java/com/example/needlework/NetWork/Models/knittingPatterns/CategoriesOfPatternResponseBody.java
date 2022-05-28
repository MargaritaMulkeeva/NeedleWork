package com.example.needlework.NetWork.Models.knittingPatterns;

import com.google.gson.annotations.SerializedName;

public class CategoriesOfPatternResponseBody {
    @SerializedName("name")
    private String name;

    public CategoriesOfPatternResponseBody(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
