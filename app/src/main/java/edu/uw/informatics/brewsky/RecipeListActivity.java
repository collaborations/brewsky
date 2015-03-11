package edu.uw.informatics.brewsky;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

/* List of available recipes
 * http://api.malt.io/#anonymous-public-api-recipe-collection
 */

public class RecipeListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // Load the recipes
        // Change this to get recipes that are currently downloaded
        ArrayList<Recipe> data = null;

        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(this, R.layout.recipe_list_row, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle acxtion bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.favorites_button) {
            // TODO: launch favorites activity
            Intent favoritesIntent = new Intent(RecipeListActivity.this, FavoritesActivity.class);
            startActivity(favoritesIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
