package com.addit.ift.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.addit.ift.App;
import com.addit.ift.CustomView.Banner;
import com.addit.ift.R;
import com.addit.ift.picasso.RoundedTransformation;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.utils.AlertBoxesHandler;
import com.utils.MapsLauncher;
import com.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.addit.ift.R.id.ok;
import static com.addit.ift.R.id.txt_help_gest1;
import static com.addit.ift.R.id.txt_help_gest2;
import static com.addit.ift.R.id.txt_help_gest3;
import static com.utils.Utils.setStatusBarColor;

public class Five_a_Side extends AppCompatActivity {
    TextView b1;
    TextView b2;
    TextView b3;
    TextView b4;
    static Intent intent;
    Info Info = new Info();
    int eventId;
    Dialog AD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5_a_side_container);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(Five_a_Side.this, R.color.transparent);
        }
        intent = getIntent();
        eventId = intent.getIntExtra("EventID", 0);
        if(!intent.getBooleanExtra("EventType",false))
            ((Button) findViewById(R.id.button4)).setVisibility(View.GONE);
        getLikeJason(eventId);
        ((TextView) findViewById(txt_help_gest1)).setText(intent.getStringExtra("Station") + "\n" + intent.getStringExtra("Venue"));
        ((TextView) findViewById(txt_help_gest2)).setText("Date:   " + intent.getStringExtra("Date") + "\nTIme:  " + intent.getStringExtra("StartTime"));
        ((TextView) findViewById(txt_help_gest3)).setText("Rs. " + intent.getDoubleExtra("PrizeMoney", 0.00));
        Banner banner = (Banner) findViewById(R.id.banner);
        Picasso.with(this)
                .load(intent.getStringExtra("ImageURL"))
                .placeholder(R.drawable.football1)   // optional// option
                .fit().centerCrop()
                .into(banner);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(intent.getStringExtra("name"));

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(intent.getStringExtra("Name"));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        FloatingActionButton mfab = (FloatingActionButton) findViewById(R.id.map_fab);
        FloatingActionButton cfab = (FloatingActionButton) findViewById(R.id.call_fab);
        FloatingActionButton lfab = (FloatingActionButton) findViewById(R.id.like_fab);

        Button button1 = (Button) findViewById(R.id.expandableButton1);
        Button button2 = (Button) findViewById(R.id.expandableButton2);
        Button button3 = (Button) findViewById(R.id.expandableButton3);
        Button button4 = (Button) findViewById(R.id.button4);

        b3 = (TextView) findViewById(txt_help_gest3);
        b1 = (TextView) findViewById(txt_help_gest1);
        b2 = (TextView) findViewById(txt_help_gest2);
        b4 = (TextView) findViewById(ok);

        b3.setVisibility(View.GONE);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);

        View.OnClickListener OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1;
                switch (v.getId()) {
                    case R.id.call_fab:
                        Utils.openDialer(Five_a_Side.this, intent.getStringExtra("Phone"));
                        break;
                    case R.id.like_fab:
                        like();
                        break;
                    case R.id.map_fab:
                        MapsLauncher.navigateTurnByTurn(Five_a_Side.this, intent.getStringExtra("Venue"));
                        break;

                    case R.id.expandableButton1:
                        if (b1.isShown()) {
                            slide_up(Five_a_Side.this, b1);
                            b1.setVisibility(View.GONE);
                        } else {
                            b1.setVisibility(View.VISIBLE);
                            slide_down(Five_a_Side.this, b1);
                        }

                        break;
                    case R.id.expandableButton2:
                        if (b2.isShown()) {
                            slide_up(Five_a_Side.this, b2);
                            b2.setVisibility(View.GONE);
                        } else {
                            b2.setVisibility(View.VISIBLE);
                            slide_down(Five_a_Side.this, b2);
                        }
                        break;
                    case R.id.expandableButton3:
                        if (b3.isShown()) {
                            slide_up(Five_a_Side.this, b3);
                            b3.setVisibility(View.GONE);
                        } else {
                            b3.setVisibility(View.VISIBLE);
                            slide_down(Five_a_Side.this, b3);
                        }
                        break;
                    case R.id.button4:
                        participate();
                        break;
                    case ok:
                        AD.dismiss();
                        break;
                    default:
                        ;
                }


            }
        };
        button1.setOnClickListener(OCL);
        button2.setOnClickListener(OCL);
        button3.setOnClickListener(OCL);
        button4.setOnClickListener(OCL);
        mfab.setOnClickListener(OCL);
        cfab.setOnClickListener(OCL);
        lfab.setOnClickListener(OCL);
    }

    private void getLikeJason(int eID) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("EventID", eID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AD = AlertBoxesHandler.showLoader(Five_a_Side.this, "Loading..", false);
        VolleyListenerGetLike volleyListener = new VolleyListenerGetLike();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Info.getLikeRecordWB(), jsonParams, volleyListener, volleyListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(postRequest);
        AD.dismiss();
    }

    private void putLike(JSONObject jobj) {
        String[] image = new String[4];
        try {
            ((TextView) findViewById(R.id.like_count)).setText(jobj.getString("Count"));
            JSONArray jarray = jobj.getJSONArray("PhotoData");

            if (jarray.length() > 0) {
                for (int i = 0; i < jarray.length(); i++) {
                     String image1 = (jarray.getJSONObject(i)).getString("PhotoUrl");
                    image1=image1.replaceAll("wwwroot/","");
                    if(!image1.contains("EventImage/"))
                        image1=image1.replaceAll("EventPhoto","EventPhoto/");
                    String im="http://";
                    image1=im.concat(image1);
                    image[i] =image1;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView imageView1 = (ImageView) findViewById(R.id.imageview1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageview2);
        ImageView imageView3 = (ImageView) findViewById(R.id.imageview3);
        ImageView imageView4 = (ImageView) findViewById(R.id.imageview4);
        Picasso.with(this)
                .load(image[0])
                .transform(new RoundedTransformation(50, 0))
                .placeholder(R.drawable.profile1)   // optional// optional
                .resize(100, 100)                        // optional// optional
                .into(imageView1);
        Picasso.with(this)
                .load(image[1])
                .transform(new RoundedTransformation(50, 0))
                .placeholder(R.drawable.profile2)   // optional// optional
                .resize(100, 100)                        // optional// optional
                .into(imageView2);
        Picasso.with(this)
                .load(image[2])
                .transform(new RoundedTransformation(50, 0))
                .placeholder(R.drawable.profile3)   // optional// optional
                .resize(100, 100)                        // optional// optional
                .into(imageView3);
        Picasso.with(this)
                .load(image[3])
                .transform(new RoundedTransformation(50, 0))
                .placeholder(R.drawable.profile4)   // optional// optional
                .resize(100, 100)                        // optional// optional
                .into(imageView4);


    }

    public void like() {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("UserID", App.Logindetails.USER_ID);
            jsonParams.put("EventID", eventId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AD = AlertBoxesHandler.showLoader(Five_a_Side.this, "Signing in", false);
        VolleyListenerLike volleyListenerLike = new VolleyListenerLike();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Info.getLikeWB(), jsonParams, volleyListenerLike, volleyListenerLike){
            @Override
            public Map<String, String> getHeaders ()throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        } ;
        queue.add(postRequest);
        AD.dismiss();
        getLikeJason(eventId);
    }

    public void participate() {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("UserID",App.Logindetails.USER_ID);
            jsonParams.put("EventID", eventId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Five_a_Side.VolleyListenerParticipate volleyListenerParticipate = new Five_a_Side.VolleyListenerParticipate();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Info.getParticipateWB(), jsonParams, volleyListenerParticipate, volleyListenerParticipate) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(postRequest);
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

    private class VolleyListenerGetLike implements Response.Listener<JSONObject>, Response.ErrorListener {

        @Override
        public void onResponse(JSONObject response) {
            try {AD.dismiss();
                JSONArray jsonArray = response.getJSONArray("records");
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                putLike(jsonObject);

            } catch (JSONException e) {
               // Toast.makeText(Five_a_Side.this, "Some error occured", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            Toast.makeText(Five_a_Side.this, "bad server response", Toast.LENGTH_SHORT).show();
        }

    }

    private class VolleyListenerParticipate implements Response.Listener<JSONObject>, Response.ErrorListener {

        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray jsonArray = response.getJSONArray("records");
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String status = jsonObject.getString("Status");

                if (status.equals("Success")) {
                    AlertBoxesHandler.showCustomAlertBox(Five_a_Side.this, R.layout.participate_alert);
                } else {
                    Toast.makeText(Five_a_Side.this, "you have already participated", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(Five_a_Side.this, "bad server response", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            Toast.makeText(Five_a_Side.this, "bad server response", Toast.LENGTH_SHORT).show();
        }
    }

    private class VolleyListenerLike implements Response.Listener<JSONObject>, Response.ErrorListener {

        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray jsonArray = response.getJSONArray("records");
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String status = jsonObject.getString("Status");
                if (status.equals("Success")) {
                    //do nothing
                } else {
                }
            } catch (JSONException e) {
                Toast.makeText(Five_a_Side.this, "Some error occured", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            Toast.makeText(Five_a_Side.this, "bad server response", Toast.LENGTH_SHORT).show();
        }
    }

}



