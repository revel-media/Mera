package com.example.goda.meraslidertask.models.skills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkillsResults {

    @SerializedName("Skill_ID")
    @Expose
    private String skillID;
    @SerializedName("Skill_en")
    @Expose
    private String skillEn;
    @SerializedName("Skill_ar")
    @Expose
    private String skillAr;

    public SkillsResults() {
    }

    public SkillsResults(String skillID, String skillEn, String skillAr) {
        super();
        this.skillID = skillID;
        this.skillEn = skillEn;
        this.skillAr = skillAr;
    }

    public String getSkillID() {
        return skillID;
    }

    public void setSkillID(String skillID) {
        this.skillID = skillID;
    }

    public String getSkillEn() {
        return skillEn;
    }

    public void setSkillEn(String skillEn) {
        this.skillEn = skillEn;
    }

    public String getSkillAr() {
        return skillAr;
    }

    public void setSkillAr(String skillAr) {
        this.skillAr = skillAr;
    }
}
