package edu.uw.informatics.brewsky.models;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */

public class Yeast {
    private String name;
    private String type;
    private String form;
    private double attenuation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public double getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(double attenuation) {
        this.attenuation = attenuation;
    }
}
