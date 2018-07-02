package com.addit.ift.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.addit.ift.App;
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

public class Search_Page extends AppCompatActivity {
    Info Info = new Info();
    AlertDialog AD;
    CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__page);
        Intent intent = getIntent();
        String query = intent.getStringExtra("QUERY");
        search(query);
        mAdapter = new CustomAdapter(this);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent3 = new Intent(Search_Page.this, Five_a_Side.class);
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
                intent3.putExtra("UserID", App.Logindetails.USER_ID);
                startActivity(intent3);
            }
        });

    }

    public void search(String query) {

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("EventName", query);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //RequestQueue queue = Volley.newRequestQueue(this);
        AD = AlertBoxesHandler.showLoader(this, "loading", false);
        Search_Page.VolleyListener volleyListener = new Search_Page.VolleyListener();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Info.getSearchWB(), jsonParams,volleyListener,volleyListener){
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

    private class VolleyListener implements Response.Listener<JSONObject>, Response.ErrorListener {

        @Override
        public void onResponse(JSONObject response) {
            try {
                AD.dismiss();
                if (response.length() <= 0) {

                    Toast.makeText(Search_Page.this, "No result found", Toast.LENGTH_SHORT).show();
                    AD.dismiss();
                    // TODO : "No Records found"
                    return;
                }
                JSONArray jsonArray = response.getJSONArray("records");
                for (int i = 0; i < jsonArray.length(); i++)
                    mAdapter.addItem(parse(jsonArray.getJSONObject(i)));
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Toast.makeText(Search_Page.this, "BAd server response", Toast.LENGTH_SHORT).show();
                // AD.dismiss();
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }

        private Events parse(JSONObject jsonObject) throws JSONException {
            return new Events(jsonObject.getInt("PostEventID"), jsonObject.getString("EventName"), jsonObject.getString("Type"),
                    jsonObject.getString("PhoneNo"), jsonObject.getString("Station"),
                    jsonObject.getString("Address") + "," + jsonObject.getString("CityName") + "," + jsonObject.getString("StateName"),
                    jsonObject.getString("EventDate"), jsonObject.getString("Timing"),
                    jsonObject.getDouble("Amount"), jsonObject.getString("Description"),
                    //   jsonObject.getString("EventImage") );
                    "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRhuu6zKAcRshLknlQ7E5kEgO4Hu3Uvj2bbE-YJ4MnzOifwYKos");
        }
    }
}


