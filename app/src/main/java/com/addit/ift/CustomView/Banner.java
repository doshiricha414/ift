package com.addit.ift.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Banner extends ImageView {
    public Banner(Context context) {
        super(context);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * getDrawable().getIntrinsicHeight() / getDrawable().getIntrinsicWidth();
        setMeasuredDimension(width, height);
    }
}


// Using this in XML
/*
    <com.addit.eventsmanger.javaClass.customView.Banner
        android:id="@+id/img1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:adjustViewBounds="true"
        android:src="@drawable/img" />
*/