package com.example.needlework.NetWork.Models.knittingPatterns;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllKnittingPatterns {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("rating")
    private double rating;

    @SerializedName("imagePattern")
    private String imagePattern;


    @SerializedName("imageOfProduct")
    private String imageOfProduct;

    @SerializedName("workProcessDescription")
    private List<KnittingPatternContent> workProcessDescription;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("categoryOfPatternId")
    private long categoryOfPatternId;

    public GetAllKnittingPatterns(long id, String name, String description, double rating, String imagePattern, String imageOfProduct, List<KnittingPatternContent> workProcessDescription, String createdAt, long categoryOfPatternId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.imagePattern = imagePattern;
        this.imageOfProduct = imageOfProduct;
        this.workProcessDescription = workProcessDescription;
        this.createdAt = createdAt;
        this.categoryOfPatternId = categoryOfPatternId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImagePattern() {
        return imagePattern;
    }

    public void setImagePattern(String imagePattern) {
        this.imagePattern = imagePattern;
    }

    public String getImageOfProduct() {
        return imageOfProduct;
    }

    public void setImageOfProduct(String imageOfProduct) {
        this.imageOfProduct = imageOfProduct;
    }

    public List<KnittingPatternContent> getWorkProcessDescription() {
        return workProcessDescription;
    }

    public void setWorkProcessDescription(List<KnittingPatternContent> workProcessDescription) {
        this.workProcessDescription = workProcessDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getCategoryOfPatternId() {
        return categoryOfPatternId;
    }

    public void setCategoryOfPatternId(long categoryOfPatternId) {
        this.categoryOfPatternId = categoryOfPatternId;
    }
}
