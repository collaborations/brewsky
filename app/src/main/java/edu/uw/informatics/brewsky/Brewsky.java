package edu.uw.informatics.brewsky;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ginoclement on 3/10/15.
 */
public class Brewsky
        extends Application
        implements RecipeRepository {

    private static Brewsky instance;
    private ArrayList<Recipe> recipeList;
    private HashMap<String, Recipe> recipesByID;
    private HashMap<String, ArrayList<String>> commentsByRecipeID;
    private String type = "All";
    private String abv = "All";
    private String rating = "All";
    private Map<String, String> beers;

    /* Application manifest throws an error if I set this as private. I don't believe we want a
     * public constructor though, otherwise you could create another app instance.
     */
    public Brewsky(){

    }

    public static Brewsky getInstance(){
        return instance;
    }

    public static void initInstance(){
        if(instance == null){
            instance = new Brewsky();
        }
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(getString(R.string.log_general), "Brewsky has been launched");
        recipeList = new ArrayList<Recipe>();
        recipesByID = new HashMap<>();
        commentsByRecipeID = new HashMap<>();
        beers = new HashMap<String, String>();
        addBeers();
        loadRecipes();
    }

    private void addBeers() {
        beers.put("american-pale-ale", "APA");
        beers.put("dark-beer", "Stout");
        beers.put("hefeweizen", "Hefeweizen");
        beers.put("irish-red", "Amber Ale");
        beers.put("test-recipe", "IPA");

    }

    public Brewsky getApplication(){
        return this.instance;
    }

    public void loadRecipes(){
        if(getRecipeCount() == 0) {
            Intent recipeDownloadService = new Intent(Brewsky.this, RecipeDownloadService.class);
            startService(recipeDownloadService);
        }
    }

    // Adds a single given recipe to the repository.
    public void addRecipe(Recipe recipe){
        if(!recipesByID.containsKey(recipe.getId())){
            recipesByID.put(recipe.getId(), recipe);
            recipeList.add(recipe);
        }
    }

    // Adds an ArrayList of recipes.
    public void addRecipes(ArrayList<Recipe> recipes){
        for (Recipe each: recipes){
            addRecipe(each);
        }
    }

    // Return the entire list of recipes.
    public ArrayList<Recipe> getRecipes(){
        if(this.recipeList.size() == 0){
            loadRecipes();
        }
        return this.recipeList;
    }

    public ArrayList<Recipe> getRecipes(boolean t) {
        ArrayList<Recipe> filtered = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            boolean abvTest = false;
            if (abv.equals("All")) {
                abvTest = true;
            } else {
                String s = abv;
                s = s.substring(0, s.length() - 1);
                String[] range = s.split("-");
                abvTest = recipe.getABV() >= Double.parseDouble(range[0]) && recipe.getABV() <
                        Double.parseDouble(range[1]);
            }
            boolean typeTest = type.equals("All") || beers.get(recipe.getSlug()).equals(type);
            boolean ratingTest = rating.equals("All") || recipe.getRating() == Float.parseFloat(rating.split(" ")[0]);
            if (abvTest && typeTest && ratingTest) {
                filtered.add(recipe);
            }
        }
        Log.i("current", recipeList.toString());
        Log.i("result", filtered.toString());
        return filtered;
    }

    public String getType() {
        return type;
    }

    public String getAbv() {
        return abv;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    // Return a recipe with the give ID
    public Recipe getRecipeByID(String id){
        return recipesByID.get(id);
    }

    // Returns the number of recipes on the phone
    public int getRecipeCount(){
        return recipeList.size();
    }

    public void addComment(String id, String comment) {
        if(!commentsByRecipeID.containsKey(id)) {
            commentsByRecipeID.put(id, new ArrayList<String>());
        }
        commentsByRecipeID.get(id).add(comment);
    }

    public ArrayList<String> getCommentsByID(String id) {
        return commentsByRecipeID.get(id);
    }

}
