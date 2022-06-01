package com.example.needlework.NetWork.Models.knittingPatterns;

import com.google.gson.annotations.SerializedName;

public class CategoriesOfPatternResponseBody {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;

    public CategoriesOfPatternResponseBody(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
