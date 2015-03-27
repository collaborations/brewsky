package edu.uw.informatics.brewsky;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import java.util.logging.Filter;


public class FilterActivity extends ActionBarActivity {
    private Brewsky app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (Brewsky) getApplication();
        setContentView(R.layout.activity_filter);
        Button submit = (Button) findViewById(R.id.submit);
        addListener(submit);
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

    public void addListener(Button submit) {
        final Spinner typeSpinner = (Spinner) findViewById(R.id.type);
        typeSpinner.setSelection(4);
        typeSpinner.setSelection(2);
        typeSpinner.setSelection(0);
        final Spinner abvSpinner = (Spinner) findViewById(R.id.abv);
        final Spinner ratingSpinner = (Spinner) findViewById(R.id.rating);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = String.valueOf(typeSpinner.getSelectedItem());
                String abv = String.valueOf(abvSpinner.getSelectedItem());
                String rating = String.valueOf(ratingSpinner.getSelectedItem());
                Toast.makeText(FilterActivity.this, type + " " + abv + " " + rating, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(FilterActivity.this, RecipeListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                // passes these as extras to RecipeData List and reloads it
                app.setType(type);
                app.setAbv(abv);
                app.setRating(rating);
                startActivity(i);
            }
        });
    }
}
