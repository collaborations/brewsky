package edu.uw.informatics.brewsky;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
        Brewsky app = (Brewsky) getApplication();
        ArrayList<Recipe> data = app.getRecipes();

        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(this, R.layout.recipe_list_row, data);
        final ListView recipeList = (ListView) findViewById(R.id.recipe_list);
        recipeList.setAdapter(recipeListAdapter);
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent recipeDetails = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
                Recipe clickedRecipe = (Recipe) recipeList.getItemAtPosition(position);
                recipeDetails.putExtra("recipe", clickedRecipe);
                startActivity(recipeDetails);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle acxtion bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.settings_menu_button){
            Intent settingsIntent = new Intent(RecipeListActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        } else if (id == R.id.favorites_menu_button) {
            Intent favoritesIntent = new Intent(RecipeListActivity.this, FavoritesActivity.class);
            startActivity(favoritesIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
