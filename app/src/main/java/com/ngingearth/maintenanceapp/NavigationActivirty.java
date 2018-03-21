package com.ngingearth.maintenanceapp;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ngingearth.maintenanceapp.Models.Machine;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class NavigationActivirty extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        private String Mac;
        private TextView txtname;
        private TextView txtpost;
        private ImageView imageEmp;
        private HashMap<String, String> machineMap, employeeMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_activirty);

        if (savedInstanceState != null) {
            machineMap = (HashMap<String, String>) savedInstanceState.getSerializable("machineMap");
            employeeMap = (HashMap<String, String>) savedInstanceState.getSerializable("employeeMap");
        } else {
            Intent intent = getIntent();
            machineMap = (HashMap<String, String>)intent.getSerializableExtra("machineMap");
            employeeMap = (HashMap<String, String>)intent.getSerializableExtra("employeeMap");


            Toast.makeText(NavigationActivirty.this,machineMap.get("name"),Toast.LENGTH_LONG).show();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            final ImageView imgMac = findViewById(R.id.ImageMachine);

            String urlMachine = machineMap.get("urlDetail");

            Uri uri = Uri.parse(urlMachine);
            Log.d(uri.toString(), "5555555");
            Glide.with(NavigationActivirty.this).load(uri).into(imgMac);

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Machine " + machineMap.get("name"));


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View header=navigationView.getHeaderView(0);
            imageEmp = (ImageView) header.findViewById(R.id.imgNavigation);
            txtname = (TextView) header.findViewById(R.id.txtNavigationName);
            txtpost = (TextView) header.findViewById(R.id.txtNavigationPost);

            Uri uriEmp = Uri.parse(employeeMap.get("imageUrl"));
            Log.d("ddddddd", uriEmp.toString());
            Glide.with(NavigationActivirty.this).load(uriEmp).into(imageEmp);
            txtname.setText(employeeMap.get("name"));
            txtpost.setText(employeeMap.get("department"));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_activirty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(NavigationActivirty.this, id, Toast.LENGTH_SHORT);
            Intent intent = new Intent(NavigationActivirty.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_history) {
            Toast.makeText(NavigationActivirty.this, id, Toast.LENGTH_SHORT);
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("machineMap", machineMap);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//         Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_monitor) {
            Toast.makeText(NavigationActivirty.this, id, Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(NavigationActivirty.this, MonitorActivity.class);
//            intent.putExtra("Machine", Machine); //Put your id to your next Intent
//            startActivity(intent);
//            finish();
            // Handle the camera action
        } else if (id == R.id.nav_plan) {
            Toast.makeText(NavigationActivirty.this, machineMap.get("name"), Toast.LENGTH_SHORT).show();
            Log.d("hhhhh", machineMap.get("name"));
            Intent intent = new Intent(NavigationActivirty.this, planActivity.class);
            intent.putExtra("machineMap", machineMap); //Put your id to your next Intent
            intent.putExtra("employeeMap", employeeMap);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_datasheet) {
            Intent intent = new Intent(NavigationActivirty.this, DatasheetActivity.class);
            intent.putExtra("machineMap", machineMap); //Put your id to your next Intent
            intent.putExtra("employeeMap", employeeMap);
            startActivity(intent);
            finish();

        }  else if (id == R.id.nav_changemachine) {
            Toast.makeText(NavigationActivirty.this, id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NavigationActivirty.this, MachineActivity.class);
            intent.putExtra("machineMap", machineMap);
            intent.putExtra("USERNAME", employeeMap.get("name"));
            intent.putExtra("PASSWORD", employeeMap.get("password"));
            intent.putExtra("USERID", employeeMap.get(id));
            intent.putExtra("DEPARTMENT", employeeMap.get("department"));
            intent.putExtra("IMAGEURL", employeeMap.get("imageUrl"));
            intent.putExtra("IMAGE", employeeMap.get("image"));
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("machineMap", machineMap);
        outState.putSerializable("employeeMap", employeeMap);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        machineMap = (HashMap<String, String>) savedInstanceState.getSerializable("machineMap");
        employeeMap = (HashMap<String, String>) savedInstanceState.getSerializable("employeeMap");
    }

}
