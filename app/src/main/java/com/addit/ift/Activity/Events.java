package com.addit.ift.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.addit.ift.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Savla's on 22-06-2016.
 */

public class Events {
    public String name;
    public int id;
    public String type;
    public String station;
    public String venue;
    public String date;
    public String start_time;
    public String phone;
    public String description;
    public Double price_money;
    public String img;


    public Events(int id, String name, String type, String phone, String station, String venue, String date, String start_time, Double price_money, String description, String img) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.station = station;
        this.date = date;
        this.start_time = start_time;
        this.phone = phone;
        this.description = description;
        this.price_money = price_money;
        this.img = img;
        this.venue = venue;
    }

    private class ViewHolder {
        TextView name;
        TextView station;
        TextView date;
        TextView start_time;
        TextView price_money;
        ImageView image;

    }

    public View getView(Context context, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.event_list, null);
            holder.name = (TextView) convertView.findViewById(R.id.textView8);
            holder.name.setText(name);
            holder.station = (TextView) convertView.findViewById(R.id.textView9);
            holder.station.setText(station);
            holder.date = (TextView) convertView.findViewById(R.id.textView11);
            holder.date.setText("" + date);
            holder.start_time = (TextView) convertView.findViewById(R.id.textView12);
            holder.start_time.setText("" + start_time);
            holder.price_money = (TextView) convertView.findViewById(R.id.textView13);
            holder.price_money.setText("Rs." + price_money);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            Picasso.with(context)
                    .load(img)
                    .placeholder(R.drawable.football1)   // optional// optional
                    .resize(100, 100)                        // optional// optional
                    .into((ImageView) convertView.findViewById(R.id.image));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.name.setText(name);
            holder.station.setText(station);
            holder.date.setText("" + date);
            holder.start_time.setText("" + start_time);
            holder.price_money.setText("Rs." + price_money);
            Picasso.with(context)
                    .load(img)
                    .placeholder(R.drawable.football1)   // optional// optional
                    .resize(100, 100)                        // optional// optional
                    .into((ImageView) convertView.findViewById(R.id.image));
        }
        return convertView;
    }
}

