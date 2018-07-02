package com.addit.ift.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.utils.AlertBoxesHandler;
import com.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.utils.Utils.setStatusBarColor;

public class LoginActivity2 extends AppCompatActivity {
    AlertDialog AD;
    Info Info = new Info();
    String firstname1;
    String lastname1;
    String email1;

    SharedPreferences pref; // 0 - for private mode
    SharedPreferences.Editor editor;
    private EditText editTextUsername;
    private EditText editTextPassword;
    //private ImageView loginButton;
    private TwitterLoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   /*     TwitterAuthConfig authConfig = new TwitterAuthConfig(getResources().getString(R.string.TWITTER_KEY),getResources().getString(R.string.TWITTER_SECRET));
        Fabric.with(this, new Twitter(authConfig));
   */
        setContentView(R.layout.activity_login2);

        /*shared preference */
        pref = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        editor = pref.edit();
        if (pref.contains("Logged_in") && pref.getBoolean("Logged_in", false)) {
            Intent intent1 = new Intent(LoginActivity2.this, MainPage.class);
            App.Logindetails.USER_ID = pref.getString("User_Id", null);
            App.Logindetails.LoggedIn = true;
            startActivity(intent1);
            finish();
        }

       /* loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //If login succeeds passing the Calling the login method and passing Result object

                Twlogin(result);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
*/
        callbackManager = CallbackManager.Factory.create();

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(LoginActivity2.this, R.color.transparent);
        }
        editTextUsername = (EditText) findViewById(R.id.user_name);
        editTextPassword = (EditText) findViewById(R.id.password);
        ImageView image = (ImageView) findViewById(R.id.back);
        TextView textview = (TextView) findViewById(R.id.sign_in);
        TextView textview1 = (TextView) findViewById(R.id.forget_password);

        View.OnClickListener OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {


                    case R.id.login_button:
                        if (AccessToken.getCurrentAccessToken() == null) {
                           // AD = AlertBoxesHandler.showLoader(LoginActivity2.this, "Logging", false);
                            LoginManager.getInstance().registerCallback(callbackManager, new FbLoginResult());
                            LoginManager.getInstance().logInWithReadPermissions(LoginActivity2.this, Arrays.asList("email,user_about_me"));

                            //LoginManager.getInstance().logInWithReadPermissions(LoginActivity2.this, Arrays.asList("public_profile"));
                            //  LoginManager.getInstance().logInWithPublishPermissions(LoginActivity2.this, Arrays.asList("publish_actions"));
                        } else
                            getGraphData(AccessToken.getCurrentAccessToken());
                        break;
                    case R.id.back:
                        finish();
                        break;
                    case R.id.sign_in:
                        if (editTextUsername.length() != 0) {
                            if (editTextPassword.length() != 0) {
                                login();
                            } else {
                                editTextPassword.setError("Enter password");
                            }
                        } else {
                            editTextUsername.setError("Enter Email id");
                        }
                        break;
                    case R.id.forget_password:
                        if (!Utils.isValidEmail(editTextUsername.getText().toString().trim()))
                            editTextUsername.setError("Email not valid");
                        else
                            forgotPassword(editTextUsername.getText().toString().trim());
                        break;
                }
            }


        };
        textview.setOnClickListener(OCL);
        textview1.setOnClickListener(OCL);
        image.setOnClickListener(OCL);
        (findViewById(R.id.login_button)).setOnClickListener(OCL);

    }

    private void forgotPassword(String EmailId) {

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("EmailID", EmailId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Info.getForgotPWD_WB(),

                jsonParams,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("records");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("Status");

                            if (status.equals("Success")) {
                                //Toast.makeText(LoginActivity2.this,Info.USER_ID, Toast.LENGTH_SHORT).show();
                                AlertBoxesHandler.showCustomAlertBox(LoginActivity2.this, R.layout.pwd_alert);
                            } else {
                                Toast.makeText(LoginActivity2.this, "please retry", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(LoginActivity2.this, "bad server response", Toast.LENGTH_SHORT).show();
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

    private void getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            /*try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                url = profile_pic.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }*/
            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                firstname1 = object.getString("first_name");
            if (object.has("last_name"))
                lastname1 = object.getString("last_name");
            if (object.has("email"))
                email1 = object.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if(requestCode==140)
            loginButton.onActivityResult(requestCode, resultCode, data);
        else
       */
        callbackManager.onActivityResult(requestCode, resultCode, data);


    }

    private void getGraphData(AccessToken accessToken) {
        AD = AlertBoxesHandler.showLoader(LoginActivity2.this, "signing  in", false);
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        getFacebookData(object);
                        registerFromFacebook();
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email"); // Parámetros que pedimos a facebook
        request.setParameters(parameters);
        request.executeAsync();
        AD.dismiss();
    }

    private void login() {
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("EmailID", username);
            jsonParams.put("Password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        AD = AlertBoxesHandler.showLoader(LoginActivity2.this, "signing  in", false);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, Info.getLoginServer(),

                jsonParams,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(LoginActivity2.this, response.toString(), Toast.LENGTH_SHORT).show();
                        AD.dismiss();
                        try {
                            JSONArray jsonArray = response.getJSONArray("records");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("Status");
                            Info.USER_ID = jsonObject.getString("UserID");

                            if (status.equals("Success")) {
                                //Toast.makeText(LoginActivity2.this,Info.USER_ID, Toast.LENGTH_SHORT).show();
                                Intent it = getIntent();
                                if (it.getBooleanExtra("FROM_CREATE", false)) {
                                    Intent intent1 = new Intent(LoginActivity2.this, MainPage.class);
                                    App.Logindetails.USER_ID = Info.getUserID();
                                    editor.putBoolean("Logged_in", true); // Storing boolean - true/false
                                    editor.putString("User_Id", Info.getUserID());
                                    editor.commit();// Storing string// Storing string
                                    App.Logindetails.LoggedIn = true;
                                    startActivity(intent1);
                                } else {
                                    Intent intent1 = new Intent(LoginActivity2.this, MainPage.class);
                                    App.Logindetails.USER_ID = Info.getUserID();
                                    editor.putBoolean("Logged_in", true); // Storing boolean - true/false
                                    editor.putString("User_Id", Info.getUserID());
                                    editor.commit();// Storing string// Storing string
                                    App.Logindetails.LoggedIn = true;
                                    startActivity(intent1);
                                }
                            } else {
                                Toast.makeText(LoginActivity2.this, "please retry", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            AD.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        AD.dismiss();
                        Toast.makeText(LoginActivity2.this, "bad server response", Toast.LENGTH_SHORT).show();
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
        AD.dismiss();
    }

    private void registerFromFacebook() {
        Info.FTP_USER_NAME = email1;
        final String URL = Info.getFBTWServer();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("FName", firstname1);
            jsonParams.put("LName", lastname1);
            jsonParams.put("EmailID", email1);
            // jsonParams.put("Url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AD = AlertBoxesHandler.showLoader(LoginActivity2.this, "Signing in", false);
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
                                //  Toast.makeText(LoginActivity2.this, "account created successfully", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(LoginActivity2.this, MainPage.class);
                                App.Logindetails.USER_ID = Info.getUserID();
                                editor.putBoolean("Logged_in", true); // Storing boolean - true/false
                                editor.putString("User_Id", Info.getUserID());
                                editor.commit();// Storing string// Storing string
                                App.Logindetails.LoggedIn = true;
                                AD.dismiss();
                                startActivity(intent1);
                            } else {
                                AD.dismiss();
                                Toast.makeText(LoginActivity2.this, "please retry", Toast.LENGTH_SHORT).show();
                            }
                            AD.dismiss();
                        } catch (JSONException e) {
                            AD.dismiss();
                            Toast.makeText(LoginActivity2.this, "bad server response", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(LoginActivity2.this, "bad server response", Toast.LENGTH_SHORT).show();
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
        //AD.dismiss();
    }

    private class FbLoginResult implements FacebookCallback<LoginResult> {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            AccessToken.setCurrentAccessToken(accessToken);
            getGraphData(accessToken);
        }

        @Override
        public void onCancel() {
            LoginManager.getInstance().logOut();
            // Toast.makeText(LoginActivity2.this, "cancel", Toast.LENGTH_SHORT).show(); // App code
        }

        @Override
        public void onError(FacebookException exception) {
            // App code
            Toast.makeText(LoginActivity2.this, exception.toString(), Toast.LENGTH_SHORT).show(); // App code
        }

    }

    public void Twlogin(Result<TwitterSession> result) {

        TwitterSession session = result.data;
        final String username = session.getUserName();
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void failure(TwitterException e) {
                        //If any error occurs handle it here
                    }

                    @Override
                    public void success(Result<User> userResult) {
                        User user = userResult.data;
                        String profileImage = user.profileImageUrl.replace("_normal", "");
                        Toast.makeText(LoginActivity2.this, username, Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity2.this, profileImage, Toast.LENGTH_SHORT).show();

                        /*Intent intent = new Intent(LoginActivity2.this, ProfileActivity.class);
                        intent.putExtra(KEY_USERNAME,username);
                        intent.putExtra(KEY_PROFILE_IMAGE_URL, profileImage);
                        startActivity(intent);
                    */
                    }
                });
        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {

                email1 = result.data;
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });
    }
}













