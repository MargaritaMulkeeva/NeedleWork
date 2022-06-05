package com.example.needlework.NetWork.Models.userBookmarks;

import com.google.gson.annotations.SerializedName;

public class DeleteUserBookmarkRequestBody {
    @SerializedName("userId")
    private long userId;
    @SerializedName("bookmarkId")
    private long bookmarkId;

    public DeleteUserBookmarkRequestBody(long userId, long bookmarkId) {
        this.userId = userId;
        this.bookmarkId = bookmarkId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }
}
