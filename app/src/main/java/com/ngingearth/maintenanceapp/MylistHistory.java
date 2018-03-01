package com.ngingearth.maintenanceapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MylistHistory extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] days;
    private final String[] date;
    private final String[] desc;
    private final String[] time;

    public MylistHistory (Activity context, String[] date, String[] days,String[] desc,String[] time) {
        super(context, R.layout.activity_mylist_history, date);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.date=date;
        this.days=days;
        this.desc=desc;
        this.time=time;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_mylist_history, null,true);


        TextView txtdayofmonth = (TextView) rowView.findViewById(R.id.tv_day_of_month);
        TextView txtdayofweek = (TextView) rowView.findViewById(R.id.tv_day_of_week);
        TextView description = (TextView) rowView.findViewById(R.id.tv_description);
        TextView Time = (TextView) rowView.findViewById(R.id.tv_time);

        txtdayofmonth.setText(date[position]);
        txtdayofweek.setText(days[position]);
        description.setText(desc[position]);
        Time.setText(time[position]);
        return rowView;


    }


}




