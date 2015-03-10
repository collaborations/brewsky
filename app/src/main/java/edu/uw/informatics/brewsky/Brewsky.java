package edu.uw.informatics.brewsky;

import android.app.Application;
import android.util.Log;

/**
 * Created by ginoclement on 3/10/15.
 */
public class Brewsky extends Application {
    private static Brewsky instance;

    private Brewsky(){
        instance = (Brewsky)getApplication();
    }

    public static Brewsky getInstance(){
        if (instance == null){
            instance = new Brewsky();
        }
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(getString(R.string.log_general), "Brewsky has been launched");
    }

    public Brewsky getApplication(){
        return this.instance;
    }

}
