package com.supagorn.devpractice.model.home;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;
import com.google.gson.annotations.SerializedName;
import com.supagorn.devpractice.enums.FeedViewType;
import com.supagorn.devpractice.enums.PostViewType;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {
    public int id;
    public String key;
    public String imageUrl;
    public String imageProfileUrl;

    public String fullName;
    public String uid;
    public String author;
    public String body;
    public long timestamp;
    public int likeCount = 0;
    public int commentCount = 0;
    public Map<String, Boolean> likes = new HashMap<>();

    @SerializedName("viewType")
    public PostViewType viewType;

    public FeedViewType feedViewType;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String key, String uid, String fullName, String author, String body) {
        this.key = key;
        this.uid = uid;
        this.fullName = fullName;
        this.author = author;
        this.body = body;
    }

    public Post(String fullName, String imageUrl, String body, FeedViewType viewType) {
        this.imageUrl = imageUrl;
        this.fullName = fullName;
        this.body = body;
        this.feedViewType = viewType;
    }
    public Post(String imageUrl, FeedViewType viewType) {
        this.imageUrl = imageUrl;
        this.feedViewType = viewType;
    }
    public Post(String body, int likeCount, FeedViewType viewType) {
        this.body = body;
        this.likeCount = likeCount;
        this.feedViewType = viewType;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("key", key);
        result.put("fullName", fullName);
        result.put("uid", uid);
        result.put("author", author);
        result.put("body", body);
        result.put("timestamp", ServerValue.TIMESTAMP);
        result.put("likeCount", likeCount);
        result.put("commentCount", commentCount);
        result.put("likes", likes);
        result.put("viewType", viewType);
        return result;
    }
}