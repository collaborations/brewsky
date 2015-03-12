package edu.uw.informatics.brewsky;

import java.util.HashSet;
import java.util.ArrayList;

/**
 * Created by ginoclement on 3/10/15.
 *
 * Uncommenting these items as they are implemented.
 */
public interface RecipeRepository {
    public void addRecipe(Recipe recipe);
    public void addRecipes(ArrayList<Recipe> recipes);
    public ArrayList<Recipe> getRecipes();
    public int getRecipeCount();
    public void loadRecipes();
//    public ArrayList<Recipe> getSortedRecipes(String sortBy, boolean ascending);
//    public ArrayList<Recipe> getFilteredRecipes(String filterBy);
//    public ArrayList<Recipe> getFilteredRecipes(ArrayList<String> filterBy);
//    public ArrayList<Recipe> getRecipesByUser(String userID);
//    //Internally just calls getFilteredRecipes
//    public ArrayList<Recipe> getRecipesByStyle(String style);
//    public Recipe getRecipeByID(String id);
}
