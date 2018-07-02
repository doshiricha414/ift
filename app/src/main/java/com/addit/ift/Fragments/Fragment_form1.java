package com.addit.ift.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.addit.ift.Activity.Five_a_Side;
import com.addit.ift.Activity.LoginActivity;
import com.addit.ift.Activity.LoginActivity2;
import com.addit.ift.Activity.New_event;
import com.addit.ift.Activity.RegisterActivity;
import com.addit.ift.R;
import com.utils.DateTimeHandler;
import com.utils.Utils;

import java.util.Calendar;

public class Fragment_form1 extends Fragment {
    EditText b1;
    EditText b2;
    EditText b3;
    EditText b4;
    ImageView b5;
    TextView timedate;
    EditText b6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_form1, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button button1 = (Button) getView().findViewById(R.id.expandableButton1);
        Button button2 = (Button) getView().findViewById(R.id.expandableButton2);
        Button button3 = (Button) getView().findViewById(R.id.expandableButton3);
        Button button4 = (Button) getView().findViewById(R.id.expandableButton4);
        b3 = (EditText) getView().findViewById(R.id.txt_help_gest3);
        b1 = (EditText) getView().findViewById(R.id.txt_help_gest1);
        b2 = (EditText) getView().findViewById(R.id.txt_help_gest2);
        b4 = (EditText) getView().findViewById(R.id.txt_help_gest4);
        b5= (ImageView) getView().findViewById(R.id.datepicker);
        b6=(EditText) getView().findViewById(R.id.time);
        timedate=(TextView) getView().findViewById(R.id.date);
        b3.setVisibility(View.GONE);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        b4.setVisibility(View.GONE);
        TextView textview = (TextView) getView().findViewById(R.id.proceed);
        // Setup any handles to view objects here



        View.OnClickListener OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1;
                switch (v.getId()) {
                    case R.id.proceed:// Create new fragment and transaction


                            if (!b1.getText().toString().equals("")&&!b2.getText().toString().equals("")&&!!b6.getText().toString().equals("")&&!b3.getText().toString().equals("")&&!b4.getText().toString().equals("")) {
                               String[] array={b1.getText().toString(),b2.getText().toString(),b3.getText().toString(),b4.getText().toString(),timedate.getText().toString(),b6.getText().toString()};
                                passData(array);
                            } else {

                                Toast.makeText(getContext(), "please fill all details", Toast.LENGTH_SHORT).show();
                            }

                        break;

                    case R.id.expandableButton1:
                        if (b1.isShown()) {
                            slide_up(getContext(), b1);
                            b1.setVisibility(View.GONE);
                        } else {
                            b1.setVisibility(View.VISIBLE);
                            slide_down(getContext(), b1);
                        }

                        break;
                    case R.id.expandableButton2:
                        if (b2.isShown()) {
                            slide_up(getContext(), b2);
                            b2.setVisibility(View.GONE);
                        } else {
                            b2.setVisibility(View.VISIBLE);
                            slide_down(getContext(), b2);
                        }
                        break;
                    case R.id.expandableButton3:
                        if (b3.isShown()) {
                            slide_up(getContext(), b3);
                            b3.setVisibility(View.GONE);
                        } else {
                            b3.setVisibility(View.VISIBLE);
                            slide_down(getContext(), b3);
                        }
                        break;
                    case R.id.expandableButton4:
                        if (b4.isShown()) {
                            slide_up(getContext(), b4);
                            b4.setVisibility(View.GONE);
                        } else {
                            b4.setVisibility(View.VISIBLE);
                            slide_down(getContext(), b4);
                        }

                        break;
                    case R.id.datepicker:
                        performDateSelection();break;
                    default:
                        ;
                }


            }
        };
        button1.setOnClickListener(OCL);
        button2.setOnClickListener(OCL);
        button3.setOnClickListener(OCL);
        button4.setOnClickListener(OCL);
        b5.setOnClickListener(OCL);
        textview.setOnClickListener(OCL);

    }


    private void performDateSelection() {
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);

                    String set = DateTimeHandler.get_DDMMYYYY(dayOfMonth, monthOfYear + 1, year);
                    timedate.setText(set);
                } catch (Exception ex) {
                    Toast.makeText(getContext(), "some error occured", Toast.LENGTH_SHORT).show();
                }
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
    }
    public static void slide_down(Context ctx, View v) {

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static void slide_up(Context ctx, View v) {

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public interface OnDataPass {
        public void onDataPass(String[] data);
    }

    OnDataPass dataPasser;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }

    public void passData(String[] data) {
        dataPasser.onDataPass(data);
    }
}
