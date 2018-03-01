package com.ngingearth.maintenanceapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NgiNG on 24/10/2560.
 */

public class Datalist extends ArrayAdapter<Data>{
    private Activity context;
    private List<Data> Datalist;

    public Datalist(Activity context,List<Data>Datalist){
        super(context,R.layout.datalist,Datalist);
        this.context = context;
        this.Datalist = Datalist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.datalist,null,true);

        TextView temp= (TextView) listViewItem.findViewById(R.id.txtTemp);
        TextView  humi = (TextView) listViewItem.findViewById(R.id.txtHumidity);
        TextView  time = (TextView) listViewItem.findViewById(R.id.txtTime);
        Data data = Datalist.get(position);

        temp.setText(data.getTemperature());
        humi.setText(data.getHumidity());
        time.setText(data.getTime());

        return listViewItem;
    }




}




