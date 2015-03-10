package edu.uw.informatics.brewsky;

import android.app.Application;
import android.util.Log;

/**
 * Created by ginoclement on 3/10/15.
 */
public class Brewsky extends Application {
    private static Brewsky instance;

    /* Application manifest throws an error if I set this as private. I don't believe we want a
     * public constructor though, otherwise you could create another app instance.
     */
    public Brewsky(){

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
