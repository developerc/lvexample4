package com.taxiandroid.ru.lvexample4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by saperov on 25.10.15.
 */
public class CustomTwoAdapter extends ArrayAdapter<Two> {
    public CustomTwoAdapter (Context context,ArrayList<Two> two_items){
        super(context,0,two_items);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Two two = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView text1 = (TextView) convertView.findViewById(R.id.text1);
        text1.setText(two.two_item);


        return convertView;
    }
}
