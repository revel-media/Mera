package com.example.goda.meraslidertask.models.user;

import com.example.goda.meraslidertask.models.login.LoginResults;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ahmed Ali on 8/6/2018.
 */

public class UserConversation implements Serializable{

    @SerializedName("result")
    @Expose
    private UserConversationResult result;

    public UserConversationResult getResult() {
        return result;
    }

    public void setResult(UserConversationResult result) {
        this.result = result;
    }
}
