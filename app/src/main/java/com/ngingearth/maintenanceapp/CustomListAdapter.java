package com.ngingearth.maintenanceapp;

/**
 * Created by NgiNG on 23/10/2560.
 */
        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.android.volley.toolbox.ImageLoader;
        import com.android.volley.toolbox.NetworkImageView;
        import com.ngingearth.maintenanceapp.Models.Machine;
        import com.ngingearth.maintenanceapp.Apps.AppController;

        import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Machine> machineItems;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public CustomListAdapter(Activity activity, List<Machine> machineItems) {
        this.activity = activity;
        this.machineItems = machineItems;
    }

    @Override
    public int getCount() {
        return machineItems.size();
    }

    @Override
    public Object getItem(int location) {
        return machineItems.get(location);
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
            convertView = inflater.inflate(R.layout.mylist, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.itemname);
        TextView code = (TextView) convertView.findViewById(R.id.itemcode);

        // getting machine data for the row
        Machine m = machineItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getUrl_machine(), imageLoader);

        // name
        name.setText(m.getName());

        // code
        code.setText("Code: " + String.valueOf(m.getCode()));

        return convertView;
    }



}
