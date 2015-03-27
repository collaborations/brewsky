package edu.uw.informatics.brewsky.models;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */

public class Fermentable {
    @SerializedName("name")
    public String name;

    @SerializedName("weight")
    public double weight;

    @SerializedName("yield")
    public int yield;

    @SerializedName("color")
    public int color;

    @SerializedName("late")
    public boolean late;

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

    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isLate() {
        return late;
    }

    public void setLate(boolean late) {
        this.late = late;
    }
}
