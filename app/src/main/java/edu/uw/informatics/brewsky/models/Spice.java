package edu.uw.informatics.brewsky.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */

public class Spice {
    @SerializedName("name")
    public String name;

    @SerializedName("weight")
    public double weight;

    @SerializedName("aa")
    public int aa;

    @SerializedName("use")
    public String use;

    @SerializedName("time")
    public double time;

    @SerializedName("form")
    public String form;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAa() {
        return aa;
    }

    public void setAa(int aa) {
        this.aa = aa;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
