package edu.uw.informatics.brewsky;

import android.app.Application;
import android.util.Log;

/**
 * Created by ginoclement on 3/10/15.
 */
public class Brewsky extends Application {
    private static Brewsky instance;

    public Brewsky(){
        instance = (Brewsky)getApplication();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("Brewsky", "Brewsky has been launched");
    }

    public Brewsky getApplication(){
        return this.instance;
    }

}
