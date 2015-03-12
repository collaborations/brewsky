package edu.uw.informatics.brewsky;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Filter;


public class FilterActivity
        extends ActionBarActivity
            implements FilterFragment.OnFragmentInteractionListener {

    private Brewsky app;
    private FragmentManager fragmentManager;
    private Fragment current;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        app = (Brewsky) getApplication();
        fragmentManager = getFragmentManager();
        id = R.id.fragment_filter_list_container;

        FragmentTransaction t = fragmentManager.beginTransaction();
        current = FilterFragment.newInstance(new ArrayList<String>(app.getRecipeIDs()), "Hello World");
        t.add(id, current);
        t.commit();

        Button submit = (Button) findViewById(R.id.filter_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter();
            }
        });

    }

    private void setFilter(){
        String style = String.valueOf(((Spinner) findViewById(R.id.filter_style)).getSelectedItem());
        String abv = String.valueOf(((Spinner) findViewById(R.id.filter_abv)).getSelectedItem());
        String rating = String.valueOf(((Spinner) findViewById(R.id.filter_rating)).getSelectedItem());

        Toast.makeText(FilterActivity.this, style + " " + abv + " " + rating, Toast.LENGTH_SHORT).show();

        ArrayList<String> filtered = filterRecipes(style, abv, rating);

        FragmentTransaction t = fragmentManager.beginTransaction();
        FilterFragment next = FilterFragment.newInstance(filtered, "Hello World");
        t.replace(id, next);
        t.commit();

    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Returns an ArrayList of all the IDs that match the filter
    private ArrayList<String> filterRecipes(String style, String abv, String rating) {
        ArrayList<String> filtered = new ArrayList<>(app.getRecipeIDs());
        if(style.equals("All") && abv.equals("All") && rating.equals("All")){
            Log.i(getString(R.string.log_general), "All filters off");
        } else {
            if (!abv.equals("All")) {
                String[] range = abv.split("-");
                String end = range[1].substring(0, range[1].length() - 1);
                filtered.retainAll(app.getRecipesInABVRange(Double.parseDouble(range[0]), Double.parseDouble(end)));
            }
            if (!style.equals("All")) {
                filtered.retainAll(app.getRecipesByStyle(style));
            }
            if (!rating.equals("All")) {
                filtered.retainAll(app.getRecipesByRating(Integer.parseInt(rating.substring(0, 1))));
            }
        }
        return filtered;
    }

    public void onFragmentListItemClick(String id){
        Intent showDetails = new Intent(FilterActivity.this, RecipeDetailActivity.class);
        showDetails.putExtra("recipe", id);
        startActivity(showDetails);
    }
}
