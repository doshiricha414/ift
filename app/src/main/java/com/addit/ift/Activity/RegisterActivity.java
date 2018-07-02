package com.addit.ift.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.utils.AlertBoxesHandler;
import com.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.utils.Utils.setStatusBarColor;

public class RegisterActivity extends Activity {
    private EditText username;
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText pwd;
    private String email1;
    private String firstname1;
    private String lastname1;
    private String url;
    Info Info = new Info();
    AlertDialog AD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(RegisterActivity.this, R.color.transparent);
        }

        username = (EditText) findViewById(R.id.username);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        pwd = (EditText) findViewById(R.id.pwd);
        ImageView image = (ImageView) findViewById(R.id.back);
        TextView textview = (TextView) findViewById(R.id.create_acc);

        View.OnClickListener OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    /*case R.id.facebook:
                        LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this, Arrays.asList("public_profile"));
                        break;*/
                    case R.id.back:
                        finish();
                        break;
                    case R.id.create_acc:
                        if (username.length() != 0) {
                            if (firstname.length() != 0) {
                                if (lastname.length() != 0) {
                                    if (email.length() != 0) {
                                        if (!Utils.isValidEmail(email.getText().toString().trim()))
                                            email.setError("Email not valid");
                                        if (pwd.length() != 0) {
                                            register();
                                        } else {
                                            pwd.setError("Enter password");
                                        }
                                    } else {
                                        email.setError("Enter Email id");
                                    }
                                } else {
                                    lastname.setError("Enter lastname");
                                }
                            } else {
                                firstname.setError("Enter firstname");
                            }
                        } else {
                            username.setError("Enter username");
                        }

                        break;
                }
            }


        };
        textview.setOnClickListener(OCL);
        image.setOnClickListener(OCL);
       // (findViewById(R.id.facebook)).setOnClickListener(OCL);
    }


    private void register() {
        email1 = email.getText().toString().trim();
        Info.FTP_USER_NAME = email1;
        final String password1 = pwd.getText().toString().trim();
        Info.FTP_PWD = password1;
        firstname1 = firstname.getText().toString().trim();
        lastname1 = lastname.getText().toString().trim();
        final String username1 = username.getText().toString().trim();
        url = "";
        final String URL = Info.getRegisterServer();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("FName", firstname1);
            jsonParams.put("LName", lastname1);
            jsonParams.put("EmailID", email1);
            jsonParams.put("Password", password1);
            jsonParams.put("Url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AD = AlertBoxesHandler.showLoader(RegisterActivity.this, "creating account", false);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL,

                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("records");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("Status");
                            if (status.equals("Success")) {
                                Toast.makeText(RegisterActivity.this, "account created successfully", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(RegisterActivity.this, MainPage.class);
                                App.Logindetails.USER_ID=jsonObject.getString("UserID");
                                SharedPreferences.Editor editor= getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE).edit();
                                editor.putBoolean("Logged_in", true); // Storing boolean - true/false
                                editor.putString("User_Id", jsonObject.getString("UserID"));
                                editor.commit();// Storing string// Storing string
                                App.Logindetails.LoggedIn = true;
                                intent1.putExtra("FROM_CREATE", true);
                                startActivity(intent1);
                            } else {
                                Toast.makeText(RegisterActivity.this, "please retry", Toast.LENGTH_SHORT).show();
                            }
                            AD.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivity.this, "bad server response", Toast.LENGTH_SHORT).show();
                            AD.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "bad server response", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(postRequest);
    }

}

