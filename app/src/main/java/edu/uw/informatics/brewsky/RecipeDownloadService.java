package edu.uw.informatics.brewsky;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.JsonReader;
import android.util.JsonToken;
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
    private Context context;

    public RecipeDownloadService(){
        super(TAG);
    }

    public void onHandleIntent(Intent intent){
        context = getApplicationContext();
        String url = getString(R.string.api_url);

        // Add any other parameters onto the URL
        Log.i(getString(R.string.log_implement), "API URL Building");

        json = null;

        //Doing this now for testing
        url += "/v1/public/recipes?detail=false";

        // Check internet availability
        Log.i(getString(R.string.log_implement), "BAD RESPONSE CODE");
        Log.i(getString(R.string.log_implement), "INTERNET CONNECTIVITY");

        try {
            URL downloadURL = new URL(url);
            InputStreamReader inputStreamReader = new InputStreamReader(downloadURL.openStream());
            json = new JsonReader(inputStreamReader);
            processRecipes();
//            Log.i(getString(R.string.log_general), json.toString());
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.onHandleIntent(): " + e.toString());
        }

    }

    private void processRecipes(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            //Array of recipes
            json.beginArray();
            while(json.hasNext()) {
                // While there are recipes
                json.beginObject();
                recipes.add(parseRecipe());
                json.endObject();
            }
            //Close array of recipes
            json.endArray();
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.processRecipes(): " + e.toString());
        }
        Brewsky brewsky = (Brewsky) getApplication();
        brewsky.addRecipes(recipes);
    }

    private Recipe parseRecipe(){
        Map<String, String> data = new HashMap<>();
        Brewer brewer = null;

        // Fermentables, spices, and yeast ArrayLists, now on referred to as F/S/Y
        ArrayList<Fermentable> fermentables = new ArrayList<>();
        ArrayList<Spice> spices = new ArrayList<>();
        ArrayList<Yeast> yeast = new ArrayList<>();

        try {
            while(json.hasNext()) {
                String name = json.nextName();
                JsonToken peek = json.peek();
                switch (json.peek()) {
                    case BEGIN_ARRAY:
                        // This should never happen
                        break;
                    case BEGIN_OBJECT:
                        if(name.equals("user")){
                            brewer = createBrewer();
                        } else if(name.equals("data")){
                            // Inside data object
                            json.beginObject();
                            while(json.hasNext()){
                                name = json.nextName();
                                peek = json.peek();
                                switch (peek){
                                    case BEGIN_ARRAY:
                                        // Inside F/S/Y array
                                        String type = name;
                                        json.beginArray();
                                        while(json.hasNext()){
                                            // In F/S/Y object
                                            Map<String, String> temp = new HashMap<>();
                                            json.beginObject();
                                            while(json.hasNext()){
                                                name = json.nextName();
                                                String value = null;
                                                switch(json.peek()){
                                                    case NUMBER:
                                                        value = Double.toString(json.nextDouble());
                                                        break;
                                                    case STRING:
                                                        value = json.nextString();
                                                        break;
                                                    case BOOLEAN:
                                                        value = Boolean.toString(json.nextBoolean());
                                                        break;
                                                }
                                                temp.put(name, value);
                                            }
                                            json.endObject();
                                            switch (type){
                                                case "fermentables":
                                                    fermentables.add(new Fermentable(temp));
                                                    break;
                                                case "spices":
                                                    spices.add(new Spice(temp));
                                                    break;
                                                case "yeast":
                                                    yeast.add(new Yeast(temp));
                                                    break;
                                            }
                                        }
                                        // Leaving F/S/Y array
                                        json.endArray();
                                        break;
                                    case BOOLEAN:
                                        data.put(name, Boolean.toString(json.nextBoolean()));
                                        break;
                                    case STRING:
                                        data.put(name, json.nextString());
                                        break;
                                    case NUMBER:
                                        data.put(name, Double.toString(json.nextDouble()));
                                        break;
                                    case NULL:
                                        data.put(name, "NULL");
                                        json.nextNull();
                                        break;
                                }
                            }
                            // Exiting data object
                            json.endObject();
                        }
                        break;
                    case BOOLEAN:
                        data.put(name, Boolean.toString(json.nextBoolean()));
                        break;
                    case NULL:
                        data.put(name, "NULL");
                        json.nextNull();
                        break;
                    case NUMBER:
                        // Should never happen
                        break;
                    case STRING:
                        data.put(name, json.nextString());
                        break;
                    default:
                        Log.wtf(getString(R.string.log_general), "Skipping value: " + name + " : " + peek.toString());
                        json.skipValue();
                        break;
                }
            }
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in parseObject: " + e.getMessage());
        }
        Recipe recipe = new Recipe(data, context);
        recipe.setBrewer(brewer);
        recipe.setFermentables(fermentables);
        recipe.setSpices(spices);
        recipe.setYeast(yeast);
        return recipe;
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
        } catch (Exception e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.createBrewer(): " + e.toString());
            Log.wtf(getString(R.string.log_wtf), e.getMessage());
        }
        return new Brewer(data);
    }



}
