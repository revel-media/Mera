package com.example.goda.meraslidertask.models.terms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Terms {


    @SerializedName("result")
    @Expose
    private List<TermsResult> result = null;

    public Terms() {
    }

    public Terms(List<TermsResult> result) {
        super();
        this.result = result;
    }

    public List<TermsResult> getResult() {
        return result;
    }

    public void setResult(List<TermsResult> result) {
        this.result = result;
    }
}
