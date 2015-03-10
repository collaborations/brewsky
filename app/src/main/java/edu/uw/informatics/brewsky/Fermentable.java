package edu.uw.informatics.brewsky;

import android.content.res.Resources;
import android.util.Log;

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

public class Fermentable {
    private String name;
    private int weight;
    private int yield;
    private double color;
    private boolean late;
    private Resources r;

    public Fermentable(Map<String, String> data){
        r = Resources.getSystem();
        this.name = data.get("name");
        this.weight = Integer.parseInt(data.get("weight"));
        this.yield = Integer.parseInt(data.get("yield"));
        this.color = Double.parseDouble(data.get("color"));
        Log.i(r.getString(R.string.log_implement), "Fermentable: Late?");
//        this.late = data.get("late")
    }

    public String getName(){
        return this.name;
    }

    public int getWeight() {
        return weight;
    }

    public int getYield() {
        return yield;
    }

    public double getColor() {
        return color;
    }

    public boolean isLate() {
        return late;
    }
}
