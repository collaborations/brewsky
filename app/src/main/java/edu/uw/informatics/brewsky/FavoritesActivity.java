package edu.uw.informatics.brewsky;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class FavoritesActivity extends ActionBarActivity {
    private Brewsky app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Load the recipes
        // Change this to get recipes that are currently downloaded
        app = Brewsky.getInstance();

        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(this, R.layout.recipe_list_row, new ArrayList<>(app.getRecipeIDs()));
    }

    private ArrayList<Recipe> getFavorites() {
        // TODO: get favorites

        return null;
    }
}
