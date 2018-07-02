package com.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.addit.ift.R;


public class AlertBoxesHandler {
    public static void showAlertBox(Context context, String title, String msg, String positiveButtonText, DialogInterface.OnClickListener positiveOnClickListener, String negativeButtonText, DialogInterface.OnClickListener negativeOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setIcon(R.mipmap.ic_launcher).setTitle(title)
                .setMessage(msg);
        if (positiveButtonText != null)
            builder.setPositiveButton(positiveButtonText, positiveOnClickListener);
        if (negativeButtonText != null)
            builder.setNegativeButton(negativeButtonText, negativeOnClickListener);
        builder.show();
    }

    public static AlertDialog showAlertBox(Context context, View view, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setIcon(R.mipmap.ic_launcher).setTitle(title);
        builder.setView(view);
        return builder.show();
    }

    /*public static AlertDialog showListViewAlertBox(Context context, String title, MyListViewAdapter adapter, AdapterView.OnItemClickListener onItemClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setIcon(R.drawable.ic_logo).setTitle(title);
        View convertView = LayoutInflater.from(context).inflate(R.layout.common_list_layout, null, false);
        ((ListView) convertView.findViewById(R.id.listCommon)).setAdapter(adapter);
        ((ListView) convertView.findViewById(R.id.listCommon)).setOnItemClickListener(onItemClickListener);
        builder.setView(convertView);
        return builder.show();
    }*/

    public static AlertDialog showListItemsAlertBox(Context context, String title, String[] charSequence, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setIcon(R.mipmap.ic_launcher).setTitle(title);
        builder.setItems(charSequence, onClickListener);
        return builder.show();
    }

    public static AlertDialog showLoader(Context context, String msg, boolean setCanceledOnTouchOutside) {
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.loading, null);
        ((TextView) view.findViewById(R.id.text1)).setText(msg);
        /*((ProgressBar) view.findViewById(R.id.progressBar).setBackgroundColor();*/
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view);
        AlertDialog alertDialog = builder.show();
        alertDialog.setCanceledOnTouchOutside(setCanceledOnTouchOutside);
        return alertDialog;
    }
    public static Dialog showCustomAlertBox(Context context, @LayoutRes int layout) {
        final Dialog dialog = new Dialog(context){@Override
        public boolean onTouchEvent(MotionEvent event) {
            // Tap anywhere to close dialog.
            this.dismiss();
            return true;
        }};

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        return dialog;
    }
    public static AlertDialog showAlert(Context context ,boolean setCanceledOnTouchOutside) {
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.participate_alert, null);
       // ((TextView) view.findViewById(R.id.text1)).setText(msg);

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view);

        AlertDialog alertDialog = builder.show();
        alertDialog.setCanceledOnTouchOutside(setCanceledOnTouchOutside);
        return alertDialog;
    }

}
