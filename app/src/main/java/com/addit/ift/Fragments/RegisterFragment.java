package com.addit.ift.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.addit.ift.Activity.MainPage;
import com.addit.ift.Activity.New_event;
import com.addit.ift.Activity.Info;
import com.addit.ift.App;
import com.addit.ift.R;

public class RegisterFragment extends Fragment {
    static Info Info;
    AlertDialog AD;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        Info = new Info();
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_main_page, container, false);
        Button button1 = (Button) rootView.findViewById(R.id.button1);
        Button button2 = (Button) rootView.findViewById(R.id.button2);
        Button button3 = (Button) rootView.findViewById(R.id.button3);
        View.OnClickListener OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), New_event.class);
                switch (v.getId()) {
                    case R.id.button1:
                        intent.putExtra("type", 5);
                        break;
                    case R.id.button2:
                        intent.putExtra("type", 7);
                        break;
                    case R.id.button3:
                        intent.putExtra("type", 11);
                        break;
                }
                startActivity(intent);
            }
        };
        button1.setOnClickListener(OCL);
        button2.setOnClickListener(OCL);
        button3.setOnClickListener(OCL);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
