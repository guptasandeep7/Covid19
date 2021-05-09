package com.example.covid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CovidAdapter extends ArrayAdapter<CovidCase> {

    public CovidAdapter(Context context, ArrayList<CovidCase> androidFlavors) {

        super(context, 0, androidFlavors);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }


        CovidCase currentstate = getItem(position);



        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView state = (TextView) listItemView.findViewById(R.id.stateName);
        state.setText(currentstate.getStateName());
        TextView active = (TextView) listItemView.findViewById(R.id.active);
        active.setText(String.valueOf(currentstate.getActive()));
        TextView recovered = (TextView) listItemView.findViewById(R.id.recovered);
        recovered.setText(String.valueOf(currentstate.getRecovered()));
        TextView deaths = (TextView) listItemView.findViewById(R.id.deaths);
        deaths.setText(String.valueOf(currentstate.getDeaths()));
        TextView confirmed = (TextView) listItemView.findViewById(R.id.confirmed);
        confirmed.setText(String.valueOf(currentstate.getConfirmed()));

        return listItemView;
    }

}
