package edu.uw.informatics.brewsky;

import android.util.Log;

import java.util.Map;

/**
 * Created by ginoclement on 3/10/15.
 */

//{
//    "id": "5262c92c349e253115000001",
//    "name": "daniel-taylor",
//    "image": "https://plus.google.com/s2/photos/profile/117442582930902051362?sz=SIZE"
//}

public class Brewer {
    private String id;
    private String name;
    private String image;

    // This is in case there is no brewer for the Recipe
    public Brewer(){
        this.id = null;
        this.name = "Anonymous Brewer";
        this.image = null;
    }

    public Brewer(Map<String, String> data){
        if(data.keySet().size() == 3) {
            this.id = data.get("id");
            this.name = data.get("name");
            this.image = data.get("image");
        } else {
            Log.i("BrewskyWTF", "Empty user");
            this.id = "Fake ID";
            this.name = "Anonymouse Brewer";
            this.image = "Fake Image";
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }


}
