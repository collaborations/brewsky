package edu.uw.informatics.brewsky;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Map;

/* Recipe Instructions
 * http://api.malt.io/#recipes-recipe-get
 */

public class RecipeInstructionsActivity
        extends ActionBarActivity
        implements TaskFragment.OnFragmentInteractionListener {

    private Brewsky app;
    private Recipe recipe;
    private FragmentManager fragmentManager;
    private ArrayList<TaskFragment> tasks;
    private int current;
    private int taskContainer;

    Map<Double, String> instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_instructions);
        app = (Brewsky) getApplication();
        Intent parent = getIntent();
        recipe = app.getRecipeByID(parent.getStringExtra("recipe"));
        fragmentManager = getFragmentManager();
        taskContainer = R.id.tasks_container;
        current = 0;
        createTasks();
        showTasks();
    }


    private void createTasks(){
        instructions = recipe.getTimeline();
        tasks = new ArrayList<>();
        for(double key : instructions.keySet()){
            TaskFragment fragment = TaskFragment.newInstance(key, instructions.get(key));
            tasks.add(fragment);
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        createTasks();
        showTasks();
    }

    public void onFragmentSwipe(int id) {
        if(tasks.size() == 1){
            Log.i(getString(R.string.log_general), "Finished Instructions");
        } else if(current == (id - 1)) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            TaskFragment frag = tasks.get(current);
            ((LinearLayout) findViewById(taskContainer)).removeView(findViewById(id));
            transaction.remove(frag);
            transaction.commit();
//            if(current == tasks.size() - 1){
//                current++;
//            } else if(current < tasks.size() - 1){
            if(current < tasks.size() - 1){
                current++;
                tasks.get(current).setCurrent();
            }
        }
    }

    // Builds all of the tasks to be shown on screen
    private void showTasks(){
        LinearLayout parentContainer = (LinearLayout) findViewById(taskContainer);
        TaskFragment previous = null;

        int i = current + 1;
        int index = current;
        for(; index < tasks.size(); index++) {
            TaskFragment each = tasks.get(index);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            RelativeLayout container = new RelativeLayout(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            if(previous != null) {
                params.addRule(RelativeLayout.BELOW, previous.getId());
            }
            container.setLayoutParams(params);
            container.setId(i);
            container.setMinimumHeight(100);
            parentContainer.addView(container);
            transaction.add(container.getId(), each, "Step" + i);
            previous = each;
            i++;
            transaction.commit();
        }
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
            instructions = recipe.getTimeline();
            resetTasks();
        } else if (id == R.id.reset_tasks_button) {
            current = 0;
            resetTasks();
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetTasks(){
        LinearLayout container = (LinearLayout) findViewById(R.id.tasks_container);
        container.removeAllViews();
        createTasks();
        showTasks();
    }

    public int getCurrent(){
        return this.current;
    }
}
