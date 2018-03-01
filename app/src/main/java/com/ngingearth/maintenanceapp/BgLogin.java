package com.ngingearth.maintenanceapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by NgiNG on 1/2/2561.
 */

public class BgLogin extends AsyncTask<String,Void,String> {

    public static String eID,eName,ePosition;


    Context context;
    AlertDialog alertDialog;
    BgLogin (Context ctx){
        context = ctx;
    }



    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://192.168.1.2/employee/login.php";
        Log.d(params[0], "aaaaaa");
        if (type.equals("login")) {
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("JSON Result", result.toString());

                StringTokenizer tokens = new StringTokenizer(result, ":");
                String first = tokens.nextToken();// this will contain "Can login! Id"
                String second = tokens.nextToken();// this will contain "57011030 , Name"
                String thrid = tokens.nextToken();// this will contain "earth , Position"
                ePosition = tokens.nextToken();// this will contain "Maintenance"


                StringTokenizer tokens2 = new StringTokenizer(second, ",");
                eID = tokens2.nextToken();// this will contain "5711030"
                String xxx = tokens2.nextToken();// this will contain "Name"

                StringTokenizer tokens3 = new StringTokenizer(thrid, ",");
                eName = tokens3.nextToken();// this will contain "earth"
                String xxxx = tokens3.nextToken();// this will contain "position"

                Log.d("JSON Result", eID.toString());
                Log.d("JSON Result", eName.toString());
                Log.d("JSON Result", ePosition.toString());





                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();


        if (result.contains("Can login")) {
//            alertDialog.setMessage("login Success");
//            alertDialog.show();
            Intent intent = new Intent(context, MachineActivity.class);
            intent.putExtra("Code", eID); //Put your id to your next Intent
            intent.putExtra("Name", eName); //Put your id to your next Intent
            intent.putExtra("Position", ePosition); //Put your id to your next Intent
            context.startActivity(intent);
        }
//        else {
//            alertDialog.setMessage(result);
//            alertDialog.show();
//
//        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }








}
