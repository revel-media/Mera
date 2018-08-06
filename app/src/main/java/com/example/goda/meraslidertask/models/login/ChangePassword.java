package com.example.goda.meraslidertask.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePassword {

    @SerializedName("result")
    @Expose
    private Integer result;

    public ChangePassword() {
    }

    public ChangePassword(Integer result) {
        super();
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
