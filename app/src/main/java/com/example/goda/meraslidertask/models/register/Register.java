package com.example.goda.meraslidertask.models.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register {

    @SerializedName("result")
    @Expose
    private RegisterResults result;

    public Register() {
    }

    public Register(RegisterResults result) {
        super();
        this.result = result;
    }

    public RegisterResults getResult() {
        return result;
    }

    public void setResult(RegisterResults result) {
        this.result = result;
    }
}
