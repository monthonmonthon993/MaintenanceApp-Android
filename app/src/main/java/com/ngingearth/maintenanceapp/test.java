package com.ngingearth.maintenanceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.aigestudio.wheelpicker.WheelPicker;

/**
 * Created by NgiNG on 14/11/2560.
 */

public class test extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        WheelPicker wheelPicker = new WheelPicker(this);
        FrameLayout flContainer = (FrameLayout) findViewById(R.id.container);
        FrameLayout.LayoutParams flParams = new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flParams.gravity = Gravity.CENTER;
        flContainer.addView(wheelPicker, flParams);//*/
    }
}
