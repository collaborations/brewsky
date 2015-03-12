package edu.uw.informatics.brewsky;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ginoclement on 3/10/15.
 */
public class RecipeDownloadService extends IntentService {
    public static final String TAG = IntentService.class.getSimpleName();
    private JsonReader json;
    private Context context;
    private String localPath;
    private int count;

    public RecipeDownloadService(){
        super(TAG);
    }

    public void onHandleIntent(Intent intent){
        count = 0;
        context = getApplicationContext();
        String url = getString(R.string.api_url);

        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        localPath = "recipes.json";

        // Add any other parameters onto the URL
        Log.i(getString(R.string.log_implement), "API URL Building");

        json = null;

        //Doing this now for testing
//        url += "/v1/public/recipes?detail=false";
        url += "/v1/public/recipes?detail=true";

        // Check internet availability
        Log.i(getString(R.string.log_implement), "BAD RESPONSE CODE");
        Log.i(getString(R.string.log_implement), "INTERNET CONNECTIVITY");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        dm.enqueue(request);
        try {
            File file = new File(getFilesDir().toString() + "/recipes.json");
            if(!file.exists()){
                downloadFile(url, file);
            }
            Log.i(getString(R.string.log_general), "Reading file from local path");
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            json = new JsonReader(inputStreamReader);
            processRecipes();
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.onHandleIntent(): " + e.toString());
        }

        Log.i(getString(R.string.log_general), "Finished: " + count + " recipes downloaded.");

        Intent broad = new Intent("RecipeDownloadService");
        broad.setAction("RECIPE_PARSING_FINISHED");
        LocalBroadcastManager.getInstance(this).sendBroadcast(broad);


    }

    private void downloadFile(String url, File file){
        Log.i(getString(R.string.log_general), "Downloading file.");
        try {
            BufferedReader input = null;
            PrintWriter output = null;
            try {
                URL download = new URL(url);
                input = new BufferedReader(new InputStreamReader(download.openStream()));
                output = new PrintWriter(new FileOutputStream(file));

                String line;
                while ((line = input.readLine()) != null) {
                    output.println(line);
                    Log.i(getString(R.string.log_general), "Reading string: " + line);
                }
            } finally {
                if (input != null){
                    input.close();
                }
                if (output != null){
                    output.close();
                }
            }
        } catch (MalformedURLException e) {
            Log.i(getString(R.string.log_general), "Malformed URL:" + e.getMessage());
        } catch (IOException e) {
            Log.i(getString(R.string.log_general), "IO Exception:" + e.getMessage());
        }
    }

    private void processRecipes(){
        Log.i(getString(R.string.log_general), "Beginning to process file");
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            //Array of recipes
            json.beginArray();
            while(json.hasNext()) {
                // While there are recipes
                json.beginObject();
                recipes.add(parseRecipe());
                count++;
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
        ArrayList<Integer> color = new ArrayList<>();
        Map<Double, String> timeline = new TreeMap<>();
        Map<Double, String> timelineImperial = new TreeMap<>();


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
                                            if(name.equals("colorRgb")){
                                                while(json.hasNext()){
                                                    color.add(json.nextInt());
                                                }
                                            } else if(name.equals("timeline") || name.equals("timelineImperial")){
                                                boolean imperial = name.equals("timelineImperial");
                                                while(json.hasNext()){
                                                    json.beginArray();
                                                    double time = json.nextDouble();
                                                    String instructions = json.nextString();
                                                    if(imperial){
                                                        timelineImperial.put(time, instructions);
                                                    } else {
                                                        timeline.put(time, instructions);
                                                    }
                                                    json.endArray();
                                                }
                                            } else {
                                                Map<String, String> temp = new HashMap<>();

                                                json.beginObject();
                                                while (json.hasNext()) {
                                                    name = json.nextName();
                                                    String value = null;
                                                    switch (json.peek()) {
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
                                                switch (type) {
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
        recipe.setColor(color);
        recipe.setTimeline(timeline, timelineImperial);
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
