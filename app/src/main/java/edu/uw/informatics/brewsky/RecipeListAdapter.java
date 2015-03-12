package edu.uw.informatics.brewsky;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ginoclement on 3/10/15.
 */
public class RecipeListAdapter extends ArrayAdapter<Recipe> {
    private ArrayList<Recipe> data;
    private Context context;
    private int layoutResourceId;

    static class RecipeListHolder {
        TextView name;
        TextView abv;
        TextView brewTime;
        ImageView type;
        ImageView rating;
    }

    public RecipeListAdapter(Context context, int resource, ArrayList<Recipe> data) {
        super(context, resource);
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
            holder.brewTime = (TextView)row.findViewById(R.id.recipe_list_brew_time);
            holder.rating = (ImageView)row.findViewById(R.id.recipe_list_rating);
            holder.type = (ImageView)row.findViewById(R.id.recipe_list_type);

            row.setTag(holder);
        } else {
            holder = (RecipeListHolder)row.getTag();
        }
        Recipe recipe = data.get(position);


        holder.rating.setImageResource(R.drawable.three_star);
        holder.type.setImageResource(R.drawable.amber_ale);

        holder.name.setText(recipe.getName());
        holder.abv.setText(Double.toString(recipe.getABV()));
        holder.brewTime.setText(Double.toString(recipe.getBrewDayDuration()));
        return row;
    }

}
