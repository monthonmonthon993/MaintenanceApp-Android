package com.ngingearth.maintenanceapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class MonitorActivity extends AppCompatActivity {

    EditText Name;
    Button btn;
    Spinner spn;
    ListView LV;
    LineGraphSeries<DataPoint> series,series2;

    List<Data> Datalist;
    DatabaseReference db;

    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMonitor);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Monitor");
        toolbar.setTitleTextColor(0xFFFFFFFF); // change color toolbar


        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //  back button


        s = getIntent().getStringExtra("Machine");

        if (s.equals("A")) {
            db = FirebaseDatabase.getInstance().getReference("logDHT");
        } else if (s.equals("B")) {
            db = FirebaseDatabase.getInstance().getReference("logDHT-2");
        }
//        db = FirebaseDatabase.getInstance().getReference("artists");

//        Name = (EditText)findViewById(R.id.editText);
//        btn = (Button) findViewById(R.id.button2);
//        spn = (Spinner) findViewById(R.id.spinner);
        LV = (ListView) findViewById(R.id.graphListView);

        Datalist = new ArrayList<>();
    }
    @Override
    protected void onStart(){
        super.onStart();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Datalist.clear();

                GraphView graph = (GraphView) findViewById(R.id.graph);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(10);

                // enable scaling and scrolling
                graph.getViewport().setScalable(true);
                graph.getViewport().setScalableY(true);

                            series = new LineGraphSeries<DataPoint>();
                series2 = new LineGraphSeries<DataPoint>();

                double x,y,y2;
                x = 0;

                for(DataSnapshot Snap: dataSnapshot.getChildren()){

                    String value = Snap.getValue().toString();
                    value = value.replaceAll("[{}]",""); // take {} out
                    String[] valuePart = value.split(", ");
                    String time = valuePart[0];
                    String temp = valuePart[1];
                    String humi = valuePart[2];
                    // Toast.makeText(MainActivity.this, "time = "+time+" temp = "+temp+" humi = "+humi, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this, Snap.getValue().toString(), Toast.LENGTH_SHORT).show();
//                    Artist artist = Snap.getValue(Artist.class);
                    Data data = new Data(time.substring(time.lastIndexOf("=")+1),temp,humi);
                    //Toast.makeText(MainActivity.this, artist.getTemperature()+" "+artist.getHumidity(), Toast.LENGTH_SHORT).show();

                    Datalist.add(data);

                    // Graph
                    x = x + 0.1;
                    temp = (temp.substring(temp.lastIndexOf("=") + 1));
                    time = (time.substring(time.lastIndexOf("=") + 1));
                    humi = (humi.substring(humi.lastIndexOf("=") + 1));
                    y = Double.parseDouble(temp);
                    y2 = Double.parseDouble(humi);
                    series.appendData(new DataPoint(x,y),true,1000);
                    series2.appendData(new DataPoint(x,y2),true,1000);
                }
                series.setColor(Color.RED);
                series2.setColor(Color.BLUE);
                graph.addSeries(series);
                graph.addSeries(series2);

                Datalist adapter = new Datalist(MonitorActivity.this,Datalist);
                LV.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        // home =  ชื่อเฉพาะ
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, NavigationActivirty.class);
            intent.putExtra("Machine", s); //Put your id to your next Intent
            startActivity(intent);
            finish();
            //intent.putExtra("Machine", s); //Put your id to your next Intent
            //startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }





}




