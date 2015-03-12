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
    private ArrayList<Recipe> recipesList;
    private ArrayList<Recipe> overallRecipeList;
//    private HashSet<Recipe> recipesList;
    private HashMap<String, Recipe> recipesByID;
    private String type = "All";
    private String abv = "All";

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
        recipesList = new ArrayList<Recipe>();
        overallRecipeList = new ArrayList<Recipe>();
        recipesByID = new HashMap<>();
        loadRecipes();
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
            recipesList.add(recipe);
            overallRecipeList.add(recipe);
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
        if(this.recipesList.size() == 0){
            loadRecipes();
        }
        return this.overallRecipeList;
    }

    public ArrayList<Recipe> getRecipes(Map<String, String> filter) {
        ArrayList<Recipe> result = new ArrayList<Recipe>();
        Log.i("Test", "here");
        for (String key : filter.keySet()) {
            Log.i("map", key + " : " + filter.get(key));
        }

        for (Recipe recipe : overallRecipeList) {
            Log.i("asdf", recipe.getABV() + "");
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
            boolean typeTest = type.equals("All") || recipe.getStyle().equals(type);
            if (abvTest && typeTest) {
                result.add(recipe);
            }
        }
        Log.i("current", overallRecipeList.toString());
        Log.i("result", result.toString());
        recipesList = result;
        return result;
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
        return recipesList.size();
    }

}
