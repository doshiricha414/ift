package com.addit.ift.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.addit.ift.App;
import com.addit.ift.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref; // 0 - for private mode
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        editor = pref.edit();
        if (pref.contains("Logged_in") && pref.getBoolean("Logged_in", false)) {
            Intent intent1 = new Intent(MainActivity.this, MainPage.class);
            App.Logindetails.USER_ID = pref.getString("User_Id", null);
            App.Logindetails.LoggedIn = true;
            startActivity(intent1);
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
