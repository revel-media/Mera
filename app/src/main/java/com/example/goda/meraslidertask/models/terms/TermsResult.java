package com.example.goda.meraslidertask.models.terms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsResult {

    @SerializedName("Terms_Conditions_en")
    @Expose
    private String termsConditionsEn;
    @SerializedName("Terms_Conditions_ar")
    @Expose
    private String termsConditionsAr;

    public TermsResult() {
    }

    public TermsResult(String termsConditionsEn, String termsConditionsAr) {
        super();
        this.termsConditionsEn = termsConditionsEn;
        this.termsConditionsAr = termsConditionsAr;
    }

    public String getTermsConditionsEn() {
        return termsConditionsEn;
    }

    public void setTermsConditionsEn(String termsConditionsEn) {
        this.termsConditionsEn = termsConditionsEn;
    }

    public String getTermsConditionsAr() {
        return termsConditionsAr;
    }

    public void setTermsConditionsAr(String termsConditionsAr) {
        this.termsConditionsAr = termsConditionsAr;
    }

}
