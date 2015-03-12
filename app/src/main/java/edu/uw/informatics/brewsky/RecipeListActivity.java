package edu.uw.informatics.brewsky;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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
    private RecipeListAdapter adapter;
    private ArrayList<Recipe> data;
    private Brewsky app;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // Load the recipes
        app = (Brewsky) getApplication();
        data = app.getRecipes();
        Log.i(getString(R.string.log_general), "Number of recipes: " + data.size());
        adapter = new RecipeListAdapter(this, R.layout.recipe_list_row, data);
        final ListView recipeList = (ListView) findViewById(R.id.recipe_list);
        recipeList.setAdapter(adapter);
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent recipeDetails = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
                Recipe clickedRecipe = (Recipe) recipeList.getItemAtPosition(position);
                recipeDetails.putExtra("recipe", clickedRecipe.getId());
                startActivity(recipeDetails);
            }
        });

        // Register Receiver
        filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE); // Add more filters here that you want the receiver to listen to
        registerReceiver(broadcastReceiver, filter);


    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_list, menu);
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
            Intent settingsIntent = new Intent(RecipeListActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        } else if (id == R.id.favorites_menu_button) {
            Intent favoritesIntent = new Intent(RecipeListActivity.this, FavoritesActivity.class);
            startActivity(favoritesIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(getString(R.string.log_general), "Receiving");
            String action = intent.getAction();
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            if(downloadID != 0){
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadID);
                Cursor c = dm.query(query);
                if(c.moveToFirst()){
                    int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if(status == DownloadManager.STATUS_SUCCESSFUL){
                        Log.i(getString(R.string.log_general), "Finished Downloading Recipes");
                        data = app.getRecipes();
                        adapter.clear();
                        adapter.addAll(data);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        }
    };
}
