package com.ngingearth.maintenanceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ngingearth.maintenanceapp.Models.ApiUtils;
import com.ngingearth.maintenanceapp.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {
    private ProgressDialog pDialog;

    private EditText inputName;
    private EditText inputPass;
    private Button btnSignin;
    private RequestInterface requestInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputName = (EditText) findViewById(R.id.editName);
        inputPass = (EditText) findViewById(R.id.editPass);
        btnSignin = (Button) findViewById(R.id.btnsignin);

        requestInterface = ApiUtils.getRequestService();
        btnSignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = inputName.getText().toString().trim();
                String password = inputPass.getText().toString().trim();

                if (!name.isEmpty() && !password.isEmpty()) {
                    loginProcess(name, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loginProcess(final String username, final String password) {
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        requestInterface.getEmployee().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                hidePDialog();
                List<User> employee = response.body();
                if(response.isSuccessful()) {
                    for (int i=0;i < employee.size();i++) {
                        if (employee.get(i).getUserName().equals(username) && employee.get(i).getPassword().equals(password)) {
                            Toast.makeText(getApplicationContext(), employee.get(i).getUserName(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, MachineActivity.class);
                            Bundle extras = new Bundle();

                            String userid = employee.get(i).getUserid();
                            String username = employee.get(i).getUserName();
                            String password = employee.get(i).getPassword();
                            String department = employee.get(i).getDepartment();
                            String empimage = employee.get(i).getEmpImage();
                            String imageUrl = employee.get(i).getImageUrl();

                            extras.putString("USERID", userid);
                            extras.putString("USERNAME", username);
                            extras.putString("PASSWORD", password);
                            extras.putString("DEPARTMENT", department);
                            extras.putString("IMAGEURL", imageUrl);
                            extras.putString("IMAGE", empimage);
                            intent.putExtras(extras);
                            startActivity(intent);
                            break;
                        }
                        if (i == employee.size()-1) {
                            Toast.makeText(getApplicationContext(), "Not found.", Toast.LENGTH_LONG).show();
                        }
                    }
                }else {
                    int statusCode  = response.code();

                    // handle request errors depending on status code
                    Log.e("Error Code", String.valueOf(response.code()));
                    Log.e("Error Body", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage() + " err.", Toast.LENGTH_LONG).show();
                hidePDialog();
            }
        });


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

