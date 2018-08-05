package com.example.goda.meraslidertask.models.slider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Slider {
    @SerializedName("result")
    @Expose
    private List<SliderResults> result = null;

    public Slider() {
    }

    public Slider(List<SliderResults> result) {
        super();
        this.result = result;
    }

    public List<SliderResults> getResult() {
        return result;
    }

    public void setResult(List<SliderResults> result) {
        this.result = result;
    }

}
