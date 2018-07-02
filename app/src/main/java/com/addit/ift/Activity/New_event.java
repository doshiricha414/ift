package com.addit.ift.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.addit.ift.App;
import com.addit.ift.Fragments.Fragment_form1;
import com.addit.ift.Fragments.Fragment_form2;
import com.addit.ift.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.utils.AlertBoxesHandler;
import com.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.utils.Utils.setStatusBarColor;

public class New_event extends FragmentActivity implements Fragment_form1.OnDataPass, Fragment_form2.OnDataPass2 {
    AlertDialog AD;

    private class VH {
        String t1;
        String t2;
        String t3;
        String t4;
        String t5;
        String t6;
        Double t7;
        String t8;
        String t10;
        String t11;
        String t12;
    }

    Info Info = new Info();
    VH vh = new VH();
    int flag = 0;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 5);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(New_event.this, R.color.transparent);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.commit();

        Fragment_form1 ls_fragment = new Fragment_form1();
        fragmentTransaction.add(R.id.fragment_container, ls_fragment);

    }

    public void onDataPass(String[] data) {
        vh.t1 = data[0];
        vh.t2 = data[1];
        vh.t3 = data[2];
        vh.t4 = data[3];
        vh.t5 = data[4];
        vh.t6 = data[5];
        Fragment_form2 newFragment = new Fragment_form2();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, newFragment).commit();
    }

    public void onDataPass2(String[] data) {
        vh.t7 = 1.00 * Integer.parseInt(data[0]);
        vh.t8 = Utils.uploadImage(Info.getImageUploadWB(), Info.getUserID(), New_event.this, data[4], data[5]);
        vh.t10 = data[1];
        vh.t11 = data[2];
        vh.t12 = data[3];
        AD = AlertBoxesHandler.showLoader(New_event.this, "Loading", false);
        postEvent();
    }


    public void postEvent() {
        final String state = vh.t1;
        final String city = vh.t2;
        final String station = vh.t3;
        final String name = vh.t4;
        final String date = vh.t5;
        final String timings = vh.t6;
        final double prizemoney = vh.t7;
        final String imageUrl = vh.t8;
        final String description = vh.t10;
        final String phoneNo = vh.t11;
        final String venue = vh.t12;

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("UserID", App.Logindetails.USER_ID);
            jsonParams.put("Type", type);
            jsonParams.put("StateName", state);
            jsonParams.put("CityName", city);
            jsonParams.put("EventName", name);
            jsonParams.put("StationName", station);
            jsonParams.put("EventDate", date);
            jsonParams.put("Timing", timings);
            jsonParams.put("Amount", prizemoney);
            jsonParams.put("EventImage", imageUrl);
            jsonParams.put("Description", description);
            jsonParams.put("PhoneNo", phoneNo);
            jsonParams.put("Address", venue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Info.getPostEventServer(),

                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("records");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("Status");
                            AD.dismiss();
                            if (status.equals("Success")) {
                                Toast.makeText(New_event.this, "Event_created", Toast.LENGTH_SHORT).show();
                                                                Intent intent1 = new Intent(New_event.this, MainPage.class);
                                intent1.putExtra("USER_ID", App.Logindetails.USER_ID);
                                startActivity(intent1);
                            } else {
                                Toast.makeText(New_event.this, "please retry", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(New_event.this, "some error occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(30),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
        AD.dismiss();
    }
}

