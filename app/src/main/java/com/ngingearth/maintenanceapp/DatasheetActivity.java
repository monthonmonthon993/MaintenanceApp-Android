package com.ngingearth.maintenanceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.Strings;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.ngingearth.maintenanceapp.Apps.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;
import static com.ngingearth.maintenanceapp.Constants.DATASHEET_API_URL;

public class DatasheetActivity extends AppCompatActivity {
    private static final String TAG = DatasheetActivity.class.getSimpleName();
   // private StorageReference mStorageRef;

    private ImageView img;
    private ProgressBar pb;
    private ImageView img1;
    private ImageView img2;
    private WebView wb;
    private String s;
    String DS1;
    String DS2;
    private HashMap<String, String> machineMap ,employeeMap;
    private ArrayList<String> datasheetUrl = new ArrayList<>();
    private ArrayList<String> imageSheetUrl = new ArrayList<>();
    private String type;
    private String id;
    private ListView listView;
    private DatasheetListAdapter adapter;
    private ProgressDialog pDialog;
   // private PDFView pdfView;
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
           intent.putExtra("machineMap", machineMap); //Put your id to your next Intent
           intent.putExtra("employeeMap", employeeMap);
           startActivity(intent);
           finish();
           //intent.putExtra("Machine", s); //Put your id to your next Intent
           //startActivity(intent);
       }
       return super.onOptionsItemSelected(item);
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasheet);
//        s = getIntent().getStringExtra("Machine");
        Intent intent = getIntent();
        machineMap = (HashMap<String, String>) intent.getSerializableExtra("machineMap");
        employeeMap = (HashMap<String, String>) intent.getSerializableExtra("employeeMap");
        id = machineMap.get("id");
        Log.d("id_machine", id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDatasheet);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF); // change color toolbar

        getSupportActionBar().setTitle("Datasheet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //  back button


        listView = findViewById(R.id.listDatasheet);
        adapter = new DatasheetListAdapter(DatasheetActivity.this, imageSheetUrl);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String datasheet_url = datasheetUrl.get(i);
                String googlePdfViewer = "http://docs.google.com/gview?url=" + datasheet_url + "&embedded=true";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(googlePdfViewer));
                startActivity(intent);
            }
        });

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url = DATASHEET_API_URL + "get_datasheet_by_machine.php?id_machine=" + id;
        JsonArrayRequest datasheetReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        Log.d("888888", "response successfully.");

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                datasheetUrl.add(obj.getString("datasheet_url"));
                                imageSheetUrl.add(obj.getString("imagesheet_url"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        hidePDialog();
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
        AppController.getInstance().addToRequestQueue(datasheetReq);

    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
