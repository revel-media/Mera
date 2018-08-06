package com.example.goda.meraslidertask.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ahmed Ali on 8/6/2018.
 */

public class Helps implements Serializable{

    @SerializedName("result")
    @Expose
    private Conversation[] conversations;

    public Conversation[] getConversations() {
        return conversations;
    }

    public void setConversations(Conversation[] conversations) {
        this.conversations = conversations;
    }
}
