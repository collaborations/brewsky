package edu.uw.informatics.brewsky;

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
    private int aa;
    private String use;
    private int time;
    private String form;

    public Spice(Map<String, String> data){
        this.name = data.get("name");
        this.weight = Double.parseDouble(data.get("weight"));
        this.aa = Integer.parseInt(data.get("aa"));
        this.use = data.get("use");
        this.time = Integer.parseInt(data.get("time"));
        this.form = data.get("form");
    }

    public String getName(){
        return this.name;
    }

    public double getWeight(){
        return this.weight;
    }

    public int getAA(){
        return this.aa;
    }

    public String getUse(){
        return this.use;
    }

    public int getTime(){
        return this.time;
    }

    public String getForm(){
        return this.form;
    }


}
