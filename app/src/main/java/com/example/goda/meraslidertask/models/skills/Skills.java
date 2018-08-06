package com.example.goda.meraslidertask.models.skills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Skills {
    @SerializedName("result")
    @Expose
    private List<SkillsResults> result = null;

    public Skills() {
    }

    public Skills(List<SkillsResults> result) {
        super();
        this.result = result;
    }

    public List<SkillsResults> getResult() {
        return result;
    }

    public void setResult(List<SkillsResults> result) {
        this.result = result;
    }
}
