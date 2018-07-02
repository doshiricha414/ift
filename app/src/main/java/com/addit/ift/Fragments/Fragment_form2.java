package com.addit.ift.Fragments;

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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.addit.ift.Activity.Info;
import com.addit.ift.R;
import com.utils.DateTimeHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Fragment_form2 extends Fragment {
    EditText b1;
    EditText b2;
    EditText b3;
    EditText b4;
    File destination;
    public Info Info =new Info();
String  image_loc;
String  image_root;
    static int SELECT_FILE;
    static int REQUEST_CAMERA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_form2, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button button1 = (Button) getView().findViewById(R.id.expandableButton1);
        Button button2 = (Button) getView().findViewById(R.id.expandableButton2);
        Button button3 = (Button) getView().findViewById(R.id.expandableButton3);
        Button button4 = (Button) getView().findViewById(R.id.expandableButton5);

        b2 = (EditText) getView().findViewById(R.id.txt_help_gest8);
        b3 = (EditText) getView().findViewById(R.id.txt_help_gest9);
        b1 = (EditText) getView().findViewById(R.id.txt_help_gest7);
        b4 = (EditText) getView().findViewById(R.id.txt_help_gest10);
        TextView textview = (TextView) getView().findViewById(R.id.proceed);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        b4.setVisibility(View.GONE);
        View.OnClickListener OCL = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1;
                switch (v.getId()) {
                    case R.id.expandableButton3:selectImage();

                        break;
                    case R.id.proceed:// Create new fragment and transaction


                        if (!b1.getText().toString().equals("")&&!b2.getText().toString().equals("")&&!b4.getText().toString().equals("")) {
                            String[] array={b2.getText().toString(),b3.getText().toString(),b4.getText().toString(),b1.getText().toString(),image_root,image_loc};
                            passData2(array);
                        } else {

                            Toast.makeText(getContext(), "prie money is mandatory", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.expandableButton5:
                        if (b4.isShown()) {
                            slide_up(getContext(), b4);
                            b4.setVisibility(View.GONE);
                        } else {
                            b4.setVisibility(View.VISIBLE);
                            slide_down(getContext(), b4);
                        }

                        break;

                    case R.id.expandableButton1:
                        if (b1.isShown()) {
                            slide_up(getContext(), b1);
                            b1.setVisibility(View.GONE);
                        } else {
                            b1.setVisibility(View.VISIBLE);
                            slide_down(getContext(), b1);
                        }

                        break;
                    case R.id.expandableButton2:
                        if (b2.isShown()) {
                            slide_up(getContext(), b2);
                            b2.setVisibility(View.GONE);
                        } else {
                            b2.setVisibility(View.VISIBLE);
                            slide_down(getContext(), b2);
                        }
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
        textview.setOnClickListener(OCL);


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
    public interface OnDataPass2 {
        public void onDataPass2(String[] data);
    }

    Fragment_form2.OnDataPass2 dataPasser;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (Fragment_form2.OnDataPass2) a;
    }

    public void passData2(String[] data) {
        dataPasser.onDataPass2(data);
    }



    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                Fragment_form2.Utility u = new Fragment_form2.Utility();
                boolean result = u.checkPermission(getContext());
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
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        String root = Environment.getExternalStorageDirectory().toString();
        File ITF = new File(root + "/IFT");
        ITF.mkdirs();
        Info.PROFILE_IMAGE_LOC = "IFT" + DateTimeHandler.getTime() + ".jpg";
         File destination = new File(ITF,
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

        image_root=root+ "/IFT/";
        image_loc= Info.PROFILE_IMAGE_LOC;
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

        String root = Environment.getExternalStorageDirectory().toString();
        File ITF = new File(root + "/IFT");
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


        image_root=root + "/IFT/";
        image_loc= Info.PROFILE_IMAGE_LOC;
    }


}
