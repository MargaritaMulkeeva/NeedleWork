package com.example.needlework.NetWork.Models.lessons;

import com.google.gson.annotations.SerializedName;

public class LessonResponseBody {
    @SerializedName("id")
    private long id;
    @SerializedName("viewed")
    private boolean viewed;
    @SerializedName("linkToVideo")
    private String linkToVideo;
    @SerializedName("userId")
    private long userId;

    public LessonResponseBody(long id, boolean viewed, String linkToVideo, long userId) {
        this.id = id;
        this.viewed = viewed;
        this.linkToVideo = linkToVideo;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public String getLinkToVideo() {
        return linkToVideo;
    }

    public void setLinkToVideo(String linkToVideo) {
        this.linkToVideo = linkToVideo;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
