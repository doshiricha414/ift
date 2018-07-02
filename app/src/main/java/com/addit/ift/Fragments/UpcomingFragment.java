package com.addit.ift.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.addit.ift.Activity.CustomAdapter;
import com.addit.ift.Activity.Events;
import com.addit.ift.Activity.Five_a_Side;
import com.addit.ift.Activity.Info;
import com.addit.ift.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.utils.AlertBoxesHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UpcomingFragment extends Fragment {
    private AlertDialog AD;
    private CustomAdapter mAdapter;
    boolean F1 = false;

    public UpcomingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CustomAdapter(getContext());
    }

    public static UpcomingFragment newInstance() {
        return new UpcomingFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.upcuming_events, container, false);
        if (!F1) {
            callNetwork();
            Log.d("1ST", "F2");
            F1 = true;
        }
        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent3 = new Intent(getActivity(), Five_a_Side.class);
                Events e = mAdapter.getItem(position);
                intent3.putExtra("Name", e.name);
                intent3.putExtra("Type", e.type);
                intent3.putExtra("Station", e.station);
                intent3.putExtra("Venue", e.venue);
                intent3.putExtra("Phone", e.phone);
                intent3.putExtra("Date", e.date);
                intent3.putExtra("StartTime", e.start_time);
                intent3.putExtra("PrizeMoney", e.price_money);
                intent3.putExtra("ImageURL", e.img);
                intent3.putExtra("EventID", e.id);
                intent3.putExtra("EventType", true);
                startActivity(intent3);
            }
        });
        return rootView;
    }

   /* @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);
        ListView lv = (ListView) view.findViewById(R.id.listView);
        lv.setAdapter(mAdapter = new CustomAdapter(getActivity()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent3 = new Intent(getActivity(), Five_a_Side.class);
                Events e = mAdapter.getItem(position);
                intent3.putExtra("Name", e.name);
                intent3.putExtra("Type", e.type);
                intent3.putExtra("Station", e.station);
                intent3.putExtra("Venue", e.venue);
                intent3.putExtra("Phone", e.phone);
                intent3.putExtra("Date", e.date);
                intent3.putExtra("StartTime", e.start_time);
                intent3.putExtra("PrizeMoney", e.price_money);
                intent3.putExtra("ImageURL", e.img);
                intent3.putExtra("EventID", e.id);
                intent3.putExtra("UserID", MainPage.user_id);
                startActivity(intent3);
            }
        });
        if (!F1)
            callNetwork();
    }*/

    private void callNetwork() {
        F1 = true;
        mAdapter.getList().clear();
        mAdapter.notifyDataSetChanged();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("EventType", "U");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AD = AlertBoxesHandler.showLoader(getContext(), "loading", false);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        VolleyListener volleyListener = new VolleyListener();
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Info.getEventServer(), jsonParams, volleyListener, volleyListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(postRequest);
    }

    private class VolleyListener implements Response.Listener<JSONObject>, Response.ErrorListener {

        @Override
        public void onResponse(JSONObject response) {
            try {
                AD.dismiss();
                if (response.length() <= 0) {
                    // TODO : "No Records found"
                    return;
                }
                JSONArray jsonArray = response.getJSONArray("records");
                for (int i = 0; i < jsonArray.length(); i++)
                    mAdapter.addItem(parse(jsonArray.getJSONObject(i)));
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                // TODO : "Bad Server Response"
            }

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }

        private Events parse(JSONObject jsonObject) throws JSONException {
            String image = jsonObject.getString("EventImage");
            image=image.replaceAll("wwwroot/","");
            if(!image.contains("EventImage/"))
            image=image.replaceAll("EventPhoto","EventPhoto/");
            String im="http://";
            image=im.concat(image);
//Log.d("EventPhoto",image);
            return new Events(jsonObject.getInt("PostEventID"), jsonObject.getString("EventName"), jsonObject.getString("Type"),
                    jsonObject.getString("PhoneNo"), jsonObject.getString("Station"),
                    jsonObject.getString("Address") + "," + jsonObject.getString("CityName") + "," + jsonObject.getString("StateName"),
                    jsonObject.getString("EDate"), jsonObject.getString("Timing"),
                    jsonObject.getDouble("Amount"), jsonObject.getString("Description"),
                    //  jsonObject.getString("EventImage") );
                    image);

        }
    }
}
