package com.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

abstract public class XFragment<T> extends Fragment implements View.OnClickListener {
    protected AsyncTask<String, Void, String> asyncTask = null;

    protected T mListener = null;

    public XFragment() {
        // Required empty public constructor
    }

    // show Toast

    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    // Fragment Methods


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (T) getActivity();
        } catch (Exception ignore) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getXmlViews(view, savedInstanceState);
        initXmlViews(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (asyncTask != null && asyncTask.getStatus().compareTo(AsyncTask.Status.FINISHED) != 0)
            asyncTask.cancel(true);
        asyncTask = null;
        onDetachFragment();
    }

    // abstract methods

    abstract protected int getContentView();

    abstract protected void getXmlViews(View view, @Nullable Bundle savedInstanceState);

    abstract protected void initXmlViews(View view);

    abstract protected void onDetachFragment();

    // OnClickListener

    @Override
    public void onClick(View v) {

    }
}
