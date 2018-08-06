package com.example.goda.meraslidertask.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ahmed Ali on 8/6/2018.
 */

public class Conversation implements Serializable {

    public static final int CARD_TYPE = 1;
    public static final int PROGRESS_TYPE = 2;

    @SerializedName("not_IN")
    @Expose
    int itemType;

    @SerializedName("c_id")
    @Expose
    private int conversationId;

    @SerializedName("User_ID")
    @Expose
    private String userId;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Fullname")
    @Expose
    private String username;

    @SerializedName("Picture")
    @Expose
    private String picture;

    @SerializedName("Phone")
    @Expose
    private String phone;

    @SerializedName("Address")
    @Expose
    private String address;

    @SerializedName("City")
    @Expose
    private String city;

    @SerializedName("LastMessage")
    @Expose
    private LastMessage lastMessage;

    @SerializedName("Skills")
    @Expose
    private Skill[] skills;

    public Skill[] getSkills() {
        return skills;
    }

    public void setSkills(Skill[] skills) {
        this.skills = skills;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }
}
