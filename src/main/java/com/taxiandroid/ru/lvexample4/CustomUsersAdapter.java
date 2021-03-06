package com.taxiandroid.ru.lvexample4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by saperov on 21.10.15.
 */
public class CustomUsersAdapter extends ArrayAdapter<User> {
    public CustomUsersAdapter (Context context,ArrayList<User> users){
        super(context,0,users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHometown);
        TextView tvIndivid = (TextView) convertView.findViewById(R.id.tvIndivid);
        // Populate the data into the template view using the data object
        tvName.setText(user.name);
        tvHome.setText(user.hometown);
        tvIndivid.setText(user.tvIndivid);
        convertView.setBackgroundColor(0xFFD3D3D3);
        tvHome.setTextColor(0xFFFF4500);
        if (tvIndivid.getText().equals("индивидуальный")) {
            tvIndivid.setTextColor(0xFFFF69B4);
            tvIndivid.setTextSize(30);
        }
        /*if (position == 0) {
            convertView.setBackgroundColor(0x300000FF);
        }*/

        // Return the completed view to render on screen
        //еще комментарий
        return convertView;
    }
}
