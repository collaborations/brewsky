package edu.uw.informatics.brewsky;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class RecipeDetailActivity extends ActionBarActivity {
    private Brewsky app;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        app = (Brewsky) getApplication();

        Intent launchedMe = getIntent();
        String recipeID = launchedMe.getStringExtra("recipe");
        recipe = app.getRecipeByID(recipeID);



        Button btn_instructions = (Button) findViewById(R.id.btn_instruction_start);
        btn_instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instructions = new Intent(RecipeDetailActivity.this, RecipeInstructionsActivity.class);
                instructions.putExtra("recipe", recipe.getId());
                startActivity(instructions);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.favorites_menu_button) {
            Intent favoritesIntent = new Intent(RecipeDetailActivity.this, FavoritesActivity.class);
            startActivity(favoritesIntent);
        } else if(id == R.id.settings_menu_button){
            Intent settingsIntent = new Intent(RecipeDetailActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
