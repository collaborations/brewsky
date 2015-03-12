package edu.uw.informatics.brewsky;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.Serializable;
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
    private boolean isPrivate;
    private String slug;
    private String style;
    private ArrayList<Fermentable> fermentables;
    private ArrayList<Spice> spices;
    private ArrayList<Yeast> yeast;
    private Map<Double, String> timeline;
    private Map<Double, String> timelineImperial;
    private Map<String, String> data;
    private ArrayList<Integer> colorRgb;
    SharedPreferences prefs;

    public Recipe(Map<String, String> data, Context context){
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.data = data;
        this.created = data.remove("created");
        String description = data.remove("description");
        this.desc = (description.equals("")) ? description : "No Description" ;
        this.id = data.remove("id");
        this.name = data.remove("name");
        this.slug = data.remove("slug");
        this.style = data.remove("style");
        this.isPrivate = Boolean.parseBoolean("private");
    }

    /**
     * Returns the name of the recipe
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the recipe's ID
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the recipe's descriptions
     * @return
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Returns the creation date of the recipe
     * @return
     */
    public String getCreated() {
        Log.i("BrewskyImplement", "CREATION DATE OBJECT");
        return created;
    }

    /**
     * Returns the slug for the recipe
     * @return
     */
    public String getSlug() {
        return slug;
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

    // Returns the color of the beer in an ArrayList
    public ArrayList<Integer> getColorRgb(){
        return this.colorRgb;
    }

    // Sets the color of the beer
    public void setColor(ArrayList<Integer> color){
        this.colorRgb = color;
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

    // Sets the timeline
    public void setTimeline(Map<Double, String> timeline, Map<Double, String> timelineImperial){
        this.timeline = timeline;
        this.timelineImperial = timelineImperial;
    }

    //Returns the timeline for the specific measurement setting
    public Map<Double, String> getTimeline(){
        return (isStandard()) ? this.timelineImperial : this.timeline;
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
    public int getAgingDays(){
        return Integer.parseInt(data.get("agingDays"));
    }

    /**
     * Returns aging temperature based on preferences
     * @return
     */
    public int getAgingTemp(){
        return Integer.parseInt(data.get((isFahrenheit()) ? "agingTempF" : "agingTemp"));
    }

    /**
     * Returns batch size.
     * Units are based on user settings.
     * @return
     */
    public double getBatchSize(){
        Log.i("BrewskyImplement", "Batch size based on units");
        return Double.parseDouble(data.get((isStandard()) ? "batchSizeGallons" : "batchSize"));
    }

    /**
     * Returns boil size.
     * Units are based on user settings.
     * @return
     */
    public double getBoilSize(){
        Log.i("BrewskyImplement", "Boil size based on units");
        return Double.parseDouble(data.get((isStandard()) ? "boilSizeGallons" : "boilSize"));
    }

    /**
     * Returns the bottling pressure
     * @return
     */
    public double getBottlingPressure(){
        return Double.parseDouble(data.get("bottlingPressure"));
    }

    /**
     * Returns bottling temperature based on preferences
     * @return
     */
    public double getBottlingTemp(){
        return Double.parseDouble(data.get((isFahrenheit()) ? "bottlingTemp" : "bottlingTempF"));
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
        Log.i("BrewskyImplement", "Mash?");
        return data.get("mash");
    }

    /**
     * Returns the mash efficiency
     */
    public int getMashEfficiency(){
        return Integer.parseInt(data.get("mashEfficiency"));
    }

    /**
     * Returns the number of days in the primary fermenter
     * @return
     */
    public int getPrimaryDays(){
        return Integer.parseInt(data.get("primaryDays"));
    }

    /**
     * Returns the primary fermentation temperature
     * @return
     */
    public double getPrimaryTemp(){
        return Double.parseDouble(data.get((isFahrenheit()) ? "primaryTempF" : "primaryTemp"));
    }

    /**
     * Returns the number of days in the secondary fermenter
     * @return
     */
    public int getSecondaryDays(){
        return Integer.parseInt(data.get("secondaryDays"));
    }

    /**
     * Returns the secondary fermentation temperature
     * @return
     */
    public double getSecondaryTemp(){
        return Double.parseDouble(data.get((isFahrenheit()) ? "secondaryTempF" : "secondaryTemp"));
    }

    /**
     * Returns the serving size
     * @return
     */
    public double getServingSize(){
        Log.i("BrewskyImplement", "STANDARD VS METRIC");
        return Double.parseDouble(data.get((isStandard()) ? "servingSizeOz" : "servingSize"));
    }

    /**
     * Returns the steep efficiency
     * @return
     */
    public int getSteepEfficiency(){
        return Integer.parseInt(data.get("steepEfficiency"));
    }

    /**
     * Returns the steep time
     * @return
     */
    public double getSteepTime(){
        return Double.parseDouble(data.get("steepTime"));
    }

    /**
     * Returns the style of the beer
     * @return
     */
    public String getStyle(){
        return this.style;
    }

    /**
     * Returns the number of days in the tertiary fermenter
     * @return
     */
    public int getTertiaryDays(){
        return Integer.parseInt("tertiaryDays");
    }

    /**
     * Returns the tertiary fermentation temperature
     * @return
     */
    public double getTertiaryTemp(){
        return Double.parseDouble(data.get((isFahrenheit()) ? "tertiaryTempF" : "tertiaryTemp"));
    }

    // Returns alcohol by volume
    public double getABV(){
        return Double.parseDouble(data.get("abv"));
    }

    // Returns alcohol by weight
    public double getABW(){
        return Double.parseDouble(data.get("abw"));
    }

    // Returns brew day duration
    public double getBrewDayDuration(){
        String value = data.get("brewDayDuration");
        return (value.equals("NULL")) ? 0 : Double.parseDouble(value);
    }

    // I don't know what this is?
    public double getBuToGo(){
        return Double.parseDouble(data.get("buToGu"));
    }

    // I don't know what this is?
    public double getBV(){
        return Double.parseDouble(data.get("bv"));
    }

    // Returns the calories
    public double getCalories(){
        return Double.parseDouble(data.get("calories"));
    }

    // Returns the color
    public double getColor(){
        return Double.parseDouble(data.get("color"));
    }

    // Returns some other value for color
    public double getColorEBC(){
        return Double.parseDouble(data.get("colorEbc"));
    }

    // Returns some other value for color
    public double getColorLovibond(){
        return Double.parseDouble(data.get("colorLovibond"));
    }

    // Returns the final gravity
    public double getFinalGravity(){
        return Double.parseDouble(data.get("fg"));
    }

    // Returns the final gravity using Plato calculation?
    public double getFinalGravityPlato(){
        return Double.parseDouble(data.get("fgPlato"));
    }

    // Returns the IBUs
    public double getIBU(){
        return Double.parseDouble(data.get("ibu"));
    }

    // Return the original gravity
    public double getOriginalGravity(){
        return Double.parseDouble(data.get("og"));
    }

    // Return the original gravity using Plato calculation?
    public double getOriginalGravityPlato(){
        return Double.parseDouble(data.get("ogPlato"));
    }

    // Returns the price
    public double getPrice(){
        return Double.parseDouble(data.get("price"));
    }

    // Returns the real extract
    public double getRealExtract(){
        return Double.parseDouble(data.get("realExtract"));
    }

    // Returns if the recipe is private or not
    public boolean isPrivate(){
        return this.isPrivate;
    }

    public String toString(){
        return this.name;
    }

    /*
     * Private helper methods
     */

    /**
     * Returns true if using Fahrenheit
     * @return
     */
    private boolean isFahrenheit(){
        return prefs.getBoolean("temperature_scale", false);
    }

    /**
     * Returns true if using standard (U.S.) measurement
     * @return
     */
    private boolean isStandard(){
        return prefs.getBoolean("measurement_scale", true);
    }
}
