package edu.uw.informatics.brewsky;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.ArrayList;

import edu.uw.informatics.brewsky.models.RecipeData;


public class FavoritesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Load the recipes
        // Change this to get recipes that are currently downloaded
        ArrayList<RecipeData> data = getFavorites();

        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(this, R.layout.recipe_list_row, data);
    }

    private ArrayList<RecipeData> getFavorites() {
        // TODO: get favorites

        return null;
    }
}
