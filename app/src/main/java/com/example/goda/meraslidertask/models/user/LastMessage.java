package com.example.goda.meraslidertask.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ahmed Ali on 8/6/2018.
 */

public class LastMessage implements Serializable {

    @SerializedName("replay_id")
    @Expose
    private int replayId;

    @SerializedName("Timestamp")
    @Expose
    private String timestamp;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("user_id")
    @Expose
    private int userId;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Fullname")
    @Expose
    private String username;

    @SerializedName("Phone")
    @Expose
    private int phone;

    @SerializedName("Picture")
    @Expose
    private String picture;

    public int getReplayId() {
        return replayId;
    }

    public void setReplayId(int replayId) {
        this.replayId = replayId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
