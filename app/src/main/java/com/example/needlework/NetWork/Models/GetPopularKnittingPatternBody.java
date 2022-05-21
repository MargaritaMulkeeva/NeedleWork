package com.example.needlework.NetWork.Models;

import com.google.gson.annotations.SerializedName;

public class GetPopularKnittingPatternBody {
    @SerializedName("criterion")
    private String criterion;

    public GetPopularKnittingPatternBody(String criterion) {
        this.criterion = criterion;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }
}
