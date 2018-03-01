package com.ngingearth.maintenanceapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by NgiNG on 5/11/2560.
 */

public class YearPlan extends Fragment {
    private MobileServiceClient mClient;
    private String s;
    private EditText edt;
    private HashMap<String, String> machineMap;
    private String name, code, type;

    RadioButton checkBoxy1;
    RadioButton checkBoxy2;
    RadioButton checkBoxy3;
    RadioButton checkBoxy4;
    RadioButton checkBoxy5;
    RadioButton checkBoxy6;
    RadioButton checkBoxy7;
    RadioButton checkBoxy8;
    RadioButton checkBoxy9;
    RadioButton checkBoxy10;
    RadioButton checkBoxy11;
    RadioButton checkBoxy12;
    RadioButton checkBoxy13;
    RadioButton checkBoxy14;
    RadioButton checkBoxy15;
    RadioButton checkBoxy16;
    RadioButton checkBoxy17;
    RadioButton checkBoxy18;
    private String note;
    private int status;
    private String typePm;

    private MyFragmentListener listener;

    public static YearPlan newInstance() {
        return new YearPlan();
    }


    public interface MyFragmentListener {
        void getDataFromFragment(String note, int status, String typePm);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MyFragmentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Must implement MyFragmentListener");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.yearly_plan, container, false);

//        s = getActivity().getIntent().getStringExtra("Machine");
        Intent intent = getActivity().getIntent();
        machineMap = (HashMap<String, String>)intent.getSerializableExtra("machineMap");
        type = machineMap.get("type");

        edt = (EditText)rootView.findViewById(R.id.edt3);
        FrameLayout touchInterceptor = (FrameLayout)rootView.findViewById(R.id.fl2);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (edt.isFocused()) {
                        Rect outRect = new Rect();
                        edt.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                            edt.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });

        TextView tvchoicey1 = (TextView) rootView.findViewById(R.id.tvchoicey1);
        TextView tvchoicey2 = (TextView) rootView.findViewById(R.id.tvchoicey2);
        TextView tvchoicey3 = (TextView) rootView.findViewById(R.id.tvchoicey3);
        TextView tvchoicey4 = (TextView) rootView.findViewById(R.id.tvchoicey4);
        TextView tvchoicey5 = (TextView) rootView.findViewById(R.id.tvchoicey5);
        TextView tvchoicey6 = (TextView) rootView.findViewById(R.id.tvchoicey6);
        TextView tvchoicey7 = (TextView) rootView.findViewById(R.id.tvchoicey7);
        TextView tvchoicey8 = (TextView) rootView.findViewById(R.id.tvchoicey8);
        TextView tvchoicey9 = (TextView) rootView.findViewById(R.id.textViewy9);
         checkBoxy1 = (RadioButton) rootView.findViewById(R.id.checkBoxy1);
         checkBoxy2 = (RadioButton) rootView.findViewById(R.id.checkBoxy2);
         checkBoxy3 = (RadioButton) rootView.findViewById(R.id.checkBoxy3);
         checkBoxy4 = (RadioButton) rootView.findViewById(R.id.checkBoxy4);
         checkBoxy5 = (RadioButton) rootView.findViewById(R.id.checkBoxy5);
         checkBoxy6 = (RadioButton) rootView.findViewById(R.id.checkBoxy6);
         checkBoxy7 = (RadioButton) rootView.findViewById(R.id.checkBoxy7);
         checkBoxy8 = (RadioButton) rootView.findViewById(R.id.checkBoxy8);
         checkBoxy9 = (RadioButton) rootView.findViewById(R.id.checkBoxy9);
         checkBoxy10 = (RadioButton) rootView.findViewById(R.id.checkBoxy10);
         checkBoxy11 = (RadioButton) rootView.findViewById(R.id.checkBoxy11);
         checkBoxy12 = (RadioButton) rootView.findViewById(R.id.checkBoxy12);
         checkBoxy13 = (RadioButton) rootView.findViewById(R.id.checkBoxy13);
         checkBoxy14 = (RadioButton) rootView.findViewById(R.id.checkBoxy14);


        ImageView saveYear = (ImageView) rootView.findViewById(R.id.save3);


