package edu.uw.informatics.brewsky;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

            row.setTag(holder);
        } else {
            holder = (RecipeListHolder)row.getTag();
        }
        Recipe recipe = data.get(position);

        //Set holder variables



        return row;
    }

}
