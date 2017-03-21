package com.collo.phemwaresolutions.collonetworks;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by EntralogIT on 2017-03-16.
 */
public class CountryAdapter extends ArrayAdapter<State> {
    private Activity context;
    ArrayList<State> data = null;

    public CountryAdapter(Activity context, int resource,
                          ArrayList<State> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
        }

        State item = data.get(position);

//        if (item != null) { // Parse the data from each object and set it.
//            TextView CountryId = (TextView) row.findViewById(R.id.item_id);
//            TextView CountryName = (TextView) row.findViewById(R.id.item_value);
//            if (CountryId != null) {
//                CountryId.setText(item.getId());
//            }
//            if (CountryName != null) {
//                CountryName.setText(item.getName());
//            }
//
//        }

        return row;
    }
}
