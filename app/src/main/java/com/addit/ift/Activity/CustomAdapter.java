package com.addit.ift.Activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    private ArrayList<Events> list;
    private Context context;

    public CustomAdapter(Context context) {
        super(context,0);
        this.list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void addItem(Events e) {
        list.add(e);
    }

    public ArrayList<Events> getList() {
        return list;
    }

    @Override
    public Events getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return list.get(position).getView(context, position, convertView, parent);
    }

}

