package com.addit.ift.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.addit.ift.R;

import static com.utils.Utils.setStatusBarColor;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            setStatusBarColor(LoginActivity.this, R.color.transparent);
        }
        TextView textview1 = (TextView) findViewById(R.id.create_acc);
        TextView textview2 = (TextView) findViewById(R.id.sign_in);
        View.OnClickListener OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1;
                switch (v.getId())
                {
                    case R.id.sign_in:intent1 = new Intent(LoginActivity.this, LoginActivity2.class);break;
                    case R.id.create_acc:intent1 = new Intent(LoginActivity.this, RegisterActivity.class);break;
                    default:intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                }
                startActivity(intent1);
            }
        };
        textview1.setOnClickListener(OCL);
        textview2.setOnClickListener(OCL);
    }
}
