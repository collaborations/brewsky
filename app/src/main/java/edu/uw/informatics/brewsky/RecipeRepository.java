package edu.uw.informatics.brewsky;

import java.util.ArrayList;

import edu.uw.informatics.brewsky.models.RecipeData;

/**
 * Created by ginoclement on 3/10/15.
 *
 * Uncommenting these items as they are implemented.
 */
public interface RecipeRepository {
    public void addRecipe(RecipeData recipe);
    public void addRecipes(ArrayList<RecipeData> recipes);
    public ArrayList<RecipeData> getRecipes();
    public int getRecipeCount();
    public void loadRecipes();
//    public ArrayList<RecipeData> getSortedRecipes(String sortBy, boolean ascending);
//    public ArrayList<RecipeData> getFilteredRecipes(String filterBy);
//    public ArrayList<RecipeData> getFilteredRecipes(ArrayList<String> filterBy);
//    public ArrayList<RecipeData> getRecipesByUser(String userID);
//    //Internally just calls getFilteredRecipes
//    public ArrayList<RecipeData> getRecipesByStyle(String style);
//    public RecipeData getRecipeByID(String id);
}
