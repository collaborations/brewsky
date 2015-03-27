package edu.uw.informatics.brewsky.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by ginoclement on 3/26/15.
 */
public class Recipe {
    @SerializedName("id")
    public String id;

    @SerializedName("parent")
    public String parent;

    @SerializedName("user")
    public Brewer brewer;

    @SerializedName("slug")
    public String slug;

    @SerializedName("created")
    public Date created;

    @SerializedName("private")
    public boolean isPrivate;

    @SerializedName("data")
    public RecipeData recipeData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Brewer getBrewer() {
        return brewer;
    }

    public void setBrewer(Brewer brewer) {
        this.brewer = brewer;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public RecipeData getRecipeData() {
        return recipeData;
    }

    public void setRecipeData(RecipeData recipeData) {
        this.recipeData = recipeData;
    }
}
