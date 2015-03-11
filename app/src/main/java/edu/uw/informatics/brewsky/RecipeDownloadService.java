package edu.uw.informatics.brewsky;

import android.app.IntentService;
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
            processRecipes();
//            Log.i(getString(R.string.log_general), json.toString());
        } catch (Exception e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.onHandleIntent(): " + e.toString());
        }

    }

    private void processRecipes(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            json.beginArray();                                      //Array of Recipes
            while(json.hasNext()) {
                recipes.add(createRecipe());
            }
            json.endArray();                                        //Close array of Recipes
        } catch (Exception e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.processRecipes(): " + e.toString());
        }
        Brewsky brewsky = (Brewsky) getApplication();
        brewsky.addRecipes(recipes);
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
            Log.wtf(getString(R.string.log_wtf), e.getMessage());
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
                JsonToken peek = json.peek();
                switch (peek){
                    case BEGIN_ARRAY:
                        if(name.equals("fermentables")){
                            fermentables = createFermentables();
                        } else if(name.equals("spices")){
                            spices = createSpices();
                        } else if(name.equals("yeast")) {
                            yeast = createYeast();
                        } else {
                            Log.wtf(getString(R.string.log_general), "Unknown array: " + name + "-->" + peek.toString());
                        }
                        break;
                    case BEGIN_OBJECT:
                        if(name.equals("user")) {
                            brewer = createBrewer();
                        } else if (name.equals("data")){
                            
                        }
                        break;
                    case BOOLEAN:
                        String text = (json.nextBoolean()) ? "true" : "false";
                        data.put(name, text);
                        Log.i(getString(R.string.log_implement), "Detect Booleans");
                        Log.i(getString(R.string.log_general), name + " : " + peek.toString());
                        break;
                    case NULL:
                        data.put(name, "NULL");
                        Log.i(getString(R.string.log_implement), "Detect Null");
                        json.nextNull();
                        break;
                    case NUMBER:
                        Log.i(getString(R.string.log_implement), "Number Handling");
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
            json.endObject();
        } catch (Exception e){
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.createRecipe(): " + e.toString());
            Log.wtf(getString(R.string.log_wtf), e.getMessage());
        }


        Recipe recipe = new Recipe(data);
        recipe.setBrewer(brewer);
        recipe.setFermentables(fermentables);
        recipe.setSpices(spices);
        recipe.setYeast(yeast);
        return recipe;
//        try {
//            json.beginObject();
//            while(json.hasNext()) {
////                Log.i(getString(R.string.log_general), json.toString());
//                String name = json.nextName();
//                JsonToken peek = json.peek();
//                Log.i(getString(R.string.log_general), name + " : " + peek.toString());
//                if(peek.equals(JsonToken.BEGIN_ARRAY)){
//                    if(name.equals("fermentables")){
//                        fermentables = createFermentables();
//                    } else if(name.equals("spices")){
//                        spices = createSpices();
//                    } else if(name.equals("yeast")) {
//                        yeast = createYeast();
//                    } else {
//                        Log.i(getString(R.string.log_general), name + " : " + peek.toString());
//                    }
//                } else if(peek.equals(JsonToken.BEGIN_OBJECT)){
//                    if(name.equals("user")){
//                        brewer = createBrewer();
//                    } else if(name.equals())) {
//
//                    } else {
//                        Log.i(getString(R.string.log_general), name + " : " + peek.toString());
//                    }
//                } else if(peek.equals(JsonToken.BOOLEAN)){
//                    String text = (json.nextBoolean()) ? "true" : "false";
//                    data.put(name, text);
//                    Log.i(getString(R.string.log_general), name + " : " + peek.toString());
//                } else if(peek.equals(JsonToken.NUMBER)){
//
//                    Log.i(getString(R.string.log_general), name + " : " + peek.toString());
//                } else if(peek.equals(JsonToken.STRING)){
//                    String value = json.nextString();
//                    data.put(name, value);
//                } else if(peek.equals(JsonToken.NULL)) {
//                    json.nextNull();
//                    data.put(name, "NULL");
//                } else {
//                    Log.i(getString(R.string.log_general), name + " : " + peek.toString());
//                }
//            }
//            json.endObject();
//        } catch (Exception e){
//            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.createRecipe(): " + e.toString());
//            Log.wtf(getString(R.string.log_wtf), e.getMessage());
//        }
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
        } catch (Exception e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.createSpices(): " + e.toString());
            Log.wtf(getString(R.string.log_wtf), e.getMessage());
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
        } catch (Exception e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.createYeast(): " + e.toString());
            Log.wtf(getString(R.string.log_wtf), e.getMessage());
        }
        return yeast;
    }

}
