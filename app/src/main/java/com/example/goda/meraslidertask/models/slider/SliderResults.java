package com.example.goda.meraslidertask.models.slider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderResults {

    @SerializedName("Slide_ID")
    @Expose
    private String slideID;
    @SerializedName("Title_en")
    @Expose
    private String titleEn;
    @SerializedName("Title_ar")
    @Expose
    private String titleAr;
    @SerializedName("Slide_Image")
    @Expose
    private String slideImage;
    @SerializedName("Slide_Caption_en")
    @Expose
    private String slideCaptionEn;
    @SerializedName("Slide_Caption_ar")
    @Expose
    private String slideCaptionAr;
    @SerializedName("Target_Link")
    @Expose
    private String targetLink;
    @SerializedName("Order_In_List")
    @Expose
    private String orderInList;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Caption_Status")
    @Expose
    private String captionStatus;
    @SerializedName("Date")
    @Expose
    private String date;

    public SliderResults() {
    }

    public SliderResults(String slideID, String titleEn, String titleAr, String slideImage, String slideCaptionEn, String slideCaptionAr, String targetLink, String orderInList, String status, String captionStatus, String date) {
        super();
        this.slideID = slideID;
        this.titleEn = titleEn;
        this.titleAr = titleAr;
        this.slideImage = slideImage;
        this.slideCaptionEn = slideCaptionEn;
        this.slideCaptionAr = slideCaptionAr;
        this.targetLink = targetLink;
        this.orderInList = orderInList;
        this.status = status;
        this.captionStatus = captionStatus;
        this.date = date;
    }

    public String getSlideID() {
        return slideID;
    }

    public void setSlideID(String slideID) {
        this.slideID = slideID;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getSlideImage() {
        return slideImage;
    }

    public void setSlideImage(String slideImage) {
        this.slideImage = slideImage;
    }

    public String getSlideCaptionEn() {
        return slideCaptionEn;
    }

    public void setSlideCaptionEn(String slideCaptionEn) {
        this.slideCaptionEn = slideCaptionEn;
    }

    public String getSlideCaptionAr() {
        return slideCaptionAr;
    }

    public void setSlideCaptionAr(String slideCaptionAr) {
        this.slideCaptionAr = slideCaptionAr;
    }

    public String getTargetLink() {
        return targetLink;
    }

    public void setTargetLink(String targetLink) {
        this.targetLink = targetLink;
    }

    public String getOrderInList() {
        return orderInList;
    }

    public void setOrderInList(String orderInList) {
        this.orderInList = orderInList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaptionStatus() {
        return captionStatus;
    }

    public void setCaptionStatus(String captionStatus) {
        this.captionStatus = captionStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
