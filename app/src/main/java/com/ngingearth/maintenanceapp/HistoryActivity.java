package com.ngingearth.maintenanceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.ngingearth.maintenanceapp.Apps.AppController;
import com.ngingearth.maintenanceapp.Models.Machine;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
//import com.stacktips.view.CalendarListener;
//import com.stacktips.view.CustomCalendarView;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.ngingearth.maintenanceapp.Constants.PM_API_URL;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = HistoryActivity.class.getSimpleName();

    private HashMap<String, String> machineMap;
    private String[] noteDaily;
    private String[] dateDaily;
    private String[] dayDaily;
    private String[] timeDaily;
    private String[] noteFilterDaily;
    private String[] dateFilterDaily;
    private String[] dayFilterDaily;
    private String[] timeFilterDaily;
    private String[] monthOfDaily;
    private String[] monthOfDailyFilter;
    private String[] yearOfDaily;
    private String[] getYearOfDailyFilter;
    private String[] aday;
    private String[] adayFilter;

    private String[] noteMonthly;
    private String[] dateMonthly;
    private String[] dayMonthly;
    private String[] timeMonthly;
    private String[] noteFilterMonthly;
    private String[] dateFilterMonthly;
    private String[] dayFilterMonthly;
    private String[] timeFilterMonthly;
    private String[] yearOfMonth;
    private String[] yearOfMonthFilter;
    private String[] amonth;
    private String[] amonthFilter;

    private String[] noteYearly;
    private String[] dateYearly;
    private String[] dayYearly;
    private String[] timeYearly;
    private String[] noteFilterYearly;
    private String[] dateFilterYearly;
    private String[] dayFilterYearly;
    private String[] timeFilterYearly;
    private String[] ayear;
    private String[] ayearFilter;

    private ProgressDialog pDialog;

    private Spinner spinner;
    private ArrayList<String> dmy = new ArrayList<>();
    private String typedmy = "day";

    private NumberPicker npMonth;
    private NumberPicker npYear;
    private String[] Months = new String[12];
    private String[] Years = new String[7];
    private String monthSelect = "";
    private String yearSelect = "";

    private ListView listView;

    ArrayAdapter<String> adapterDayMonthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent intent = getIntent();
        machineMap = (HashMap<String, String>) intent.getSerializableExtra("machineMap");
        Log.d("id", machineMap.get("id"));
        getPmPlanJsonRequest();
        setUpToolBar();

    }

    private void getPmPlanJsonRequest() {
        String id = machineMap.get("id");
        String url = PM_API_URL + "get_pm_by_machine.php?id_machine=" + id;
        JsonArrayRequest machineReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        Log.d("888888", "response successfully.");
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Log.d("try", response.getString(i));
                                JSONObject pmObj = response.getJSONObject(i);
                                Log.d("oooooooo", pmObj.toString());

                                if (pmObj.has("daily")) {
                                    JSONArray day = pmObj.getJSONArray("daily");
                                    Log.d("aaaaaaaaa", day.toString());
                                    noteDaily = new String[day.length()];
                                    dateDaily = new String[day.length()];
                                    dayDaily = new String[day.length()];
                                    timeDaily = new String[day.length()];
                                    monthOfDaily = new String[day.length()];
                                    yearOfDaily = new String[day.length()];
                                    aday = new String[day.length()];
                                    for (int j = 0; j < day.length(); j++) {
                                        JSONObject obj = day.getJSONObject(j);
                                        noteDaily[j] = obj.getString("note");
                                        dateDaily[j] = obj.getString("date");
                                        dayDaily[j] = obj.getString("day");
                                        timeDaily[j] = obj.getString("time");
                                        monthOfDaily[j] = obj.getString("month");
                                        yearOfDaily[j] = obj.getString("year");
                                        aday[j] = obj.getString("day");
                                        Log.d("monthOfDailyFirst", monthOfDaily[i]);
                                    }
                                } else {
                                    aday = new String[1];
                                    Arrays.fill(aday, null);
                                }

                                if (pmObj.has("monthly")) {
                                    JSONArray month = pmObj.getJSONArray("monthly");
                                    Log.d("aaaaaaaaa", month.toString());
                                    noteMonthly = new String[month.length()];
                                    dateMonthly = new String[month.length()];
                                    dayMonthly = new String[month.length()];
                                    timeMonthly = new String[month.length()];
                                    yearOfMonth = new String[month.length()];
                                    amonth = new String[month.length()];
                                    for (int j = 0; j < month.length(); j++) {
                                        JSONObject obj = month.getJSONObject(j);
                                        noteMonthly[j] = obj.getString("note");
                                        dateMonthly[j] = obj.getString("date");
                                        dayMonthly[j] = obj.getString("day");
                                        timeMonthly[j] = obj.getString("time");
                                        yearOfMonth[j] = obj.getString("year");
                                        amonth[j] = obj.getString("month");
                                    }
                                } else {
                                    amonth = new String[1];
                                    Arrays.fill(amonth, null);
                                }

                                if (pmObj.has("yearly")) {
                                    JSONArray year = pmObj.getJSONArray("yearly");
                                    Log.d("aaaaaaaaa", year.toString());
                                    noteYearly = new String[year.length()];
                                    dateYearly = new String[year.length()];
                                    dayYearly = new String[year.length()];
                                    timeYearly = new String[year.length()];
                                    ayear = new String[year.length()];
                                    for (int j = 0; j < year.length(); j++) {
                                        JSONObject obj = year.getJSONObject(j);
                                        noteYearly[j] = obj.getString("note");
                                        dateYearly[j] = obj.getString("date");
                                        dayYearly[j] = obj.getString("day");
                                        timeYearly[j] = obj.getString("time");
                                        ayear[j] = obj.getString("year");
                                    }
                                } else {
                                    ayear = new String[1];
                                    Arrays.fill(ayear, null);
                                }

                                //other process
                                //Numberpicker
                                npMonth = findViewById(R.id.numberPicker2);
                                npYear = findViewById(R.id.numberPicker3);
                                createMonth();
                                createYear();
                                npMonth.setMinValue(0);
                                npMonth.setMaxValue(Months.length-1);
                                npMonth.setDisplayedValues(Months);
                                npMonth.setWrapSelectorWheel(true);
                                npYear.setMinValue(0);
                                npYear.setMaxValue(Years.length-1);
                                npYear.setDisplayedValues(Years);
                                npYear.setWrapSelectorWheel(true);

                                //Spinner
                                spinner = findViewById(R.id.spinner3);
                                createDayMonthYear();
                                adapterDayMonthYear = new ArrayAdapter<>(HistoryActivity.this,
                                        R.layout.support_simple_spinner_dropdown_item, dmy);
                                spinner.setAdapter(adapterDayMonthYear);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        typedmy = dmy.get(i);
                                        Log.d("type", typedmy);
                                        monthSelect = npMonth.getDisplayedValues()[npMonth.getValue()];
                                        yearSelect = npYear.getDisplayedValues()[npYear.getValue()];
                                        Log.d("spinner", monthSelect +" :" + yearSelect);
                                        getData(typedmy, monthSelect, yearSelect);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                npMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                                    @Override
                                    public void onValueChange(NumberPicker numberPicker, int oldVar, int newVar) {
                                        Log.d("numbers", numberPicker.toString());
                                        Log.d("month", Months[newVar]);
                                        monthSelect = Months[newVar];
                                        Log.d("year2", npYear.getDisplayedValues()[npYear.getValue()]);
                                        yearSelect = npYear.getDisplayedValues()[npYear.getValue()];
                                        Log.d("my", yearSelect + ":" + monthSelect);
                                        getData(typedmy, monthSelect, yearSelect);

                                    }
                                });
                                npYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                                    @Override
                                    public void onValueChange(NumberPicker numberPicker, int oldVar, int newVar) {
                                        Log.d("numbers", numberPicker.toString());
                                        Log.d("year", Years[newVar]);
                                        yearSelect = Years[newVar];
                                        Log.d("month2", npMonth.getDisplayedValues()[npMonth.getValue()]);
                                        monthSelect = npMonth.getDisplayedValues()[npMonth.getValue()];
                                        Log.d("my", yearSelect + ":" + monthSelect);
                                        getData(typedmy, monthSelect, yearSelect);
                                    }
                                });




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
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

    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbarHistory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");
        toolbar.setTitleTextColor(0xFFFFFFFF); // change color toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //  back button
    }

    private void createDayMonthYear() {
        dmy.add("Day");
        dmy.add("Month");
        dmy.add("Year");
    }

    private void createMonth() {
        Months[0] = "January";
        Months[1] = "February";
        Months[2] = "March";
        Months[3] = "April";
        Months[4] = "May";
        Months[5] = "June";
        Months[6] = "July";
        Months[7] = "August";
        Months[8] = "September";
        Months[9] = "October";
        Months[10] = "November";
        Months[11] = "December";
    }

    private void createYear() {
        Years[0] = "2015";
        Years[1] = "2016";
        Years[2] = "2017";
        Years[3] = "2018";
        Years[4] = "2019";
        Years[5] = "2020";
        Years[6] = "2021";
    }

    private void getData(String type, String month, String year) {
        Log.d("typeget", type);
        if (type.equals("Day")) {
            Log.d("dayssss",type);
            ArrayAdapter<String> adapter;
            getSelect(month ,year);
            adapter = new MylistHistory(HistoryActivity.this, dateFilterDaily, dayFilterDaily, noteFilterDaily, timeFilterDaily);
            listView = findViewById(R.id.listViewHistory);
            listView.setAdapter(adapter);

        } else if (type.equals("Month")) {
            Log.d("monthsss",type);
            ArrayAdapter<String> adapter;
            getSelect(month ,year);
            adapter = new MylistHistory(HistoryActivity.this, dateFilterMonthly, dayFilterMonthly, noteFilterMonthly, timeFilterMonthly);
            listView = findViewById(R.id.listViewHistory);
            listView.setAdapter(adapter);

        } else if (type.equals("Year")) {
            Log.d("yearsssss",type);
            ArrayAdapter<String> adapter;
            getSelect(month ,year);
            adapter = new MylistHistory(HistoryActivity.this, dateFilterYearly, dayFilterYearly, noteFilterYearly, timeFilterYearly);
            listView = findViewById(R.id.listViewHistory);
            listView.setAdapter(adapter);

        } else {
            Toast.makeText(HistoryActivity.this, "Please select correct type.", Toast.LENGTH_SHORT);
            Log.d("select", "non type");
        }
    }

    private void getSelect(String month, String year) {
        noteFilterDaily = new String[dayDaily.length];
        dateFilterDaily = new String[dayDaily.length];
        dayFilterDaily = new String[dayDaily.length];
        timeFilterDaily = new String[dayDaily.length];
        monthOfDailyFilter = new String[dayDaily.length];
        if (aday[0] != null) {
            for (int i = 0; i < dayDaily.length; i++) {
                if (monthOfDaily[i].equals(month) && yearOfDaily[i].equals(year)) {
                    monthOfDailyFilter[i] = monthOfDaily[i];
                    noteFilterDaily[i] = noteDaily[i];
                    dateFilterDaily[i] = dateDaily[i];
                    dayFilterDaily[i] = dayDaily[i];
                    timeFilterDaily[i] = timeDaily[i];
                }
            }
        }

        noteFilterMonthly = new String[amonth.length];
        dateFilterMonthly = new String[amonth.length];
        dayFilterMonthly = new String[amonth.length];
        timeFilterMonthly = new String[amonth.length];
        amonthFilter = new String[amonth.length];
        if (amonth[0] != null) {
            for (int i = 0; i < amonth.length; i++) {
                if (amonth[i].equals(month) && yearOfMonth[i].equals(year)) {
                    amonthFilter[i] = month;
                    noteFilterMonthly[i] = noteMonthly[i];
                    dateFilterMonthly[i] = dateMonthly[i];
                    dayFilterMonthly[i] = dayMonthly[i];
                    timeFilterMonthly[i] = timeMonthly[i];
                }
            }
        }

        noteFilterYearly = new String[ayear.length];
        dateFilterYearly = new String[ayear.length];
        dayFilterYearly = new String[ayear.length];
        timeFilterYearly = new String[ayear.length];
        ayearFilter = new String[ayear.length];
        if (ayear[0] != null) {
            for (int i = 0; i < ayear.length; i++) {
                if (ayear[i].equals(year)) {
                    ayearFilter[i] = year;
                    noteFilterYearly[i] = noteYearly[i];
                    dateFilterYearly[i] = dateYearly[i];
                    dayFilterYearly[i] = dayYearly[i];
                    timeFilterYearly[i] = timeYearly[i];
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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


