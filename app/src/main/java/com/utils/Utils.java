package com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.addit.ift.Activity.CustomAdapter;
import com.addit.ift.Activity.Events;
import com.addit.ift.Activity.Ftp;
import com.addit.ift.Activity.LoginActivity2;
import com.addit.ift.Activity.MainActivity;
import com.addit.ift.Activity.MainPage;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Utils {


    public static String getImei(Context context) {
        TelephonyManager tmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tmgr.getDeviceId();
    }

    public static boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.find();
    }

    public static String capitalizeFirstLetter(String str) {
        return str == null || str.length() == 0 ? str : str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
public static void Logout(){
   SharedPreferences pref = getApplicationContext().getSharedPreferences("Mypref",Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();
    editor.clear().commit();


}
    public static void openLinkInBrowser(Context context, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static String uploadImage(String URL, String User_Id, final Context context, final String IMG_ROOT, final String IMG_NAME) {


        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("UserID", User_Id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL,

                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = response.getJSONArray("records");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String IpAdressFTP = jsonObject.getString("IpAddress");
                            String UserNameFTP = jsonObject.getString("UserName");
                            String PasswordFTP = jsonObject.getString("Password");
                            String ImagePathFTP = jsonObject.getString("imagepath");
                            // String ImagePathFTP  = "clientworksarea.com/wwwroot/bigfaida/Uploads/Bill/";
                           /* if(!ImagePathFTP.endsWith("/"))
                                ImagePathFTP=ImagePathFTP+"/";
                            while(ImagePathFTP.charAt(0)=='w')
                                ImagePathFTP=ImagePathFTP.substring(1);
*/
                            //ImagePathFTP=ImagePathFTP.substring(1);
                            if (!ImagePathFTP.endsWith("/"))
                                ImagePathFTP = ImagePathFTP + "/";

                            // String status = jsonObject.getString("Status");


                            if (IpAdressFTP.length() != 0) {
                                Ftp ftp = new Ftp();
                                /*ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                                String root = Environment.getExternalStorageDirectory().toString();
                                File ITF = new File(root + "/IFT");
                                ITF.mkdirs();
                                String PROFILE_IMAGE_LOC =imagePath= "IFT" + DateTimeHandler.getTime() + ".jpg";
                                File destination = new File(ITF,
                                        PROFILE_IMAGE_LOC);

                                FileOutputStream fo;
                                try {
                                    destination.createNewFile();
                                    fo = new FileOutputStream(destination);
                                    fo.write(bytes.toByteArray());
                                    fo.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
*/
                                if (ftp.upLoad(IpAdressFTP, UserNameFTP, PasswordFTP, IMG_ROOT + IMG_NAME, ImagePathFTP + IMG_NAME)) {
                                    Toast.makeText(context, "Image uploaded", Toast.LENGTH_SHORT).show();


                                } else
                                  Toast.makeText(context, "Image not Uploaded", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(context, "upload failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    File destinaton=new File(IMG_ROOT + IMG_NAME);
                        destinaton.delete();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, "bad server response", Toast.LENGTH_SHORT).show();
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
        Log.d("img_name",IMG_NAME);
        return IMG_NAME;
    }

    public static void openDialer(Context context, String phoneNo) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNo));
        context.startActivity(intent);
    }
}