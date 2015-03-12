package edu.uw.informatics.brewsky;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class RecipeDetailActivity extends ActionBarActivity {
    private Brewsky app;
    private RatingBar ratingBar;
    private ArrayAdapter commentAdapter;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        app = (Brewsky) getApplication();

        Intent launchedMe = getIntent();
        final String recipeID = launchedMe.getStringExtra("recipe");
        recipe = app.getRecipeByID(recipeID);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Set up view
        TextView name = (TextView) findViewById(R.id.recipe_name);
        TextView abv = (TextView) findViewById(R.id.recipe_abv);
        ImageView type = (ImageView) findViewById(R.id.recipe_type);
        TextView spiceList = (TextView) findViewById(R.id.spices_list);
        TextView yeastList = (TextView) findViewById(R.id.yeast_list);
        TextView fermentableList = (TextView) findViewById(R.id.fermentables_list);

        // add the data to the view
        name.setText(recipe.getName());
        abv.setText(Double.toString(recipe.getABV()) + "% ABV");
        type.setImageResource(R.drawable.amber_ale);

        // create spices list
        String spices = "";
        ArrayList<Spice> recipeSpices = recipe.getSpices();
        for(int i = 0; i < recipeSpices.size(); i++) {
            Spice spice = recipeSpices.get(i);
            spices += spice.getName();
            if( i < recipeSpices.size() - 1) {
                spices += ", ";
            }
        }

        // create yeast list
        String yeasts = "";
        ArrayList<Yeast> recipeYeast = recipe.getYeast();
        for(int i = 0; i < recipeYeast.size(); i++) {
            Yeast yeast = recipeYeast.get(i);
            yeasts += yeast.getName();
            if( i < recipeYeast.size() - 1) {
                yeasts += ", ";
            }
        }

        // create fermentable list
        String fermentables = "";
        ArrayList<Fermentable> recipeFermentables = recipe.getFermentables();
        for(int i = 0; i < recipeFermentables.size(); i++) {
            Fermentable fermentable = recipeFermentables.get(i);
            fermentables += fermentable.getName();
            if( i < recipeFermentables.size() - 1) {
                fermentables += ", ";
            }
        }

        spiceList.setText(spices);
        yeastList.setText(yeasts);
        fermentableList.setText(fermentables);

        Button btn_instructions = (Button) findViewById(R.id.btn_instruction_start);
        btn_instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instructions = new Intent(RecipeDetailActivity.this, RecipeInstructionsActivity.class);
                instructions.putExtra("recipe", recipe.getId());
                startActivity(instructions);
            }
        });

        addListenerOnRatingBar();
        final ListView commentView = (ListView) findViewById(R.id.commentBox);
        ArrayList<String> comments = app.getCommentsByID(recipeID);
        if(comments != null) {
            // If there are comments to start populate them on creation
            commentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
            Log.d("comments", "ListView is " + commentView.toString());
            Log.d("comments", "Adapter is " + commentAdapter);
            commentView.setAdapter(commentAdapter);
            commentAdapter.notifyDataSetChanged();
        }
        Button commentSubmit = (Button) findViewById(R.id.commentSubmit);
        commentSubmit.setOnClickListener(new View.OnClickListener() {
            // When clicked add the comment to the list view
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.input_box);
                app.addComment(recipeID, text.getText().toString());
                Log.i("comments", app.getCommentsByID(recipeID).toString());
                ArrayList<String> newComments = app.getCommentsByID(recipeID);
                if(commentAdapter == null) {
                    // if the adapter was not set on creation set it now
                    commentAdapter = new ArrayAdapter<String>(RecipeDetailActivity.this, android.R.layout.simple_list_item_1, newComments);
                    commentView.setAdapter(commentAdapter);
                }
                commentAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        float rating = recipe.getRating();
        ratingBar.setRating(rating);
    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                recipe.setRating(Integer.parseInt(String.format("%.0f", rating)));
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
