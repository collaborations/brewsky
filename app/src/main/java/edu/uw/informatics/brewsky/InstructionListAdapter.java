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
 * Created by danielnakamura on 3/11/15.
 */
public class InstructionListAdapter extends ArrayAdapter<String> {
    private ArrayList<String> data;
    private Context context;
    private int layoutResourceId;

    public InstructionListAdapter(Context context, int resource, ArrayList<String> instructions) {
        super(context, resource);
        this.context = context;
        this.data = instructions;
        this.layoutResourceId = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        TextView textInstruction;
        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            // Set up view
            textInstruction = (TextView)row.findViewById(R.id.instruction_list_element);

            row.setTag(textInstruction);
        } else {
            textInstruction = (TextView)row.getTag();
        }
        String instruction = data.get(position);
        //Set holder variables
        Log.i("InstructionListAdapter", "instruction is: " + instruction);
        textInstruction.setText(position + " " + instruction);
        return row;
    }

}
