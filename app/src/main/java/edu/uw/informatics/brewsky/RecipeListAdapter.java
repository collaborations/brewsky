package edu.uw.informatics.brewsky;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ginoclement on 3/10/15.
 */
public class RecipeListAdapter extends ArrayAdapter<String> {
    private ArrayList<String> data;
    private Context context;
    private int layoutResourceId;
    private Brewsky app;

    static class RecipeListHolder {
        TextView name;
        TextView abv;
        ImageView type;
        RatingBar ratingBar;
    }

    public RecipeListAdapter(Context context, int resource, ArrayList<String> data) {
        super(context, resource, data);
        app = (Brewsky) context.getApplicationContext();
        this.context = context;
        this.data = data;
        this.layoutResourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View row = convertView;
        RecipeListHolder holder = null;
        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecipeListHolder();
            // Set up view
            holder.name = (TextView)row.findViewById(R.id.recipe_list_name);
            holder.abv = (TextView)row.findViewById(R.id.recipe_list_abv);
            holder.type = (ImageView)row.findViewById(R.id.recipe_list_type);
            holder.ratingBar = (RatingBar)row.findViewById(R.id.ratingBar);


            row.setTag(holder);
        } else {
            holder = (RecipeListHolder)row.getTag();
        }
        Log.i("Adapter", data.get(position) + " --> " + app.getRecipeByID(data.get(position)));
        Recipe recipe = app.getRecipeByID(data.get(position));

        //Set holder variables
        holder.type.setImageResource(R.drawable.amber_ale);

        // This will be used when the backend is set up
        holder.name.setText(recipe.getName());
        holder.abv.setText(Double.toString(recipe.getABV()) + "% ABV");
        holder.ratingBar.setRating(Float.valueOf("" + recipe.getRating()));

        return row;
    }

}
