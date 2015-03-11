package edu.uw.informatics.brewsky;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */

//{
//   "name": "Wyeast 3052",
//   "type": "ale",
//   "form": "liquid",
//   "attenuation": 74
//}


public class Yeast implements Serializable {
    private String name;
    private String type;
    private String form;
    private double attenuation;

    public Yeast(Map<String, String> data){
        this.name = data.get("name");
        this.type = data.get("type");
        this.form = data.get("form");
        this.attenuation = Double.parseDouble(data.get("attenuation"));
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getForm() {
        return form;
    }

    public double getAttenuation() {
        return attenuation;
    }


}
