package edu.uw.informatics.brewsky;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Map;

/* Recipe Instructions
 * http://api.malt.io/#recipes-recipe-get
 */

public class RecipeInstructionsActivity extends ActionBarActivity {
    Intent parent;
    Recipe recipe;
    Map<Double, String> instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_instructions);
        Intent parent = getIntent();
        recipe = (Recipe) parent.getSerializableExtra("recipe");
        instructions = recipe.getTimeline();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.settings_menu_button){
            Intent settingsIntent = new Intent(RecipeInstructionsActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        } else if (id == R.id.favorites_menu_button) {
            Intent favoritesIntent = new Intent(RecipeInstructionsActivity.this, FavoritesActivity.class);
            startActivity(favoritesIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
