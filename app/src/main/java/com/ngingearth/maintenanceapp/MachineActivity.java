package com.ngingearth.maintenanceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.app.ListActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;

import com.ngingearth.maintenanceapp.Apps.AppController;
import com.ngingearth.maintenanceapp.Constants;
import com.ngingearth.maintenanceapp.Models.Machine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;

import static com.ngingearth.maintenanceapp.Constants.BASE_URL;


public class MachineActivity extends AppCompatActivity {
    private static final String TAG = MachineActivity.class.getSimpleName();

    private static final String urlMachine = BASE_URL + "machine_management/api/get_all_machine.php";
    private ProgressDialog pDialog;
    private List<Machine> machineList = new ArrayList<Machine>();
    private ListView listView;
    private CustomListAdapter adapter;

    private TextView textName;
    private TextView textCode;
    private TextView textPost;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);

        textName = (TextView) findViewById(R.id.textName);
        textCode = (TextView) findViewById(R.id.textPass);
        textPost = (TextView) findViewById(R.id.textPost);
        imageView = (ImageView) findViewById(R.id.imgAndroid);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String username = extras.getString("USERNAME");
        String password = extras.getString("PASSWORD");
        String userid = extras.getString("USERID");
        String department = extras.getString("DEPARTMENT");
        String imageUrl = extras.getString("IMAGEURL");
        String image = extras.getString("IMAGE");

        final HashMap<String, String> employeeMap = new HashMap<>();
        employeeMap.put("name", username);
        employeeMap.put("id", userid);
        employeeMap.put("department", department);
        employeeMap.put("imageUrl", imageUrl);
        employeeMap.put("password", password);
        employeeMap.put("image", image);


        Uri uri = Uri.parse(imageUrl);
        Log.d(uri.toString(), "5555555");
        Glide.with(MachineActivity.this).load(uri).into(imageView);

        textName.setText(username);
        textCode.setText(userid);
        textPost.setText(department);

        listView = (ListView) findViewById(R.id.listview);
        adapter = new CustomListAdapter(this, machineList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MachineActivity.this, machineList.get(i).getName(), Toast.LENGTH_SHORT).show();

                int id = machineList.get(i).getId();
                String name = machineList.get(i).getName();
                String code = machineList.get(i).getCode();
                String urlMachineImage = machineList.get(i).getUrl_machine();
                String urlMachineDetail = machineList.get(i).getUrl_detail();
                String type = machineList.get(i).getType();

                HashMap<String, String> machineMap = new HashMap<>();
                machineMap.put("id", String.valueOf(id));
                machineMap.put("name", name);
                machineMap.put("code", code);
                machineMap.put("urlImage", urlMachineImage);
                machineMap.put("urlDetail", urlMachineDetail);
                machineMap.put("type", type);

                Intent intent = new Intent(MachineActivity.this, NavigationActivirty.class);
                intent.putExtra("machineMap", machineMap);
                intent.putExtra("employeeMap", employeeMap);
                startActivity(intent);
            }
        });


        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest machineReq = new JsonArrayRequest(urlMachine,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Machine machine = new Machine();
                                machine.setId(obj.getInt("id_machine"));
                                machine.setName(obj.getString("name_machine"));
                                machine.setUrl_machine(obj.getString("url_machine"));
                                machine.setCode(obj.getString("code_machine"));
                                machine.setUrl_detail(obj.getString("url_detail"));
                                machine.setType(obj.getString("type"));

                                // adding machine to machines array
                                machineList.add(machine);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        hidePDialog();

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(machineReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}

