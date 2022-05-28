package com.example.needlework.NetWork.Models.userBookmarks;

import com.google.gson.annotations.SerializedName;

public class UserBookmarksResponseBody {
    @SerializedName("id")
    private long id;
    @SerializedName("userId")
    private long userId;
    @SerializedName("knittingPatternId")
    private long knittingPatternId;

    public UserBookmarksResponseBody(long id, long userId, long knittingPatternId) {
        this.id = id;
        this.userId = userId;
        this.knittingPatternId = knittingPatternId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getKnittingPatternId() {
        return knittingPatternId;
    }

    public void setKnittingPatternId(long knittingPatternId) {
        this.knittingPatternId = knittingPatternId;
    }
}
