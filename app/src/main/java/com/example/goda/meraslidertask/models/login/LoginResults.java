package com.example.goda.meraslidertask.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResults {

    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("Type_ID")
    @Expose
    private String typeID;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("Fullname")
    @Expose
    private String fullname;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Phone")
    @Expose
    private Object phone;
    @SerializedName("Iqama_No")
    @Expose
    private String iqamaNo;
    @SerializedName("Address")
    @Expose
    private Object address;
    @SerializedName("City")
    @Expose
    private Object city;
    @SerializedName("BuildingInfo")
    @Expose
    private Object buildingInfo;
    @SerializedName("Location")
    @Expose
    private Object location;
    @SerializedName("Email_Verified")
    @Expose
    private String emailVerified;
    @SerializedName("Reset_Password")
    @Expose
    private String resetPassword;
    @SerializedName("Picture")
    @Expose
    private Object picture;
    @SerializedName("Is_Available")
    @Expose
    private String isAvailable;
    @SerializedName("Registered_At")
    @Expose
    private String registeredAt;

    public LoginResults() {
    }

    public LoginResults(String userID, String typeID, String userType, String fullname, String email, Object phone, String iqamaNo, Object address, Object city, Object buildingInfo, Object location, String emailVerified, String resetPassword, Object picture, String isAvailable, String registeredAt) {
        super();
        this.userID = userID;
        this.typeID = typeID;
        this.userType = userType;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.iqamaNo = iqamaNo;
        this.address = address;
        this.city = city;
        this.buildingInfo = buildingInfo;
        this.location = location;
        this.emailVerified = emailVerified;
        this.resetPassword = resetPassword;
        this.picture = picture;
        this.isAvailable = isAvailable;
        this.registeredAt = registeredAt;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public String getIqamaNo() {
        return iqamaNo;
    }

    public void setIqamaNo(String iqamaNo) {
        this.iqamaNo = iqamaNo;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getBuildingInfo() {
        return buildingInfo;
    }

    public void setBuildingInfo(Object buildingInfo) {
        this.buildingInfo = buildingInfo;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(String resetPassword) {
        this.resetPassword = resetPassword;
    }

    public Object getPicture() {
        return picture;
    }

    public void setPicture(Object picture) {
        this.picture = picture;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(String registeredAt) {
        this.registeredAt = registeredAt;
    }
}
