package edu.uw.informatics.brewsky.models;

import android.util.Log;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */

public class Brewer {
    private String id;
    private String name;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
