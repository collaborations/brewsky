package edu.uw.informatics.brewsky;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */

//    {
//        "name": "Chinook",
//        "weight": 0.018,
//        "aa": 12,
//        "use": "boil",
//        "time": 60,
//        "form": "pellet"
//    }

public class Spice {
    private String name;
    private double weight;
    private double aa;
    private String use;
    private double time;
    private String form;

    public Spice(Map<String, String> data){
        this.name = data.get("name");
        this.weight = Double.parseDouble(data.get("weight"));
        this.aa = Double.parseDouble(data.get("aa"));
        this.use = data.get("use");
        this.time = Double.parseDouble(data.get("time"));
        this.form = data.get("form");
    }

    public String getName(){
        return this.name;
    }

    public double getWeight(){
        return this.weight;
    }

    public double getAA(){
        return this.aa;
    }

    public String getUse(){
        return this.use;
    }

    public double getTime(){
        return this.time;
    }

    public String getForm(){
        return this.form;
    }


}
