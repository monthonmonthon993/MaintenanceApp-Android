package com.ngingearth.maintenanceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.util.List;

public class Spare extends AppCompatActivity {
    int machnum= 0;
    private MobileServiceClient mClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare);


        try {
            mClient = new MobileServiceClient("https://yamonporn.azurewebsites.net",this);
        }
        catch (MalformedURLException e){

        }

        //Toast.makeText(getActivity(),Boolean.toString(null), Toast.LENGTH_SHORT).show();

//        if(NavigationActivirty.Machine.equals("A")){
//            machnum = 7;
//        }
//        else if(NavigationActivirty.Machine.equals("B")){
//            machnum = 8;
//        }
//        else if(NavigationActivirty.Machine.equals("C")){
//            machnum = 9;
//        }
//        mClient.getTable(Day.class).where().field("SeCode").eq(LoginActivity.Id.substring(LoginActivity.Id.length()-5))
//                .and().field("Machine").eq(NavigationActivirty.Machine)
//                .and(mClient.getTable(Day.class).where()
//                .field("No1").eq(false)
//                .or().field("No2").eq(false)
//                .or().field("No3").eq(false)
//                .or().field("No4").eq(false)
//                .or().field("No5").eq(false)
//                .or().field("No6").eq(false)
//                .or().field("No7").eq(false))
//                .execute(new TableQueryCallback<Day>() {
//            @Override
//            public void onCompleted(List<Day> result, int count, Exception exception, ServiceFilterResponse response) {
//                Toast.makeText(Spare.this,Integer.toString(count), Toast.LENGTH_SHORT).show();
//                 for(Day D : result) {
//                //Toast.makeText(LoginActivity.this,user.Name + "   "+user.Password, Toast.LENGTH_SHORT).show();
//                //if (CPass.equals(user.Password))
//
//                    Toast.makeText(Spare.this,D.Note, Toast.LENGTH_SHORT).show();
//
//                //Toast.makeText(LoginActivity.this,user.Name + "   "+user.Password, Toast.LENGTH_SHORT).show();
//                //else
//                //       Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
//
//                  }
//            }
//        });//*/
    }
}
