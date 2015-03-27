package edu.uw.informatics.brewsky.models;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import edu.uw.informatics.brewsky.models.Brewer;
import edu.uw.informatics.brewsky.models.Fermentable;
import edu.uw.informatics.brewsky.models.Spice;
import edu.uw.informatics.brewsky.models.Yeast;

/**
 * Created by ginoclement on 3/10/15.
 *
 * This class represents any given recipeData pulled from the API. In the case of items stored in the
 * data map, mainly minor properties, I have also commented above how they are stored in the map
 * with an example. This is separate from the Javadoc comments and will be removed later.
 */
public class RecipeData {
    @SerializedName("agingDays")
    private int agingDays;

    @SerializedName("agingTemp")
    private int agingTemp;

    @SerializedName("author")
    private String author;

    @SerializedName("batchSize")
    private int batchSize;

    @SerializedName("boilSize")
    private int boilSize;

    @SerializedName("bottlingPressure")
    private double bottlingPressure;

    @SerializedName("bottlingTemp")
    private int bottlingTemp;

    @SerializedName("description")
    private String description;

    @SerializedName("fermentables")
    private Fermentable[] fermentables;

    @SerializedName("ibuMethod")
    private String ibuMethod;

    @SerializedName("mash")
    private String mash;

    @SerializedName("primaryDays")
    private int primaryDays;

    @SerializedName("primaryTemp")
    private int primaryTemp;

    @SerializedName("secondaryDays")
    private int secondaryDays;

    @SerializedName("secondaryTemp")
    private int secondaryTemp;

    @SerializedName("tertiaryDays")
    private int tertiaryDays;

    @SerializedName("tertiaryTemp")
    private int tertiaryTemp;

    @SerializedName("servingSize")
    private int servingSize;

    @SerializedName("spices")
    private Spice[] spices;

    @SerializedName("steepEfficiency")
    private int steepEfficiency;

    @SerializedName("steepTime")
    private int steepTime;

    @SerializedName("style")
    private String style;

    @SerializedName("yeast")
    private Yeast yeast;

    @SerializedName("abv")
    private double abv;

    @SerializedName("abw")
    private double abw;

    @SerializedName("brewDayDuration")
    private double brewDayDuration;

    @SerializedName("buToGu")
    private double buToGu;

    @SerializedName("bv")
    private double bv;

    @SerializedName("calories")
    private double calories;

    @SerializedName("color")
    private double color;

    @SerializedName("colorEbc")
    private double colorEbc;

    @SerializedName("colorLovibond")
    private double colorLovibond;

    @SerializedName("colorRgb")
    private String colorRgb;

    @SerializedName("fg")
    private double fg;

    @SerializedName("fgPlato")
    private double fgPlato;

    @SerializedName("ibu")
    private double ibu;

    @SerializedName("og")
    private double og;

    @SerializedName("ogPlato")
    private double ogPlato;

    @SerializedName("price")
    private double price;

    @SerializedName("realExtract")
    private double realExtract;

    @SerializedName("timeline")
    private String timeline;

    public int getAgingDays() {
        return agingDays;
    }

    public void setAgingDays(int agingDays) {
        this.agingDays = agingDays;
    }

    public int getAgingTemp() {
        return agingTemp;
    }

