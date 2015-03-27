package edu.uw.informatics.brewsky.utils;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import edu.uw.informatics.brewsky.R;
import edu.uw.informatics.brewsky.models.Recipe;

/**
 * Created by ginoclement on 3/10/15.
 */
public class RecipeDownloadService extends IntentService {
    public static final String TAG = IntentService.class.getSimpleName();
    private Context context;
    private Gson gson;

    public RecipeDownloadService(){
        super(TAG);
    }

    public void onHandleIntent(Intent intent){
        context = getApplicationContext();
        gson = new Gson();

        String url = getString(R.string.api_url);
        url += "/v1/public/recipes?detail=true";

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        try {
            URL downloadURL = new URL(url);
            InputStreamReader inputStreamReader = new InputStreamReader(downloadURL.openStream());
            Recipe r = gson.fromJson(inputStreamReader, Recipe.class);
        } catch (IOException e) {
            Log.wtf(getString(R.string.log_wtf), "Exception in RecipeDownloadService.onHandleIntent(): " + e.toString());
        }

    }
}
