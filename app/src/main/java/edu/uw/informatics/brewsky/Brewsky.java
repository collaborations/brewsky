package edu.uw.informatics.brewsky;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ginoclement on 3/10/15.
 */
public class Brewsky
        extends Application
        implements RecipeRepository {

    private static Brewsky instance;
    private ArrayList<Recipe> recipesList;

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
        recipesList = new ArrayList<>();
        loadRecipes();
    }

    public Brewsky getApplication(){
        return this.instance;
    }

    public void loadRecipes(){
        Intent recipeDownloadService = new Intent(Brewsky.this, RecipeDownloadService.class);
        startService(recipeDownloadService);
    }

    /**
     * Methods to implement the RecipeRepository
     */

    /**
     * Adds a single given recipe to the repository.
     * @param recipe
     */
    public void addRecipe(Recipe recipe){
        recipesList.add(recipe);
    }

    /**
     * Adds an ArrayList of recipes.
     */
    public void addRecipes(ArrayList<Recipe> recipes){
        for(Recipe each : recipes){
            Log.i(getString(R.string.log_general), each.toString());
        }
        recipesList.addAll(recipes);
    }

    /**
     * Return the entire list of recipes.
     * @return
     */
    public ArrayList<Recipe> getRecipes(){
        return this.recipesList;
    }
}
