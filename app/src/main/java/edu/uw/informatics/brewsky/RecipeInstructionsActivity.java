package edu.uw.informatics.brewsky;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
//import java.util.Queue;

/* Recipe Instructions
 * http://api.malt.io/#recipes-recipe-get
 */

public class RecipeInstructionsActivity extends ActionBarActivity {
    private Brewsky app;
    private Recipe recipe;
    private FragmentManager fragmentManager;
//    private Queue<TaskFragment> tasks;
    private ArrayList<TaskFragment> tasks;
    private TaskFragment current;
    private int taskContainer;

    Map<Double, String> instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_instructions);
        app = (Brewsky) getApplication();
        Intent parent = getIntent();
        recipe = app.getRecipeByID(parent.getStringExtra("recipe"));
        instructions = recipe.getTimeline();
        fragmentManager = getFragmentManager();
        taskContainer = R.id.tasks_container;




        createTasks();
        showTasks();

    }


    private void createTasks(){
        tasks = new ArrayList<>();
        for(double key : instructions.keySet()){
            Log.i(getString(R.string.log_general), "" + key + " -> " + instructions.get(key));
            TaskFragment fragment = TaskFragment.newInstance(key, instructions.get(key));
            tasks.add(fragment);
        }
    }

    private void nextTask(){
        Log.i(getString(R.string.log_implement), "NEXT TASK");
    }

    // Builds all of the tasks to be shown on screen
    private void showTasks(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        LinearLayout parentContainer = (LinearLayout) findViewById(taskContainer);
        ViewGroup.LayoutParams parentParams = parentContainer.getLayoutParams();
        int w = parentParams.width;
        TaskFragment previous = null;
        int i = 1;
        for(TaskFragment each : tasks) {
            RelativeLayout container = new RelativeLayout(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            if(previous != null) {
                params.addRule(RelativeLayout.BELOW, previous.getId());
            }
            container.setLayoutParams(params);
            container.setId(i);
            parentContainer.addView(container);
            transaction.add(container.getId(), each, "Step" + i);
            previous = each;
            i++;
        }
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_instructions, menu);
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