    public void setAgingTemp(int agingTemp) {
        this.agingTemp = agingTemp;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getBoilSize() {
        return boilSize;
    }

    public void setBoilSize(int boilSize) {
        this.boilSize = boilSize;
    }

    public double getBottlingPressure() {
        return bottlingPressure;
    }

    public void setBottlingPressure(double bottlingPressure) {
        this.bottlingPressure = bottlingPressure;
    }

    public int getBottlingTemp() {
        return bottlingTemp;
    }

    public void setBottlingTemp(int bottlingTemp) {
        this.bottlingTemp = bottlingTemp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Fermentable[] getFermentables() {
        return fermentables;
    }

    public void setFermentables(Fermentable[] fermentables) {
        this.fermentables = fermentables;
    }

    public String getIbuMethod() {
        return ibuMethod;
    }

    public void setIbuMethod(String ibuMethod) {
        this.ibuMethod = ibuMethod;
    }

    public String getMash() {
        return mash;
    }

    public void setMash(String mash) {
        this.mash = mash;
    }

    public int getPrimaryDays() {
        return primaryDays;
    }

    public void setPrimaryDays(int primaryDays) {
        this.primaryDays = primaryDays;
    }

    public int getPrimaryTemp() {
        return primaryTemp;
    }

    public void setPrimaryTemp(int primaryTemp) {
        this.primaryTemp = primaryTemp;
    }

    public int getSecondaryDays() {
        return secondaryDays;
    }

    public void setSecondaryDays(int secondaryDays) {
        this.secondaryDays = secondaryDays;
    }

    public int getSecondaryTemp() {
        return secondaryTemp;
    }

    public void setSecondaryTemp(int secondaryTemp) {
        this.secondaryTemp = secondaryTemp;
    }

    public int getTertiaryDays() {
        return tertiaryDays;
    }

    public void setTertiaryDays(int tertiaryDays) {
        this.tertiaryDays = tertiaryDays;
    }

    public int getTertiaryTemp() {
        return tertiaryTemp;
    }

    public void setTertiaryTemp(int tertiaryTemp) {
        this.tertiaryTemp = tertiaryTemp;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public Spice[] getSpices() {
        return spices;
    }

    public void setSpices(Spice[] spices) {
        this.spices = spices;
    }

    public int getSteepEfficiency() {
        return steepEfficiency;
    }

    public void setSteepEfficiency(int steepEfficiency) {
        this.steepEfficiency = steepEfficiency;
    }

    public int getSteepTime() {
        return steepTime;
    }

    public void setSteepTime(int steepTime) {
        this.steepTime = steepTime;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Yeast getYeast() {
        return yeast;
    }

    public void setYeast(Yeast yeast) {
        this.yeast = yeast;
    }

    public double getAbv() {
        return abv;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public double getAbw() {
        return abw;
    }

    public void setAbw(double abw) {
        this.abw = abw;
    }

    public double getBrewDayDuration() {
        return brewDayDuration;
    }

    public void setBrewDayDuration(double brewDayDuration) {
        this.brewDayDuration = brewDayDuration;
    }

    public double getBuToGu() {
        return buToGu;
    }

    public void setBuToGu(double buToGu) {
        this.buToGu = buToGu;
    }

    public double getBv() {
        return bv;
    }

    public void setBv(double bv) {
        this.bv = bv;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getColor() {
        return color;
    }

    public void setColor(double color) {
        this.color = color;
    }

    public double getColorEbc() {
        return colorEbc;
    }

    public void setColorEbc(double colorEbc) {
        this.colorEbc = colorEbc;
    }

    public double getColorLovibond() {
        return colorLovibond;
    }

    public void setColorLovibond(double colorLovibond) {
        this.colorLovibond = colorLovibond;
    }

    public String getColorRgb() {
        return colorRgb;
    }

    public void setColorRgb(String colorRgb) {
        this.colorRgb = colorRgb;
    }

    public double getFg() {
        return fg;
    }

    public void setFg(double fg) {
        this.fg = fg;
    }

    public double getFgPlato() {
        return fgPlato;
    }

    public void setFgPlato(double fgPlato) {
        this.fgPlato = fgPlato;
    }

    public double getIbu() {
        return ibu;
    }

    public void setIbu(double ibu) {
        this.ibu = ibu;
    }

    public double getOg() {
        return og;
    }

    public void setOg(double og) {
        this.og = og;
    }

    public double getOgPlato() {
        return ogPlato;
    }

    public void setOgPlato(double ogPlato) {
        this.ogPlato = ogPlato;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRealExtract() {
        return realExtract;
    }

    public void setRealExtract(double realExtract) {
        this.realExtract = realExtract;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }
}
