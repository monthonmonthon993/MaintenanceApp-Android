package com.ngingearth.maintenanceapp;

/**
 * Created by NgiNG on 23/10/2560.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ngingearth.maintenanceapp.Apps.AppController;
import com.ngingearth.maintenanceapp.Models.Machine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatasheetListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<String> datasheet;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public DatasheetListAdapter(Activity activity, ArrayList<String> datasheet) {
        this.activity = activity;
        this.datasheet = datasheet;
    }

    @Override
    public int getCount() {
        return datasheet.size();
    }

    @Override
    public Object getItem(int location) {
        return datasheet.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_datasheet, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.ImageDatasheet);

        // thumbnail image
        thumbNail.setImageUrl(datasheet.get(position), imageLoader);

        return convertView;
    }
}
