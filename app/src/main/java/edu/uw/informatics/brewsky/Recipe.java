package edu.uw.informatics.brewsky;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */
public class Recipe {
    private Brewer brewer;
    private String created;
    private String desc;
    private String id;
    private String name;
//    private boolean isPrivate;
    private String slug;
//    private String style;
    private ArrayList<Spice> spices;
    private Map<String, String> properties;


    public Recipe(){

    }

    public Recipe(String name, String style, Map<String, String> data){
        this.name = (data.containsKey("name")) ? data.remove("name") : "No Name";
        this.id = (data.containsKey("id")) ? data.remove("name") : "No ID";
        this.created = (data.containsKey("created")) ? data.remove("created") : "No Creation Date";
        this.slug = (data.containsKey("slug")) ? data.remove("slug") : "No Slug";
        spices = new ArrayList<>();
    }

    public ArrayList<Spice> getSpices(){
        return this.spices;
    }

    public void setSpices(ArrayList<Spice> spices){
        this.spices = spices;
    }


}
