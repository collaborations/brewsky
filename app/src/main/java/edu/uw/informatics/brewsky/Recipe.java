package edu.uw.informatics.brewsky;

import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 *
 * This class represents any given recipe pulled from the API. In the case of items stored in the
 * data map, mainly minor properties, I have also commented above how they are stored in the map
 * with an example. This is separate from the Javadoc comments and will be removed later.
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
    private ArrayList<Fermentable> fermentables;
    private ArrayList<Spice> spices;
    private ArrayList<Yeast> yeast;
    private Resources r;
    private Map<String, String> data;

    public Recipe(Map<String, String> data){
        this.data = data;
        r = Resources.getSystem();
        this.created = data.remove("created");
        String description = data.remove("description");
        this.desc = (description.equals("")) ? description : r.getString(R.string.no_recipe_desc) ;
        this.id = data.remove("id");
        this.name = data.remove("name");
        this.slug = data.remove("slug");
        Log.i(r.getString(R.string.log_implement), "isPrivate");
        Log.i(r.getString(R.string.log_implement), "Style");
    }

    /**
     * Gets the brewer, or author of this recipe
     * @return
     */
    public Brewer getBrewer(){
        return this.brewer;
    }

    /**
     * Sets the brewer, or author of this recipe
     * @param brewer
     */
    public void setBrewer(Brewer brewer){
        this.brewer = brewer;
    }

    /**
     * Gets the fermentable items in the recipe, such as grains and malt
     * @return
     */
    public ArrayList<Fermentable> getFermentables() {
        return fermentables;
    }

    /**
     * Sets the fermentable items in the recipe, such as grains and malt
     * @param fermentables
     */
    public void setFermentables(ArrayList<Fermentable> fermentables) {
        this.fermentables = fermentables;
    }

    /**
     * Gets the types of spices used in the recipe, such as the hops
     * @return
     */
    public ArrayList<Spice> getSpices(){
        return this.spices;
    }

    /**
     * Sets the types of spices used in the recipe, such as the hops
     * @param spices
     */
    public void setSpices(ArrayList<Spice> spices){
        this.spices = spices;
    }

    /**
     * Returns the types of yeast used in the recipe
     * @return
     */
    public ArrayList<Yeast> getYeast() {
        return yeast;
    }

    /**
     * Sets the types of yeast used in the recipe
     * @param yeast
     */
    public void setYeast(ArrayList<Yeast> yeast) {
        this.yeast = yeast;
    }

    /**
     * Return the number of days for aging
     * @return
     */
    //    "agingDays": 14,
    public int getAgingDays(){
        return Integer.parseInt(data.get("agingDays"));
    }

    /**
     * Returns aging temperature based on preferences
     * @return
     */
    //    "agingTemp": 20,
    public int getAgingTemp(){
        Log.i(r.getString(R.string.log_implement), "C/F based on settings");
        return Integer.parseInt(data.get("agingTemp"));
    }

    /**
     * Returns batch size.
     * Units are based on user settings.
     * @return
     */
    //    "batchSize": 20,
    //    "batchSizeGallons": 5.283440000000001,
    public double getBatchSize(){
        Log.i(r.getString(R.string.log_implement), "Batch size based on units");
        return Double.parseDouble(data.get("batchSize"));
    }

    /**
     * Returns boil size.
     * Units are based on user settings.
     * @return
     */
    //    "boilSize": 10,
    //    "boilSizeGallons": 2.6417200000000003,
    public double getBoilSize(){
        Log.i(r.getString(R.string.log_implement), "Boil size based on units");
        return Double.parseDouble(data.get("boilSize"));
    }

    /**
     * Returns the bottling pressure
     * @return
     */
    //    "bottlingPressure": 2.5,
    public double getBottlingPressure(){
        return Double.parseDouble(data.get("bottlingPressure"));
    }

    /**
     * Returns bottling temperature based on preferences
     * @return
     */
    //    "bottlingTemp": 23,
    //    "bottlingTempF": 73.4,
    public double getBottlingTemp(){
        Log.i(r.getString(R.string.log_implement), "C/F based on settings");
        return Double.parseDouble(data.get("bottlingTemp"));
    }

    /**
     * Returns the IBU calculation method.
     * @return
     */
    //    "ibuMethod": "tinseth",
    public String getIBUMethod(){
        return data.get("ibuMethod");
    }

    /**
     * Not really sure, I assume this means whether or not it requires a mash
     * @return
     */
    //    "mash": null,
    public String getMash(){
        Log.i(r.getString(R.string.log_implement), "Mash?");
        return data.get("mash");
    }

    /**
     * Returns the mash efficiency
     */
    //    "mashEfficiency": 75,
    public int getMashEfficiency(){
        return Integer.parseInt(data.get("mashEfficiency"));
    }

    /**
     * Returns the number of days in the primary fermenter
     * @return
     */
    //    "primaryDays": 14,
    public int getPrimaryDays(){
        return Integer.parseInt(data.get("primaryDays"));
    }

    /**
     * Returns the primary fermentation temperature
     * @return
     */
    //    "primaryTemp": 20,
    //    "primaryTempF": 68,
    public double getPrimaryTemp(){
        Log.i(r.getString(R.string.log_implement), "C/F based on settings");
        return Double.parseDouble(data.get("primaryTemp"));
    }

    /**
     * Returns the number of days in the secondary fermenter
     * @return
     */
    //    "secondaryDays": 0,
    public int getSecondaryDays(){
        return Integer.parseInt(data.get("secondaryDays"));
    }

    /**
     * Returns the secondary fermentation temperature
     * @return
     */
    //    "secondaryTemp": 0,
    //    "secondaryTempF": 32,
    public double getSecondaryTemp(){
        Log.i(r.getString(R.string.log_implement), "C/F based on settings");
        return Double.parseDouble(data.get("secondaryTemp"));
    }

    /**
     * Returns the serving size
     * @return
     */
    //    "servingSize": 0.355,
    //    "servingSizeOz": 0.09378106,
    public double getServingSize(){
        return Double.parseDouble(data.get("servingSize"));
    }

    /**
     * Returns the steep efficiency
     * @return
     */
    //    "steepEfficiency": 50,
    public int getSteepEfficiency(){
        return Integer.parseInt(data.get("steepEfficiency"));
    }

    /**
     * Returns the steep time
     * @return
     */
    //    "steepTime": 20,
    public double getSteepTime(){
        return Double.parseDouble(data.get("steepTime"));
    }

    /**
     * Returns the style of the beer
     * @return
     */
    //    "style": null,
    public String getStyle(){
        return data.get("style");
    }

    /**
     * Returns the number of days in the tertiary fermenter
     * @return
     */
    //    "tertiaryDays": 0,
    public int getTertiaryDays(){
        return Integer.parseInt("tertiaryDays");
    }

    /**
     * Returns the tertiary fermentation temperature
     * @return
     */
    //    "tertiaryTemp": 0,
    //    "tertiaryTempF": 32,
    public double getTertiaryTemp(){
        Log.i(r.getString(R.string.log_implement), "C/F based on settings");
        return Double.parseDouble(data.get("tertiaryTemp"));
    }

    public String toString(){
        return this.name;
    }
}
