package com.example.needlework.NetWork.Models.knittingPatterns;

import com.google.gson.annotations.SerializedName;

public class KnittingPatternContent {
    @SerializedName("type")
    private String type;

    @SerializedName("content")
    private String content;

    public KnittingPatternContent(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
