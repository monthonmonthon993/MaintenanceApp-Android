package com.ngingearth.maintenanceapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ngingearth.maintenanceapp.Apps.AppController;

import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;
import static com.ngingearth.maintenanceapp.Constants.INSERT_PM_PLAN_API;

public class planActivity extends AppCompatActivity implements MonthPlan.MyFragmentListener, YearPlan.MyFragmentListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private HashMap<String, String> machineMap, employeeMap;
    private ViewPager mViewPager;

    public void getDataFromFragment(final String note, final int status, final String typePm) {
        Log.d("note", note);
        String url = INSERT_PM_PLAN_API;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (response == "error." || response == "wrong type.") {
                            Toast.makeText(planActivity.this, "Can't add this pm plan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(planActivity.this, "Add this pm plan successfully.", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(planActivity.this, "Can't Connect to server.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("note", note);
                params.put("status", String.valueOf(status));
                params.put("id_machine", machineMap.get("id"));
                params.put("type_pm", typePm);
                return params;
            }
        };
//        queue.add(postRequest);
        AppController.getInstance().addToRequestQueue(postRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);


        Intent intent = getIntent();
        machineMap = (HashMap<String, String>)intent.getSerializableExtra("machineMap");
        employeeMap = (HashMap<String, String>)intent.getSerializableExtra("employeeMap");
        Toast.makeText(planActivity.this, machineMap.get("name"), Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("PM Plan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //  back button

        toolbar.setTitleTextColor(0xFFFFFFFF); // change color toolbar
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan, menu);
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
            Intent intent = new Intent(planActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_history) {

            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("machineMap", machineMap);
            this.startActivity(intent);
            return true;
        }

        // home =  ชื่อเฉพาะ
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, NavigationActivirty.class);
            intent.putExtra("machineMap", machineMap); //Put your id to your next Intent
            intent.putExtra("employeeMap", employeeMap);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    DailyPlan plandaily = new DailyPlan();
                    return plandaily;
                case 1:
                    MonthPlan planmonth = new MonthPlan();
                    return planmonth;

                case 2:
                    YearPlan planyear = new YearPlan();
                    return planyear;
            }
            return null;
        }



        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Daily";
                case 1:
                    return "Month";

                case 2:
                    return "Year";
            }
            return null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("machineMap", machineMap);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        machineMap = (HashMap<String, String>) savedInstanceState.getSerializable("machineMap");
    }
}
