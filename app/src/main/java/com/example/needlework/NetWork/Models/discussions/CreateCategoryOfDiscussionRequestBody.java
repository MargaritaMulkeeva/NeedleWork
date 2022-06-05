package com.example.needlework.NetWork.Models.discussions;

import com.google.gson.annotations.SerializedName;

public class CreateCategoryOfDiscussionRequestBody {
    @SerializedName("name")
    private String name;

    public CreateCategoryOfDiscussionRequestBody(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
