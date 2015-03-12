package edu.uw.informatics.brewsky;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
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
    private HashMap<String, HashSet<String>> recipesByStyle;
    private HashMap<String, ArrayList<String>> commentsByRecipeID;
    private HashMap<Integer, HashSet<String>> recipesByRating;
    private HashMap<String, HashSet<String>> recipesByABV;

    private Map<String, String> styles;

    /* Application manifest throws an error if I set this as private. I don't believe we want a
     * public constructor though, otherwise you could create another app instance.
     */
    public Brewsky(){

    }

    public static Brewsky getInstance(){
        if(instance == null){
            initInstance();
        }
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
        initInstance();
        setup();
        Log.i(getString(R.string.log_general), "Brewsky has been launched");
        loadRecipes();
    }

    private void setup(){
        recipeList = new ArrayList<Recipe>();
        recipesByID = new HashMap<>();
        recipesByRating = new HashMap<>();
        recipesByStyle = new HashMap<>();
        recipesByABV = new HashMap<>();
        for(int i = 0; i <= 5; i++){
            recipesByRating.put(i, new HashSet<String>());
        }
        commentsByRecipeID = new HashMap<>();
        styles = new HashMap<String, String>();
        addBeerStyles();
        Log.i(getString(R.string.log_general), "Finished setting up data structures in Brewsky");
    }

    private void addBeerStyles() {
        styles.put("american-pale-ale", "APA");
        styles.put("dark-beer", "Stout");
        styles.put("hefeweizen", "Hefeweizen");
        styles.put("irish-red", "Amber Ale");
        styles.put("test-recipe", "IPA");
    }

    public Brewsky getApplication(){
        return this.instance;
    }

    public void loadRecipes(){
        Intent recipeDownloadService = new Intent(Brewsky.this, RecipeDownloadService.class);
        startService(recipeDownloadService);
    }

    // Adds a single given recipe to the repository.
    public void addRecipe(Recipe recipe){
        recipe.setStyle(styles.get(recipe.getSlug()));
        String id = recipe.getId();
        if(!recipesByID.containsKey(id)){       // Check to see if the recipe already exists
            recipeList.add(recipe);
            recipesByID.put(id, recipe);
            recipesByRating.get(recipe.getRating()).add(id);
            String style = recipe.getStyle();
            if (recipesByStyle == null){
                recipesByStyle = new HashMap<>();
            }
            if (!recipesByStyle.containsKey(style)) { // If style doesn't exist, create it
                recipesByStyle.put(style, new HashSet<String>());
            }
            recipesByStyle.get(style).add(id);

            String abv = String.format("%.1f", recipe.getABV());
            if(!recipesByABV.containsKey(abv)){
                recipesByABV.put(abv, new HashSet<String>());
            }
            recipesByABV.get(abv).add(id);
        }
    }

    // Called by Recipes, it changes the rating in the map
    public void updateRating(String id, int oldRating, int newRating){
        if(recipesByRating == null){
            recipesByRating = new HashMap<>();
        }
        // Remove old rating
        if(recipesByRating.get(oldRating) != null){
            if(recipesByRating.get(oldRating).contains(id)){
                recipesByRating.get(oldRating).remove(id);
            }
        }
        // New rating
        if (recipesByRating.get(newRating) == null){
            recipesByRating.put(newRating, new HashSet<String>());
        }
        recipesByRating.get(newRating).add(id);
    }

    // Adds an ArrayList of recipes.
    public void addRecipes(ArrayList<Recipe> recipes){
        for (Recipe each: recipes){
            addRecipe(each);
        }
    }

    // Return the entire list of recipes.
    public ArrayList<Recipe> getRecipes(){
        return new ArrayList<Recipe>();
    }

    public Set<String> getRecipeIDs(){
        return recipesByID.keySet();
    }

    // Return a recipe with the give ID
    public Recipe getRecipeByID(String id){
        return (recipesByID.containsKey(id)) ? recipesByID.get(id) : null;
    }

    // Returns a HashSet of recipe IDs with the given ABV
    public HashSet<String> getRecipesByABV(double abv){
        String key = String.format("%.1f", abv);
        return (recipesByABV.containsKey(key)) ? recipesByABV.get(key) : new HashSet<String>();
    }

    // Returns a Hashset of recipe IDs within a given ABV range
    public HashSet<String> getRecipesInABVRange(double start, double end){
        HashSet<String> combined = new HashSet<>();
        while(start <= end){
            String current = String.format("%.1f", start);
            if(recipesByABV.containsKey(current)){
                HashSet<String> values = recipesByABV.get(current);
                Log.i("Current ABV", "" + start);
                for(String temp : values){
                    Log.i("ABV", "BEER: " + getRecipeByID(temp).getName() + " " + "ABV: " + getRecipeByID(temp).getABV());
                }
                combined.addAll(recipesByABV.get(current));
            }
            start += 0.1;
        }
        return combined;
    }

    // Returns a HashSet of recipe IDs with the given rating
    public HashSet<String> getRecipesByRating(int rating){
        Log.i("Rating", "" + recipesByRating.toString());
        return (recipesByRating.containsKey(rating)) ? recipesByRating.get(rating) : new HashSet<String>();
    }

    // Returns a HashSet of recipe IDs with the given style
    public HashSet<String> getRecipesByStyle(String style){
        if(recipesByStyle == null){
            recipesByStyle = new HashMap<>();
        }
        if(recipesByStyle.containsKey(style)){
            return recipesByStyle.get(style);
        }
        return new HashSet<>();
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

    public void broadCastNewRecipes(){
        Intent intent = new Intent(getString(R.string.brewsky_broadcast));
        intent.setAction(getString(R.string.broadcast_new_recipes));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