        if(type.equals("compressor")){
            tvchoicey2.setText("1.เปลี่ยนน้ำมันของเครื่องอัดอากาศ");
            tvchoicey3.setText("2.เปลี่ยนไส้กรองอากาศ");
            checkBoxy5.setVisibility(View.GONE);
            checkBoxy6.setVisibility(View.GONE);
            checkBoxy7.setVisibility(View.GONE);
            checkBoxy8.setVisibility(View.GONE);
            checkBoxy9.setVisibility(View.GONE);
            checkBoxy10.setVisibility(View.GONE);
            checkBoxy11.setVisibility(View.GONE);
            checkBoxy12.setVisibility(View.GONE);
            checkBoxy13.setVisibility(View.GONE);
            checkBoxy14.setVisibility(View.GONE);


            saveYear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DateFormat dateFormat = new SimpleDateFormat("E yyyy/MM/dd");
                    Date date = new Date();
                    if((checkBoxy1.isChecked() || checkBoxy2.isChecked() )&&
                            (checkBoxy3.isChecked() || checkBoxy4.isChecked())
                            ) {

//

                        note = edt.getText().toString();
                        status = 0;
                        if (note.isEmpty()) {
                            status=1;
                            note = "ปกติ";
                        }
                        typePm = "yearly";
                        listener.getDataFromFragment(note, status, typePm);

                        Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });


        } else if (type.equals("transformer")){
            tvchoicey2.setText("1.น้ำมันหม้อแปลง(Transformer oil)");
            tvchoicey3.setText("2.จุดสายต่อลงดิน");
            tvchoicey4.setText("3.อุปกรณ์ป้องกันต่างๆ");
            tvchoicey5.setText("4.ค่า Insulation resistance  ของขดลวดหม้อแปลง");
            checkBoxy9.setVisibility(View.GONE);
            checkBoxy10.setVisibility(View.GONE);
            checkBoxy11.setVisibility(View.GONE);
            checkBoxy12.setVisibility(View.GONE);
            checkBoxy13.setVisibility(View.GONE);
            checkBoxy14.setVisibility(View.GONE);


            saveYear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();
                    if((checkBoxy1.isChecked() || checkBoxy2.isChecked() )&&
                            (checkBoxy3.isChecked() || checkBoxy4.isChecked())&&
                            (checkBoxy5.isChecked() || checkBoxy6.isChecked())&&
                            (checkBoxy7.isChecked() || checkBoxy8.isChecked())

                            ) {


                        note = edt.getText().toString();
                        status = 0;
                        if (note.isEmpty()) {
                            status=1;
                            note = "ปกติ";
                        }
                        typePm = "yearly";
                        listener.getDataFromFragment(note, status, typePm);

                        Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });


        } else if (type.equals("boiler")){
            tvchoicey2.setText("1.ทําความสะอาดผนังด้านที่สัมผัสโดยตรงกับไฟ");
            tvchoicey3.setText("2.สภาพของปล่องไอเสีย พร้อมทําความสะอาด");
            tvchoicey4.setText("3.สภาพของถังบรรจุน้ํามัน");
            tvchoicey5.setText("4.การทํางานของ Safety valve");
            tvchoicey6.setText("5.สภาพของปั๊มน้ํามัน");
            tvchoicey7.setText("6.ทําความสะอาด Condensate receiver ");
            tvchoicey8.setText("7.สภาพโดยทั่วไปของ receiver");


            saveYear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();
                    if((checkBoxy1.isChecked() || checkBoxy2.isChecked() )&&
                            (checkBoxy3.isChecked() || checkBoxy4.isChecked())&&
                            (checkBoxy5.isChecked() || checkBoxy6.isChecked())&&
                            (checkBoxy7.isChecked() || checkBoxy8.isChecked())&&
                            (checkBoxy9.isChecked() || checkBoxy10.isChecked())&&
                            (checkBoxy11.isChecked() || checkBoxy12.isChecked())&&
                            (checkBoxy13.isChecked() || checkBoxy14.isChecked())

                            ) {

                        note = edt.getText().toString();
                        status = 0;
                        if (note.isEmpty()) {
                            status=1;
                            note = "ปกติ";
                        }
                        typePm = "yearly";
                        listener.getDataFromFragment(note, status, typePm);
                        Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();

                    }
                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });
        }

        else if (type.equals("motor")){
            tvchoicey2.setText("1.ตรวจสอบช่องอากาศระหว่าาวโรเตอร์กับสเตเตอร์");
            tvchoicey3.setText("2.ทดสอบความทนทาร");

            saveYear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date = new Date();
                    if((checkBoxy1.isChecked() || checkBoxy2.isChecked() )&&
                            (checkBoxy3.isChecked() || checkBoxy4.isChecked())&&
                            (checkBoxy5.isChecked() || checkBoxy6.isChecked())&&
                            (checkBoxy7.isChecked() || checkBoxy8.isChecked())&&
                            (checkBoxy9.isChecked() || checkBoxy10.isChecked())&&
                            (checkBoxy11.isChecked() || checkBoxy12.isChecked())&&
                            (checkBoxy13.isChecked() || checkBoxy14.isChecked())

                            ) {

                        note = edt.getText().toString();
                        status = 0;
                        if (note.isEmpty()) {
                            status=1;
                            note = "ปกติ";
                        }
                        typePm = "yearly";
                        listener.getDataFromFragment(note, status, typePm);

                        Toast.makeText(getActivity(),"saved", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(),"Please Check all Questions", Toast.LENGTH_SHORT).show();
                }
            });
        }


        return rootView;
    }



}
