package edu.uw.informatics.brewsky;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */
public class RecipeDownloadService extends IntentService {
    public static final String TAG = IntentService.class.getSimpleName();
    private Context context;

    public RecipeDownloadService(){
        super(TAG);
    }

    public void onHandleIntent(Intent intent){
        context = getApplicationContext();
        String api = getString(R.string.api_url);

        // Add any other parameters onto the URL
        Log.i(getString(R.string.log_implement), "API URL Building");
        // Check internet availability
        Log.i(getString(R.string.log_implement), "BAD RESPONSE CODE");
        Log.i(getString(R.string.log_implement), "INTERNET CONNECTIVITY");

        //Doing this now for testing
        api += "/v1/public/recipes?detail=true";

//        while ((inputLine = input.readLine()) != null) {
//            System.out.println("I got a message from a client: " + inputLine);
//        }


        try {
            URL url = new URL(api);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = null;
            try {

                Log.i("Test", "" + urlConnection.getResponseCode());
                inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String input = "";
                String text = "";
                while((input = bufferedReader.readLine()) != null){
                    text += input;
                }
                Log.i("Temp", text);
                try {
                    JSONObject json = new JSONObject(text);
                    
                    Log.i("temp", json.get("name").toString());
                } catch (JSONException e) {

                }


            } finally {
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e){
            Log.wtf(getString(R.string.log_wtf), "RecipeDownloadService: " + e.getMessage());
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "RecipeDownloadService: " + e.getMessage());
        } finally {

        }

    }




}
