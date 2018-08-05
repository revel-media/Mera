package com.example.goda.meraslidertask.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("result")
    @Expose
    private LoginResults result;

    public Login() {
    }

    public Login(LoginResults result) {
        super();
        this.result = result;
    }

    public LoginResults getResult() {
        return result;
    }

    public void setResult(LoginResults result) {
        this.result = result;
    }

}
