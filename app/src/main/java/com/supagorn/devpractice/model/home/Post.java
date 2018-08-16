package com.supagorn.devpractice.model.home;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {
    public String name;
    public String imageUrl;

    public String uid;
    public String author;
    public String body;
    public long timestamp;
    public int likeCount = 0;
    public int commentCount = 0;
    public Map<String, Boolean> likes = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String body) {
        this.uid = uid;
        this.author = author;
        this.body = body;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("body", body);
        result.put("timestamp", ServerValue.TIMESTAMP);
        result.put("likeCount", likeCount);
        result.put("commentCount", commentCount);
        result.put("likes", likes);
        return result;
    }
}