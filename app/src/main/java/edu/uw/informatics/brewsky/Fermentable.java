package edu.uw.informatics.brewsky;

import android.content.res.Resources;
import android.util.Log;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */

//{
//    "name": "Extra pale extract",
//    "weight": 4,
//    "yield": 75,
//    "color": 2.5,
//    "late": false
//}

public class Fermentable implements Serializable {
    private String name;
    private double weight;
    private double yield;
    private double color;
    private boolean late;

    public Fermentable(Map<String, String> data){
        this.name = data.get("name");
        this.weight = Double.parseDouble(data.get("weight"));
        this.yield = Double.parseDouble(data.get("yield"));
        this.color = Double.parseDouble(data.get("color"));
        this.late = Boolean.parseBoolean(data.get("late"));
    }

    public String getName(){
        return this.name;
    }

    public double getWeight() {
        return weight;
    }

    public double getYield() {
        return yield;
    }

    public double getColor() {
        return color;
    }

    public boolean isLate() {
        return late;
    }
}
