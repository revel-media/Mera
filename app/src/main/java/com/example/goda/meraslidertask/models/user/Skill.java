package com.example.goda.meraslidertask.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ahmed Ali on 8/6/2018.
 */

public class Skill implements Serializable {

    @SerializedName("Skill_ID")
    @Expose
    private int id;


    @SerializedName("Name")
    @Expose
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
