package com.example.goda.meraslidertask.models.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResults {

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public RegisterResults() {
    }

    public RegisterResults(Integer userId) {
        super();
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
