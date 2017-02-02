package com.codingblocks.hackcsi.UI;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingblocks.hackcsi.R;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final ArrayList<String> name;
    private final ArrayList<String> email;
    private final ArrayList<String> city;

    private final Activity context;

    public CustomListAdapter(Activity context,ArrayList<String> name,ArrayList<String> city,ArrayList<String> email){

        super(context,R.layout.listview,name);

        this.context=context;
        this.name=name;
        this.email=email;
        this.city=city;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview, null, true);
        TextView nametv=(TextView)rowView.findViewById(R.id.name);
        TextView emailtv = (TextView) rowView.findViewById(R.id.email);
        TextView citytv = (TextView) rowView.findViewById(R.id.city);
        Log.e("sachinisgre",this.name.get(0));
        String s1= name.get(position);
        String s2 =email.get(position);
        String s3= city.get(position);
        nametv.setText(this.name.get(position).substring(7,s1.length()-2));
        emailtv.setText(email.get(position).substring(7,s2.length()-2));
        citytv.setText(city.get(position).substring(7,s3.length()-2));


        return rowView;
    }
}

