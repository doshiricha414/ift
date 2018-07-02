package com.addit.ift.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.utils.DateTimeHandler;
import com.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity3 extends AppCompatActivity {
    Info Info = new Info();
    AlertDialog AD;
    static int SELECT_FILE;
    static int REQUEST_CAMERA;
    File destination;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        Intent intent = getIntent();
        Info.USER_ID = user_id = intent.getStringExtra("USER_ID");
        ImageView image = (ImageView) findViewById(R.id.imageView3);
        View.OnClickListener OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.imageView3:
                        selectImage();
                        break;
                    case R.id.upload:
                        Info.PROFILE_IMAGE_LOC = Utils.uploadImage(Info.getImageUploadWB(), Info.getUserID(), LoginActivity3.this, Info.IMG_ROOT, Info.PROFILE_IMAGE_LOC);

                        upload();

                        break;
                }
            }
        };
        image.setOnClickListener(OCL);


    }


    private void upload() {

        final String URL = Info.getUpdateServer();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("UserID", user_id);
            jsonParams.put("Url", Info.PROFILE_IMAGE_LOC);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AD = AlertBoxesHandler.showLoader(LoginActivity3.this, "updating account", false);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URL,

                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = response.getJSONArray("records");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String status = jsonObject.getString("Status");
                            //  Info.USER_ID=jsonObject.getString("UserID");


                            if (status.equals("Success")) {
                                Toast.makeText(LoginActivity3.this, "profile picture uploaded successfully", Toast.LENGTH_SHORT).show();

                                Intent intent1 = new Intent(LoginActivity3.this, MainPage.class);
                                intent1.putExtra("USER_ID", user_id);
                                startActivity(intent1);
                            } else {
                                Toast.makeText(LoginActivity3.this, "please retry", Toast.LENGTH_SHORT).show();
                            }
                            AD.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity3.this, "bad server response", Toast.LENGTH_SHORT).show();
                            AD.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(LoginActivity3.this, "bad server response", Toast.LENGTH_SHORT).show();
                        AD.dismiss();

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


    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity3.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                LoginActivity3.Utility u = new LoginActivity3.Utility();
                boolean result = u.checkPermission(LoginActivity3.this);
                if (items[item].equals("Take Photo")) {
                    String userChoosenTask = "Take Photo";
                    SELECT_FILE = 0;
                    REQUEST_CAMERA = 1;
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    String userChoosenTask = "Choose from Library";
                    SELECT_FILE = 1;
                    REQUEST_CAMERA = 0;
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public class Utility {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public boolean checkPermission(final Context context) {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }

    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 10, bytes);
        String root = Environment.getExternalStorageDirectory().toString();
        File ITF = new File(root + "/IFT");
        Info.IMG_ROOT = root + "/IFT/";
        ITF.mkdirs();
        Info.PROFILE_IMAGE_LOC = "IFT" + DateTimeHandler.getTime() + ".jpg";
        destination = new File(ITF,
                Info.PROFILE_IMAGE_LOC);

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

        ImageView ivImage = (ImageView) findViewById(R.id.imageView3);
        ivImage.setImageBitmap(bm);
        Picasso.with(LoginActivity3.this)
                .load(destination)
                .transform(new RoundedTransformation(300, 0))
                .placeholder(R.drawable.ic_placeholder)   // optional// optional
                .resize(600, 600)                       // optional// optional
                .into(ivImage);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 10, bytes);

        String root = Environment.getExternalStorageDirectory().toString();
        File ITF = new File(root + "/IFT");
        Info.IMG_ROOT = root + "/IFT/";
        ITF.mkdirs();
        Info.PROFILE_IMAGE_LOC = "IFT" + DateTimeHandler.getTime() + ".jpg";
        destination = new File(ITF,
                Info.PROFILE_IMAGE_LOC);

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


        ImageView ivImage = (ImageView) findViewById(R.id.imageView3);
        ivImage.setImageBitmap(thumbnail);
        Picasso.with(LoginActivity3.this)
                .load(destination)
                .transform(new RoundedTransformation(300, 0))
                .placeholder(R.drawable.ic_placeholder)   // optional// optional
                .resize(600, 600)                       // optional// optional
                .into(ivImage);
    }

}
