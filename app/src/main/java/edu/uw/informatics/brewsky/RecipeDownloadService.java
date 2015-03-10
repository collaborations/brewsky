package edu.uw.informatics.brewsky;

import android.app.IntentService;
import android.content.Intent;
import android.util.JsonReader;
import android.util.Log;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */
public class RecipeDownloadService extends IntentService {
    public static final String TAG = IntentService.class.getSimpleName();
    private JsonReader json;


    public RecipeDownloadService(){
        super(TAG);
    }

    public void onHandleIntent(Intent intent){
        String url = getString(R.string.api_url);

        // Add any other parameters onto the URL
        Log.i(getString(R.string.log_implement), "API URL Building");

        json = null;

        //Doing this now for testing
        url += "/v1/public/recipes?detail=true";

        // Check internet availability
        Log.i(getString(R.string.log_implement), "Internet Availability?");

        try {
            URL downloadURL = new URL(url);
            InputStreamReader inputStreamReader = new InputStreamReader(downloadURL.openStream());
            json = new JsonReader(inputStreamReader);

            Log.i(getString(R.string.log_general), json.toString());
        } catch (Exception e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService: " + e.toString());
        }

    }

    private void processRecipe(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            json.beginArray();                                      //Array of Recipes
            while(json.hasNext()) {
                recipes.add(createRecipe());
            }
            json.endArray();                                        //Close array of Recipes
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService: " + e.toString());
        }
    }

    private Brewer createBrewer(){
        Map<String, String> data = new HashMap<>();
        try {
          json.beginObject();
          while(json.hasNext()){
            String name = json.nextName();
            String value = json.nextString();
            data.put(name, value);
          }
          json.endObject();
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService: " + e.toString());
        }
        return new Brewer(data);
    }

    private ArrayList<Fermentable> createFermentables(){
        ArrayList<Fermentable> fermentables = new ArrayList<>();
        try {
            json.beginArray();
            while(json.hasNext()) {                                                 //Loop over array of fermentables
                json.beginObject();
                Map<String, String> fermentableProperties = new HashMap<String, String>();
                while (json.hasNext()) {                                            //Loop over fermentable properties
                    String name = json.nextName();
                    String value = json.nextString();
                    fermentableProperties.put(name, value);
                }
                json.endObject();
                fermentables.add(new Fermentable(fermentableProperties));
            }
            json.endArray();
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService: " + e.toString());
        }
        return fermentables;
    }

    private Recipe createRecipe(){
        Map<String, String> data = new HashMap<>();
        Brewer brewer = null;
        ArrayList<Fermentable> fermentables = null;
        ArrayList<Spice> spices = null;
        ArrayList<Yeast> yeast = null;

        try {
            json.beginObject();
            while(json.hasNext()) {
                String name = json.nextName();
                if(name.equals("user")){
                    brewer = createBrewer();
                } else if(name.equals("fermentables")){
                    fermentables = createFermentables();
                } else if(name.equals("spices")){
                    spices = createSpices();
                } else if(name.equals("yeast")){
                    yeast = createYeast();
                } else {
                    String value = json.nextString();
                    data.put(name, value);
                }
            }
            json.endObject();
        } catch (IOException e){
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService: " + e.toString());
        }
        Recipe recipe = new Recipe(data);
        recipe.setBrewer(brewer);
        recipe.setFermentables(fermentables);
        recipe.setSpices(spices);
        recipe.setYeast(yeast);
        return recipe;
    }

    private ArrayList<Spice> createSpices(){
        ArrayList<Spice> spices = new ArrayList<>();
        try {
            json.beginArray();
            while(json.hasNext()) {                                                 //Loop over array of spices
                json.beginObject();
                Map<String, String> spiceProperties = new HashMap<String, String>();
                while (json.hasNext()) {                                            //Loop over spice properties
                    String name = json.nextName();
                    String value = json.nextString();
                    spiceProperties.put(name, value);
                }
                json.endObject();
                spices.add(new Spice(spiceProperties));
            }
            json.endArray();
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService: " + e.toString());
        }
        return spices;
    }

    /**
     * Assuming the next item in the JsonReader is a Yeast object, it returns an ArrayList of
     * @return
     */
    private ArrayList<Yeast> createYeast(){
        ArrayList<Yeast> yeast = new ArrayList<>();
        try {
            json.beginArray();
            while(json.hasNext()) {                                                 //Loop over array of spices
                json.beginObject();
                Map<String, String> yeastProperties = new HashMap<String, String>();
                while (json.hasNext()) {                                            //Loop over spice properties
                    String name = json.nextName();
                    String value = json.nextString();
                    yeastProperties.put(name, value);
                }
                json.endObject();
                yeast.add(new Yeast(yeastProperties));
            }
            json.endArray();
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService: " + e.toString());
        }
        return yeast;
    }

}
